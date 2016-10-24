package com.uran.gamblingstation.controller;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.util.TimeUtil;
import com.uran.gamblingstation.util.stake.StakeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AbstractStakeController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractStakeController.class);

    @Autowired
    private StakeService service;

    public Stake get(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("get stake {} for User {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("delete stake {} for User {}", id, userId);
        service.delete(id, userId);
    }

    public List<Stake> getAll() {
        //int userId = AuthorizedUser.id();
        LOG.info("getAll for all users");
        return service.getAll();
    }

    public List<Stake> getAllByUserId() {
        int userId = AuthorizedUser.id();
        LOG.info("getAll for User {}", userId);
        return service.getAllByUserId(userId);
    }

    public void update(Stake stake, int id) {
        stake.setId(id);
        int userId = AuthorizedUser.id();
        LOG.info("update {} for User {}", stake, userId);
        service.update(stake, userId);
    }

    public Stake create(Stake stake) {
        stake.setId(null);
        int userId = AuthorizedUser.id();
        LOG.info("create {} for User {}", stake, userId);
        return service.save(stake, userId);
    }
    // make sequential filtration by date,  then by time and filter option
    public List<Stake> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, String option) {
        int userId = AuthorizedUser.id();
        LOG.info("getBetween dates {} - {} for time {} - {} for User {}", startDate, endDate, startTime, endTime, userId);
        startDate = startDate != null ? startDate : TimeUtil.MIN_DATE;
        endDate = endDate != null ? endDate : TimeUtil.MAX_DATE;
        startTime = startTime != null ? startTime : LocalTime.MIN;
        endTime = endTime != null ? endTime : LocalTime.MAX;
        return StakeUtil.getFilteredByTimeAndWins(
                service.getBetweenDateTimes(LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime), userId),
                startTime, endTime, option
        );
    }
}
