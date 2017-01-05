package com.uran.gamblingstation.service.simulation;

import com.uran.gamblingstation.model.*;
import com.uran.gamblingstation.service.HorseService;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.service.UserService;
import com.uran.gamblingstation.service.WalletService;
import com.uran.gamblingstation.service.scheduler.RaceScheduler;
import com.uran.gamblingstation.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.uran.gamblingstation.service.scheduler.RaceScheduler.RACE_IS_RUNNING;
import static com.uran.gamblingstation.service.scheduler.RaceScheduler.USERS_CAN_MAKE_STAKES;

@Component
public class RaceSimulationHelperImpl implements RaceSimulationHelper{
    private static final Logger LOG = LoggerFactory.getLogger(RaceSimulationHelperImpl.class);

    private final StakeService stakeService;
    private final UserService userService;
    private final WalletService walletService;
    private final HorseService horseService;

    public RaceSimulationHelperImpl(StakeService stakeService,
                                    UserService userService,
                                    WalletService walletService,
                                    HorseService horseService) {
        this.stakeService = stakeService;
        this.userService = userService;
        this.walletService = walletService;
        this.horseService = horseService;
    }

    private List<User> bots = new ArrayList<>();
    private List<Horse> selectedHorses = new ArrayList<>();
    private static int botsNumber = 0;
    private static int count = 0;

    @Override
    @Transactional
    public void selectHorsesForRace(){
        final List<Horse> all = horseService.getAll();
        // set ready to false for all horses
        all.stream().peek(horse -> horse.setReady(false)).forEach(horseService::save);
        // set ready to true for random selected horses and return as List
        selectedHorses =  RandomUtil.getHorsesForRace(all).stream()
                .peek(horse -> horse.setReady(true))
                .peek(horseService::save)
                .collect(Collectors.toList());
    }

    @Override
    public List<Horse> getHorsesForRace(){
        return selectedHorses;
    }

    @Override
    public void createBots(int max){
        botsNumber = max;
        bots = new BotFactory().getBots(botsNumber);
        bots.forEach(userService::save);
    }

    @Override
    public void initBots(int min, int max){
        botsNumber = ThreadLocalRandom.current().nextInt(min, max);
    }

    @Override
    @Transactional
    public void fillWallets(){
        bots.forEach(user -> {
            Wallet botWallet = walletService.get(user.getId());
            botWallet.setCash(botWallet.getCash() + 10 + ThreadLocalRandom.current().nextDouble(90.0));
            walletService.update(botWallet);
        });
    }

    @Override
    public void clearWallets(){
        bots.forEach(user -> {
            user.getWallet().setCash(0.0d);
            walletService.update(user.getWallet());
        });
    }

    @Override
    public void killBots(){
        userService.getAll().stream()
                .filter(user -> user.getName().startsWith("testuser"))
                .forEach(user -> userService.delete(user.getId()));
    }

    @Override
    public void startGamble(){
        final List<Integer> randomTimePoints = RandomUtil.getRandomTimePoints(0, 45, botsNumber);
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
        Race currentRace = RaceScheduler.getCurrentRace();
        final Stake stake = stakeService.save(
                new Stake(null, botUser, stakeHorse, currentRace, stakeValue, LocalDateTime.now(), false, 0.0d, false),
                botUser.getId()
        );
        LOG.info("Bot {} make stake as big as {} at {} minute", botUser.getName(), stakeValue, stake.getDateTime().getMinute());
    }
}
