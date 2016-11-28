package com.uran.gamblingstation.service.processor;

import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.HorseService;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.service.account.AccountService;
import com.uran.gamblingstation.util.stake.StakeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RaceProcessorImpl implements RaceProcessor{
    private static final Logger LOG = LoggerFactory.getLogger(RaceProcessor.class);
    @Autowired private StakeService stakeService;
    @Autowired private AccountService accountService;
    @Autowired private HorseService horseService;

   /* public void process(int winningHorseId, LocalDateTime start, LocalDateTime finish) {

        // set editable to false for all stakes in the current race
        stakeService.setNotEditable(start, finish);
        // -------------------- для выигравшей лошади увеличить кол-во выигрышных забегов -------------------
        // ОБЪЕДИНИТЬ В ОДНУ ФУНКЦИЮ ???
        Horse horse = horseService.get(winningHorseId);
        LOG.info("Winning horse: {}", horse.getName());
        horse.setWins(horse.getWins() + 1);
        horseService.update(horse);
        // -------------------- установить признак "wins" у выигравших ставок (по horse.id)-------------------
        stakeService.setWinningStakes(winningHorseId, start, finish);
        // -------------------= получить сумму средств всех сделанных ставок, участвующих в розыгрыше=--------
        Double allCash = stakeService.getAllCash(start, finish);
        LOG.info("Overall stakes sum for current race is {}", allCash);
        // -------------------= получить список выигравших ставок (по wins) =---------------------------------
        List<Stake> winningStakes = stakeService.getWinningStakes(start, finish);
        // ---------------------------= обработка результатов =-----------------------------------------------
        LOG.info("Winning stakes count: {}, all cash from race stakes: {}", winningStakes.size(), allCash);
        LOG.info("Station cash: {}", walletService.get(WALLET_ID).getCash());
        LOG.info("Mast be the same??? {} = {}", allCash, walletService.get(WALLET_ID).getCash());

        if (winningStakes.isEmpty()) {
            // -----------------------= если нет выигравших, все средства идут организатору =------------------
            //accountService.addToStationAccount(allCash); //еще раз?
            //accountService.debitAccounts(startRace, endRace); // add RACE table -> debitAccounts(currentRace)
            LOG.info("Nobody wins, station revenue: {}", allCash);
        } else {
            // -----------------------= получить сумму выигравших ставок =-------------------------------------
            Double winCash = StakeUtil.getValue(winningStakes);
            // -----------------------= получить коэффициент для начисления выигрышей =------------------------
            // -------------------------= разыгрываются только средства проигравших =--------------------------
            //Double winRatio = (allCash - winningCash) / winningCash;
            Double winRatio = allCash / winCash;
            // -----------------------= получить матрицу выигрышей =-------------------------------------------
            Map<Integer, Double> winningMap = winningStakes.stream()
                    .collect(Collectors.toMap(s -> s.getUser().getId(), s -> s.getStakeValue() * winRatio));
            // -----------------------= установка поля amount у выигравших ставок =----------------------------
            stakeService.processWinningStakes(winningStakes, winningMap);
            // -----------------------= пополнение кошельков выигравших пользователей =-------------------------
            stakeService.getWinningStakes(start, finish)
                    .forEach(stake -> accountService.transferToUser(stake.getUser().getId(), stake.getAmount()));
            // -----------------------= списание средств с кошельков проигравших пользователей =----------------
            //stakeService.getLoosingStakes(startRace, endRace).forEach(stake -> accountService.debitAccount(stake.getUser().getId(), stake.getAmount()));
            // -------------------------------------= the end =-------------------------------------------------
            // а что с кошельком station???
            LOG.info("Winning map: {}", winningMap);
        }

    }*/

    @Override
    public void process(int horseId, int raceId) {
        stakeService.setNotEditable(raceId);
        horseService.update(horseService.get(horseId).addWins());
        stakeService.setWinningStakes(horseId, raceId);
        Double allCash = stakeService.getAllCash(raceId);
        List<Stake> winningStakes = stakeService.getWinningStakes(raceId);
        if (!winningStakes.isEmpty()) {
            Double winCash = StakeUtil.getValue(winningStakes);
            Double winRatio = allCash / winCash;
            Map<Integer, Double> winningMap = winningStakes.stream()
                    .collect(Collectors.toMap(s -> s.getUser().getId(), s -> s.getStakeValue() * winRatio));
            stakeService.processWinningStakes(winningStakes, winningMap);
            stakeService.getWinningStakes(raceId)
                    .forEach(stake -> accountService.transferToUser(stake.getUser().getId(), stake.getAmount()));
            LOG.info("Winning stakes count: {}, all cash from race stakes: {}", winningStakes.size(), allCash);
        } else {
            LOG.info("Nobody wins, station revenue: {}", allCash);
        }
    }
}
