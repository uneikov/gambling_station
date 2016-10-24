package com.uran.gamblingstation.service.time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.Future;

@Service
public class TimeProducer {
    private static final Logger LOG = LoggerFactory.getLogger(TimeProducer.class);

    @Async
    public Future<LocalDateTime> setTime() throws InterruptedException {
        LOG.info("Timer set up");
        LocalDateTime start = LocalDateTime.now();
        while (true){
            if(LocalTime.now().getSecond() == 0) {
                start = LocalDateTime.now();
               //end = start.plusMinutes(5);
                break;

            }
            Thread.sleep(1000);
            System.out.println(start);
        }

        return new AsyncResult<>(start);
    }

}