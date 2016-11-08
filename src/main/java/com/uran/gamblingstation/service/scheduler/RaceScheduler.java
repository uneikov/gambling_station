package com.uran.gamblingstation.service.scheduler;


import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.service.HorseService;
import com.uran.gamblingstation.service.processor.RaceProcessor;
import com.uran.gamblingstation.service.simulation.RaceSimulationHelper;
import com.uran.gamblingstation.util.RandomUtil;
import com.uran.gamblingstation.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.uran.gamblingstation.model.BaseEntity.RACE_IS_RUNNING;

@Component
public class RaceScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(RaceScheduler.class);
    private static LocalDateTime RACE_START_TIME = null;
    private static LocalDateTime START_GAME = null;
    private static LocalDateTime END_GAME = null;

    @Autowired private HorseService horseService;
    //@Autowired private UserService userService;
    @Autowired private RaceSimulationHelper helper;
    @Autowired private RaceProcessor processor;

    //Fire every new hour at *:40 every day
    @Scheduled(cron = "0 45 * * * ?")
    public void startRace() {
        LocalDateTime start = LocalDateTime.now();
        LOG.info("Race started at {}", start.format(TimeUtil.DATE_TIME_FORMATTER));
        RACE_START_TIME = start;
        RACE_IS_RUNNING = true;
        // how really start race ??? where is a method?
    }

    //Fire every new hour (10:00, 11:00, ... etc) every day
    @Scheduled(cron = "0 0 * * * ?")
    public void stopRace() {
        LOG.info("Race stopped at {}", LocalDateTime.now().format(TimeUtil.DATE_TIME_FORMATTER));
        LOG.info("Game started at {}", LocalDateTime.now().format(TimeUtil.DATE_TIME_FORMATTER));

        RACE_IS_RUNNING = false;
        START_GAME = LocalDateTime.now();
        END_GAME = START_GAME.plusMinutes(15);
        helper.makeBots();
    }

    @Scheduled(cron = "0 59 * * * ?")
    public void processRaceResult() {
        LocalDateTime end = LocalDateTime.now();
        LOG.info("Race results processing start at {}", end.format(TimeUtil.DATE_TIME_FORMATTER));
        if (RACE_START_TIME != null){
            Horse winningHorse = RandomUtil.getRandomHorseFromList(horseService.getAll());
            processor.process(winningHorse.getId(), START_GAME, END_GAME);
        }
    }
}
