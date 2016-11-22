package com.uran.gamblingstation.service.scheduler;


import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Race;
import com.uran.gamblingstation.service.RaceService;
import com.uran.gamblingstation.service.processor.RaceProcessor;
import com.uran.gamblingstation.service.simulation.RaceSimulationHelper;
import com.uran.gamblingstation.util.RandomUtil;
import com.uran.gamblingstation.util.TimeUtil;
import com.uran.gamblingstation.util.horse.HorseUtil;
import com.uran.gamblingstation.util.race.RaceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.uran.gamblingstation.model.BaseEntity.RACE_IS_RUNNING;
import static com.uran.gamblingstation.model.BaseEntity.USERS_CAN_MAKE_STAKES;

@Component
public class RaceScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(RaceScheduler.class);

    private static LocalDateTime START = null;
    private static LocalDateTime FINISH = null;

    private static List<Horse> horsesForRace;
    private static Race currentRace;
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

        helper.selectHorsesForRace();// random SELECT horses for race

        horsesForRace = helper.getHorsesForRace();

        currentRace = RaceFactory
                .open()
                .addStart(START)
                .addFinish(null)
                .addHorses(HorseUtil.getSerialized(horsesForRace))
                .addWinning("not yet:еще нет");
        currentRace = raceService.save(currentRace);

        //helper.killBots();
        if (FIRST) {
            helper.createBots();
            FIRST = false;
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

    //Fire every new hour (10:59:30, 11:59:30, ... etc) every day
    @Scheduled(cron = "0 59 * * * ?")
    public void processRaceResult() {

        if (FINISH == null) return;

        RACE_IS_RUNNING = false;
        USERS_CAN_MAKE_STAKES = false;

        LOG.info("Race results processing start at {}", LocalDateTime.now().format(TimeUtil.DATE_TIME_FORMATTER));

        Horse winning = RandomUtil.getRandomHorseFromList(horsesForRace);

        currentRace
                .addFinish(FINISH)
                .setWinning(HorseUtil.getSerialized(Collections.singletonList(winning)));
        raceService.update(currentRace);

        RaceFactory
                .close();

        processor.process(winning.getId(), START, FINISH);
        //processor.process(winning.getId(), currentRace.getId()); // race.id !!!

    }

    public static Race getCurrentRace(){
        return currentRace;
    }
}
