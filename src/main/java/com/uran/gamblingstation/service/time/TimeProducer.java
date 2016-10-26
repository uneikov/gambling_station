package com.uran.gamblingstation.service.time;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/*public class TimeProducer {
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

}*/


//@Component
public class TimeProducer implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(TimeProducer.class);

    public TimeProducer() {}

    @Autowired
    private TimeJobService timeJobService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOG.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
        timeJobService.executeJob();
        LOG.info("Next job scheduled @ {}", context.getNextFireTime());
    }
}
