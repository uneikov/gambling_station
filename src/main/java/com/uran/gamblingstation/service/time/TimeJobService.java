package com.uran.gamblingstation.service.time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Service
public class TimeJobService {
    private static final Logger LOG = LoggerFactory.getLogger(TimeJobService.class);
    public void executeJob() {

        LOG.info("The sample job has begun...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LOG.error("Error while executing sample job", e);
        } finally {
            LOG.info("Sample job has finished...");
        }
    }
}
