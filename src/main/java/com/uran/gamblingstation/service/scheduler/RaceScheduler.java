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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
public class RaceScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(RaceScheduler.class);

    public static boolean RACE_IS_RUNNING;
    public static boolean USERS_CAN_MAKE_STAKES;

    public static int NUMBER_OF_HORSES_FOR_RACE = 6;
    public static int MIN_BOTS = 30;
    public static int MAX_BOTS = 50;

    private static LocalDateTime START = null;
    private static LocalDateTime FINISH = null;

    private static List<Horse> horsesForRace = null;
    private static Race currentRace = null;
    private static boolean FIRST = true;

    @Autowired private RaceSimulationHelper helper;
    @Autowired private RaceProcessor processor;
    @Autowired private RaceService raceService;

    //Fire every new hour (10:00, 11:00, ... etc) every day
    @Scheduled(cron = "0 0 * * * ?")
    public void startGamble() {
        LOG.info("Race stopped & Game Started at {}", LocalDateTime.now().format(TimeUtil.DATE_TIME_FORMATTER));

        START = LocalDateTime.now();
        RACE_IS_RUNNING = false;
        USERS_CAN_MAKE_STAKES = true;

        helper.selectHorsesForRace();

        horsesForRace = helper.getHorsesForRace();

        currentRace = RaceFactory
                .open()
                .addStart(START)
                .addFinish(null)
                .addHorses(HorseUtil.getSerialized(horsesForRace))
                .addWinning("not yet:еще нет");
        currentRace = raceService.save(currentRace);

        if (FIRST) {
            helper.killBots();
            helper.createBots(MAX_BOTS);
            FIRST = false;
        }else {
            helper.initBots(MIN_BOTS, MAX_BOTS);
        }

        helper.fillWallets();
        helper.startGamble();
    }

    //Fire every new hour at *:45 every day
    @Scheduled(cron = "0 45 * * * ?")
    public void startRace() {

        if (START == null) return;

        RACE_IS_RUNNING = true;
        USERS_CAN_MAKE_STAKES=false;

        FINISH = LocalDateTime.now(); // stakes to current race are done
        LOG.info("Race started at {}", FINISH.format(TimeUtil.DATE_TIME_FORMATTER));

        // TODO race simulation realisation & visualisation
    }

    //Fire every new hour (10:55, 11:55, ... etc) every day
    @Scheduled(cron = "0 55 * * * ?")
    public void processRaceResult() {

        if (FINISH == null) return;

        RACE_IS_RUNNING = false;
        USERS_CAN_MAKE_STAKES = false;

        LOG.info("Race results processing starts at {}", LocalDateTime.now().format(TimeUtil.DATE_TIME_FORMATTER));

        Horse winning = RandomUtil.getRandomHorseFromList(horsesForRace);

        currentRace
                .addFinish(FINISH)
                .setWinning(HorseUtil.getSerialized(Collections.singletonList(winning)));
        raceService.update(currentRace);

        RaceFactory
                .close();

        processor.process(winning.getId(), currentRace.getId());
    }

    public static Race getCurrentRace(){
        return currentRace;
    }
}
