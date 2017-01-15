package com.uran.gamblingstation.service.scheduler;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Race;
import com.uran.gamblingstation.service.RaceService;
import com.uran.gamblingstation.service.processor.RaceProcessor;
import com.uran.gamblingstation.service.simulation.RaceFactory;
import com.uran.gamblingstation.service.simulation.RaceSimulationHelper;
import com.uran.gamblingstation.util.RandomUtil;
import com.uran.gamblingstation.util.TimeUtil;
import com.uran.gamblingstation.util.horse.HorseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
public class RaceScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(RaceScheduler.class);

    public static final int NUMBER_OF_HORSES_FOR_RACE = 6;

    private static final int MIN_BOTS = 30;
    private static final int MAX_BOTS = 50;
    private static final String START_GAMBLE = "0 0 * * * ?";
    private static final String START_RACE = "0 45 * * * ?";
    private static final String SERVICE_TIME = "0 55 * * * ?";

    private static boolean RACE_IS_RUNNING = false;
    private static boolean USERS_CAN_MAKE_STAKES = false;
    private static Race currentRace = null;

    private boolean first = true;
    private LocalDateTime start = null;
    private LocalDateTime finish = null;
    private List<Horse> horsesForRace = null;

    private final RaceSimulationHelper helper;
    private final RaceProcessor processor;
    private final RaceService raceService;

    public RaceScheduler(RaceSimulationHelper helper, RaceProcessor processor, RaceService raceService) {
        this.helper = helper;
        this.processor = processor;
        this.raceService = raceService;
    }

    //Fire every new hour (10:00, 11:00, ... etc) every day
    @Scheduled(cron = START_GAMBLE)
    public void startGamble() {
        LOG.info("Race stopped & Game Started at {}", LocalDateTime.now().format(TimeUtil.DATE_TIME_FORMATTER));

        start = LocalDateTime.now();
        RACE_IS_RUNNING = false;
        USERS_CAN_MAKE_STAKES = true;

        horsesForRace = helper.getHorsesForRace();

        currentRace = RaceFactory
                .open()
                .addStart(start)
                .addFinish(null)
                .addHorses(HorseUtil.getSerialized(horsesForRace))
                .addWinning("not yet:еще нет");
        currentRace = raceService.save(currentRace);

        if (first) {
            helper.killBots();
            helper.createBots(MAX_BOTS);
            first = false;
        } else {
            helper.initBots(MIN_BOTS, MAX_BOTS);
        }

        helper.fillWallets();
        helper.startGamble();
    }

    //Fire every new hour at *:45 every day
    @Scheduled(cron = START_RACE)
    public void startRace() {

        if (start == null) {
            return;
        }

        RACE_IS_RUNNING = true;
        USERS_CAN_MAKE_STAKES = false;

        finish = LocalDateTime.now(); // stakes to current race are done
        LOG.info("Race started at {}", finish.format(TimeUtil.DATE_TIME_FORMATTER));

        // TODO race simulation realisation & visualisation
    }

    //Fire every new hour (10:55, 11:55, ... etc) every day
    @Scheduled(cron = SERVICE_TIME)
    public void processRaceResult() {

        if (finish == null) {
            return;
        }

        RACE_IS_RUNNING = false;
        USERS_CAN_MAKE_STAKES = false;

        LOG.info("Race results processing starts at {}", LocalDateTime.now().format(TimeUtil.DATE_TIME_FORMATTER));

        Horse winning = RandomUtil.getRandomHorseFromList(horsesForRace);

        currentRace
                .addFinish(finish)
                .setWinning(HorseUtil.getSerialized(Collections.singletonList(winning)));
        raceService.update(currentRace);

        RaceFactory
                .close();

        processor.process(winning.getId(), currentRace.getId());
    }

    public static Race getCurrentRace() {
        return currentRace;
    }

    public static boolean isRaceIsRunning() {
        return RACE_IS_RUNNING;
    }

    public static boolean isUsersCanMakeStakes() {
        return USERS_CAN_MAKE_STAKES;
    }
}
