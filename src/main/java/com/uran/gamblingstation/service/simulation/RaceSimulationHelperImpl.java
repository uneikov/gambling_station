package com.uran.gamblingstation.service.simulation;

import com.uran.gamblingstation.model.*;
import com.uran.gamblingstation.service.HorseService;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.service.UserService;
import com.uran.gamblingstation.service.WalletService;
import com.uran.gamblingstation.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RaceSimulationHelperImpl implements RaceSimulationHelper{
    private static final Logger LOG = LoggerFactory.getLogger(RaceSimulationHelperImpl.class);

    @Autowired private StakeService stakeService;
    @Autowired private UserService userService;
    @Autowired private WalletService walletService;
    @Autowired
    private HorseService horseService;

    public void makeBots(){
        for (int i = 0; i < 50; i++) {
            // ----------------------------------- create random user ----------------------------------
            User user = new User(
                    null,
                    "testuser"+i,
                    "testuser"+i+"@yandex.ru",
                    "testpassword"+i,
                    true,
                    Collections.singleton(Role.ROLE_USER)
            );
            LOG.info("Create test user {}", user);
            userService.save(user);
            // ---------------------- create user`s wallet with random amount--------------------
            user.setWallet(new Wallet(user.getId(), 10 + ThreadLocalRandom.current().nextDouble(90.0)));
            walletService.save(user.getWallet());
            // -------------------------------- get random horse -----------------------------------
            Horse randomHorseForStake = RandomUtil.getRandomHorseFromList(horseService.getAll());
            // ----------------------------------- make stake -----------------------------------
            Double randomStakeValue = 10 + ThreadLocalRandom.current().nextDouble(90.0);
            if (randomStakeValue > user.getWallet().getCash()) randomStakeValue = user.getWallet().getCash();
            Double winningAmount = 0.0d;
            Stake stake = new Stake(user, randomHorseForStake, randomStakeValue, LocalDateTime.now(), false, winningAmount);
            stakeService.save(stake, user.getId());
        }
    }

    public void killBots(){
        userService.getAll().stream()
                .filter(user -> user.getName().startsWith("testuser"))
                .forEach(user -> userService.delete(user.getId()));
    }
}
