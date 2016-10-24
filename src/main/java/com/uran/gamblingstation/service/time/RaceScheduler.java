package com.uran.gamblingstation.service.time;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RaceScheduler {

    @Scheduled(fixedDelay=1000)
    public void time(){

        System.out.println("fifififififififif");
    }
}
