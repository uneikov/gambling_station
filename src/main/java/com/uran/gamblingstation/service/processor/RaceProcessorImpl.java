package com.uran.gamblingstation.service.processor;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.HorseService;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.service.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.uran.gamblingstation.model.BaseEntity.ADMIN_ID;

@Component
public class RaceProcessorImpl implements RaceProcessor{
    private static final Logger LOG = LoggerFactory.getLogger(RaceProcessor.class);
    @Autowired private StakeService stakeService;
    @Autowired private AccountService accountService;
    @Autowired private HorseService horseService;

    public void process0(Horse winningHorse, LocalDateTime startRace, LocalDateTime endRace){
        // ------------------------- set & get winning stakes -------------------------------------
        LOG.info("Winning horse: {}", winningHorse);
        Horse horse = horseService.get(winningHorse.getId(), ADMIN_ID);
        horse.setWins(horse.getWins() + 1);
        horseService.update(horse, ADMIN_ID);
        stakeService.setWinningStakes(horse.getId(), startRace, endRace);

        List<Stake> winningStakes = stakeService.getWinningStakes(startRace, endRace);
        Double allCash = stakeService.getAllCash(startRace, endRace);
        LOG.info("Winning stakes count: {}, all cash from race stakes: {}", winningStakes.size(), allCash);
        if (winningStakes.isEmpty()){
           /* Wallet stationWallet = walletService.get(WALLET_ID, WALLET_ID); //??????????
            stationWallet.setCash(stationWallet.getCash() + allCash);*/
            accountService.addToStationAccount(allCash);
            LOG.info("Nobody wins, station revenue: {}", allCash);
           /* walletService.update(stationWallet, WALLET_ID);*/
        }else {
            // ----------- получить сумму всех ставок и рассчитать коэффициент выигрыша ----------------

            Double winningCash = winningStakes.stream().mapToDouble(Stake::getStakeValue).sum();
            Double winRatio = allCash/winningCash;
            // ---------------------------- make winning map -------------------------------------------

            Map<Integer, Double> winningMap = winningStakes.stream()
                    .collect(Collectors.toMap(s -> s.getUser().getId(), s -> s.getStakeValue() * winRatio));

            //beforeList.forEach(stake ->  winningMap.put(stake.getUser().getId(), stake.getStakeValue()*coeff));*//*

            // ------------------- process winning stakes to set winning amount ------------------------
            stakeService.processWinningStakes(winningStakes, winningMap);
            // ------------------------ deposit winning amount to winner`s wallets ---------------------
            winningStakes = stakeService.getWinningStakes(startRace, endRace); // можно упростить?
            LOG.info("Winning stakes {}", winningStakes);
            winningStakes.forEach(stake -> {
                accountService.addToAccount(stake.getUser().getId(), stake.getAmount());
               /* Wallet wallet = stake.getUser().getWallet();
                wallet.setCash(wallet.getCash() + stake.getAmount());
                walletService.update(wallet, stake.getUser().getId());*/
            });
            // ----------------------- withdrawal stake values from looser`s wallets -------------------
            List<Stake> loosingStakes = stakeService.getLoosingStakes(startRace, endRace); // можно упростить??
            //loosingStakes.forEach(s -> System.out.println("============= BEFORE ========= " + s.getUser().getWallet().getCash()));
            loosingStakes.forEach(stake -> {
                accountService.debitAccount(stake.getUser().getId(), stake.getAmount());
               /* Wallet wallet = stake.getUser().getWallet();
                wallet.setCash(wallet.getCash() - stake.getStakeValue());
                walletService.update(wallet, stake.getUser().getId());*/
            });
            //loosingStakes.forEach(s -> System.out.println("============= AFTER ========= " + s.getUser().getWallet().getCash()));
        }
    }
    //????????????????????

    public void process(int winningHorseId, LocalDateTime startRace, LocalDateTime endRace) {
        // -------------------- для выигравшей лошади увеличить кол-во выигрышных забегов -------------------
        Horse horse = horseService.get(winningHorseId, ADMIN_ID);
        LOG.info("Winning horse: {}", horse);
        horse.setWins(horse.getWins() + 1);
        horseService.update(horse, ADMIN_ID);
        // -------------------- установить признак "wins" у выигравших ставок (по horse.id)-------------------
        stakeService.setWinningStakes(horse.getId(), startRace, endRace);
        // -------------------= получить сумму средств всех сделанных ставок, участвующих в розыгрыше=--------
        Double allCash = stakeService.getAllCash(startRace, endRace);
        // -------------------= получить список выигравших ставок (по wins) =---------------------------------
        List<Stake> winningStakes = stakeService.getWinningStakes(startRace, endRace);
        // ---------------------------= обработка результатов =-----------------------------------------------
        LOG.info("Winning stakes count: {}, all cash from race stakes: {}", winningStakes.size(), allCash);
        if (winningStakes.isEmpty()) {
            // -----------------------= если нет выигравших, все средства идут организатору =------------------
            accountService.addToStationAccount(allCash);
            accountService.debitAccounts(startRace, endRace);
            LOG.info("Nobody wins, station revenue: {}", allCash);
        } else {
            // -----------------------= получить сумму выигравших ставок =-------------------------------------
            Double winningCash = winningStakes.stream().mapToDouble(Stake::getStakeValue).sum();
            // -----------------------= получить коэффициент для начисления выигрышей =------------------------
            // -------------------------= разыгрываются только средства проигравших =--------------------------
            Double winRatio = (allCash - winningCash) / winningCash;
            // -----------------------= получить матрицу выигрышей =-------------------------------------------
            Map<Integer, Double> winningMap = winningStakes.stream()
                    .collect(Collectors.toMap(s -> s.getUser().getId(), s -> s.getStakeValue() * winRatio));
            // -----------------------= установка поля amount у выигравших ставок =----------------------------
            stakeService.processWinningStakes(winningStakes, winningMap);
            // -----------------------= пополнение кошельков выигравших пользователей =-------------------------
            stakeService.getWinningStakes(startRace, endRace)
                    .forEach(stake -> {
                        accountService.addToAccount(stake.getUser().getId(), stake.getAmount());
                    });
            // -----------------------= списание средств с кошельков проигравших пользователей =----------------
            stakeService.getLoosingStakes(startRace, endRace)
                    .forEach(stake -> {
                        accountService.debitAccount(stake.getUser().getId(), stake.getAmount());
                    });
            // -------------------------------------= the end =-------------------------------------------------
        }
    }
}
