package com.uran.gamblingstation.service.simulation;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.model.Wallet;
import com.uran.gamblingstation.service.HorseService;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.service.UserService;
import com.uran.gamblingstation.service.WalletService;
import com.uran.gamblingstation.service.scheduler.RaceScheduler;
import com.uran.gamblingstation.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.uran.gamblingstation.model.BaseEntity.RACE_IS_RUNNING;
import static com.uran.gamblingstation.model.BaseEntity.USERS_CAN_MAKE_STAKES;

@Component
public class RaceSimulationHelperImpl implements RaceSimulationHelper{
    private static final Logger LOG = LoggerFactory.getLogger(RaceSimulationHelperImpl.class);

    @Autowired private StakeService stakeService;
    @Autowired private UserService userService;
    @Autowired private WalletService walletService;
    @Autowired private HorseService horseService;

    private List<User> bots = new ArrayList<>();
    private List<Horse> selectedHorses = new ArrayList<>();
    private static int count = 0;

    public void selectHorsesForRace(){
        final List<Horse> all = horseService.getAll();
        // set ready to false for all horses
        all.stream().peek(horse -> horse.setReady(false)).forEach(horse -> horseService.save(horse));
        // set ready to true for random selected horses and return as List
        selectedHorses =  RandomUtil.getHorsesForRace(all).stream()
                .peek(horse -> horse.setReady(true))
                .peek(horse -> horseService.save(horse))
                .collect(Collectors.toList());
    }

    public List<Horse> getHorsesForRace(){
        return selectedHorses;
    }

    public void createBots(){
        int botsNumber = ThreadLocalRandom.current().nextInt(30, 51);
        BotFactory botFactory = new BotFactory();
        bots = botFactory.getBots(botsNumber);
        bots.forEach(user -> userService.save(user));
    }

    public void fillWallets(){
        bots.forEach(user -> {
            Wallet botWallet = walletService.get(user.getId());
            botWallet.setCash(botWallet.getCash() + 10 + ThreadLocalRandom.current().nextDouble(90.0));
            walletService.update(botWallet);
        });
    }

    public void clearWallets(){
        bots.forEach(user -> {
            user.getWallet().setCash(0.0d);
            walletService.update(user.getWallet());
        });
    }

    public void killBots(){
        userService.getAll().stream()
                .filter(user -> user.getName().startsWith("testuser"))
                .forEach(user -> userService.delete(user.getId()));
    }

    public void startGamble(){
        final List<Integer> randomTimePoints = RandomUtil.getRandomTimePoints(0, 45, 50);
        LOG.info("Make random time points, race is running? - {}", RACE_IS_RUNNING);
        randomTimePoints.forEach(tick -> {
            while (USERS_CAN_MAKE_STAKES){
                if (tick <= LocalDateTime.now().getMinute()){
                    makeStake();
                    break;
                }
            }
        });
        count = 0;
    }

    private void makeStake() {
        User botUser = bots.get(count++);
        Double botCash = walletService.get(botUser.getId()).getCash();
        Double stakeValue = 10 + ThreadLocalRandom.current().nextDouble(90.0);
        stakeValue = stakeValue > botCash ? botCash : stakeValue;
        Horse stakeHorse = RandomUtil.getRandomHorseFromList(selectedHorses);
        stakeService.save(
                new Stake(null, botUser, stakeHorse, RaceScheduler.getCurrentRace(), stakeValue, LocalDateTime.now(), false, 0.0d, false),
                botUser.getId()
        );
        LOG.info("Bot {} make stake as big as {} at {} minute", botUser.getName(), stakeValue, LocalDateTime.now().getMinute());
    }
}
