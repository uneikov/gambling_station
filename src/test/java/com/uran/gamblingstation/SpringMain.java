package com.uran.gamblingstation;

import com.uran.gamblingstation.controller.RootController;
import com.uran.gamblingstation.controller.horse.HorseRestController;
import com.uran.gamblingstation.controller.stake.StakeRestController;
import com.uran.gamblingstation.controller.user.AdminRestController;
import com.uran.gamblingstation.controller.wallet.WalletRestController;
import com.uran.gamblingstation.model.*;
import com.uran.gamblingstation.util.RandomUtil;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.uran.gamblingstation.model.BaseEntity.START_SEQ;
import static com.uran.gamblingstation.model.BaseEntity.WALLET_ID;

public class SpringMain {
   /* private static UserService userService = new UserServiceImpl();
    private static WalletService walletService = new WalletServiceImpl();
    private static StakeService stakeService = new StakeServiceImpl();
    private static HorseService horseService = new HorseServiceImpl();*/

    private static boolean RACE_DATA_READY = false;
    private static final int HORSE_1_ID = START_SEQ + 4;

    private static final Horse HORSE_1 = new Horse(HORSE_1_ID, "Black Ghost", "Черный призрак", 5, 0);
    private static final Horse HORSE_2 = new Horse(HORSE_1_ID + 1, "White Ghost", "Белый призрак", 5, 0);
    private static final Horse HORSE_3 = new Horse(HORSE_1_ID + 2, "Enisei", "Енисей", 3, 0);
    private static final Horse HORSE_4 = new Horse(HORSE_1_ID + 3, "Thunderbird", "Гром", 5, 0);
    private static final Horse HORSE_5 = new Horse(HORSE_1_ID + 4, "Ruby Rose", "Рубироуз", 4, 0);
    private static final Horse HORSE_6 = new Horse(HORSE_1_ID + 5, "Predator", "Хищник", 5, 0);
    private static final Horse HORSE_7 = new Horse(HORSE_1_ID + 6, "Alien", "Чужой", 6, 0);
    private static final Horse HORSE_8 = new Horse(HORSE_1_ID + 7, "Gulfstream", "Гольфстрим", 3, 0);
    private static final Horse HORSE_9 = new Horse(HORSE_1_ID + 8, "Rabindranate", "Рабиндранат", 5, 0);
    private static final Horse HORSE_10 = new Horse(HORSE_1_ID + 9, "Angelfire", "Энджелфае", 5, 0);

    public static final Horse HORSE_ADDED = new Horse(HORSE_1_ID + 10, "Captain", "Капитан", 5, 0);

    private static Horse WINNING_HORSE;
    private static List<User> users = new ArrayList<>();
    private static List<Stake> stakes = new ArrayList<>();

    private static LocalDateTime VALID_START;
    private static LocalDateTime VALID_END;

    private static final List<Horse> HORSES =
            Arrays.asList(HORSE_7, HORSE_10, HORSE_1, HORSE_3, HORSE_8, HORSE_6, HORSE_9, HORSE_5, HORSE_4, HORSE_2);

    public static List<Horse> getHorsesForRace() {
        return RandomUtil.getHorsesListForRace(new ArrayList<>(HORSES));
    }

    public static Horse getWinningHorse(List<Horse> horses) {
        return RandomUtil.getRandomHorseFromList(horses);
    }

