package com.uran.gamblingstation.service.time;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RaceScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(RaceScheduler.class);

    @Scheduled(fixedDelay = 10000)
    public void fixedDelaySchedule() {
        LOG.info("fixedDelaySchedule every 10 seconds" + LocalDateTime.now());
    }

    //every 30 seconds (seconds, minutes, hours, day of month, month, day of week, year(optional))
    @Scheduled(cron = "0/30 * * * * ?")
    public void cronSchedule() {
        System.out.println("cronSchedule every 30 seconds" + LocalDateTime.now());
    }

}