    public static void main(String[] args) {

        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx =
                     new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/spring-database.xml")) {
            AdminRestController userRestController = appCtx.getBean(AdminRestController.class);
            HorseRestController horseRestController = appCtx.getBean(HorseRestController.class);
            StakeRestController stakeController = appCtx.getBean(StakeRestController.class);
            WalletRestController walletController = appCtx.getBean(WalletRestController.class);
            RootController rootController = appCtx.getBean(RootController.class);

            VALID_START = LocalDateTime.now();
            VALID_END = VALID_START.plusMinutes(10);

           /* while (RACE_DATA_READY){*/
                for (int i = 0; i < 50; i++) {
                    // ----------------------------------- create random user ----------------------------------
                    User user = new User(null, "testuser"+i, "testuser"+i+"@yandex.ru", "testpassword"+i, Collections.singleton(Role.ROLE_USER));
                    userRestController.create(user);
                    // ---------------------- create user`s wallet with random amount--------------------
                    user.setWallet(new Wallet(user.getId(), 10 + ThreadLocalRandom.current().nextDouble(90.0)));
                    walletController.create(user.getWallet(), user.getId());
                    // -------------------------------- get random horse -----------------------------------
                    Horse randomHorseForStake = RandomUtil.getRandomHorseFromList(HORSES);
                    // ----------------------------------- make stake -----------------------------------
                    Double randomStakeValue = 10 + ThreadLocalRandom.current().nextDouble(90.0);
                    if (randomStakeValue > user.getWallet().getCash()) randomStakeValue = user.getWallet().getCash();
                    Double winningAmount = 0.0d;
                    Stake stake = new Stake(user, randomHorseForStake, randomStakeValue, LocalDateTime.now(), false, winningAmount);
                    stakeController.create(stake, user.getId());
                    // ------------------------------------------------------------------------------------
                    /*users.add(user);
                    stakes.add(stake);*/
                }
                // ----------------------- get winning horse & process race results -----------------------
                WINNING_HORSE = getWinningHorse(getHorsesForRace());
                rootController.processRaceResults(WINNING_HORSE, VALID_START, VALID_END);
                //------------------------------------ print test results ---------------------------------
                List<Stake> winningStakes = stakeController.getWinningStakes(VALID_START, VALID_END);
                Double cash = stakeController.getAllStakesCash();
                Double sum = winningStakes.stream().mapToDouble(Stake::getStakeValue).sum();
                Double stationCash = walletController.get(WALLET_ID).getCash();
                System.out.println("\nПервой пришла лошадь " + WINNING_HORSE.getName());
                System.out.println("\nРазыграна сумма ставок  " + cash);
                System.out.println("\nОбщая сумма выигрыша   " + sum);
                System.out.println("\nВыигрыш организатора   " + stationCash);
                System.out.println("\nА вот и наши победители:\n");
                winningStakes.forEach(s -> System.out.println(s.getUser().getName() +" сумма выигрыша - " + s.getAmount()));
                System.out.println();
               /* List<User> winningUsers = winningStakes.stream().map(Stake::getUser).collect(Collectors.toList());*/

                RACE_DATA_READY = false;

           /* }*/

            /*new Thread() {
                Map<Integer, Double> winningMap = new HashMap<>();

                @Override
                public void run() {

                    while(true) {
                        try {
                            AdminRestController userRestController = appCtx.getBean(AdminRestController.class);
                            //HorseRestController horseRestController = appCtx.getBean(HorseRestController.class);
                            StakeRestController stakeController = appCtx.getBean(StakeRestController.class);
                            WalletRestController walletRestController = appCtx.getBean(WalletRestController.class);
                            Thread.sleep(1000);

                            if(LocalTime.now().getSecond() == 0) {
                                VALID_START = LocalDateTime.now();
                                VALID_END = VALID_START.plusMinutes(1);
                                RACE_DATA_READY = true;
                                for (int i = 0; i < 50; i++) {
                                    User user = new User(null, "user"+i, "user"+i+"@yandex.ru", "password"+i, Collections.singleton(Role.ROLE_USER));
                                    System.out.println(user.toString());
                                    userRestController.create(user);
                                    user.setWallet(new Wallet(user.getId(), ThreadLocalRandom.current().nextDouble(100.0)));
                                    walletRestController.create(user.getWallet(), user.getId());
                                    Stake stake = new Stake(user, RandomUtil.getRandomHorseFromList(HORSES), ThreadLocalRandom.current().nextDouble(100.0), LocalDateTime.now(), false, 0.0d);
                                    stakeController.create(stake, user.getId());
                                    users.add(user);
                                }
                                WINNING_HORSE = getWinningHorse(getHorsesForRace());
                                stakeController.setWinningStakes(WINNING_HORSE, VALID_START, VALID_END, START_SEQ);
                                List<Stake> beforeList = stakeController.getWinningStakes(VALID_START, VALID_END);
                                beforeList.forEach(stake -> winningMap.put(stake.getUser().getId(), 100.0));
                                stakeController.processWinningStakes(beforeList, winningMap);
                                List<Stake> afterList = stakeController.getWinningStakes(VALID_START_DATETIME, VALID_END_DATETIME);
                                System.out.println(WINNING_HORSE);
                                afterList.forEach(System.out::println);
                                Thread.sleep(1000);
                            *//*System.out.println(WINNING_HORSE.toString());*//*
                            }
                        } catch (InterruptedException ie) {}
                    }
                }
            }.start();*/

        }
    }
}
