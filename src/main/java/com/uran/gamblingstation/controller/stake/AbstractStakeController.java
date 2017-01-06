package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.to.StakeTo;
import com.uran.gamblingstation.util.TimeUtil;
import com.uran.gamblingstation.util.stake.StakeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.time.LocalDateTime.of;

public class AbstractStakeController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractStakeController.class);

    @Autowired private StakeService service;

    public List<Stake> getAll() {
        LOG.info("getAll for all users");
        return service.getAll();
    }

    public List<Stake> getAllByUserId(int userId) {
        LOG.info("getAll for User {}", userId);
        return service.getAllByUserId(userId);
    }

    public List<Stake> getAllForAuthorizedUser() {
        int userId = AuthorizedUser.id();
        LOG.info("getAll for User {}", userId);
        return service.getAllByUserId(userId);
    }

    List<Stake> getAllByRaceId(int raceId) {
        LOG.info("getAll for Race {}", raceId);
        return service.getAllByRaceId(raceId);
    }

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

    public Stake create(Stake stake) {
        stake.setId(null);
        int userId = AuthorizedUser.id();
        LOG.info("create {} for User {} value {}", stake, userId, stake.getStakeValue());
        return service.save(stake, userId);
    }

    public Stake create(StakeTo stakeTo) {
        int userId = AuthorizedUser.id();
        return service.save(stakeTo, userId);
    }

    public void update(Stake stake, int id) {
        stake.setId(id);
        int userId = AuthorizedUser.id();
        LOG.info("update {} for User {}", stake, userId);
        service.update(stake, userId);
    }

    public void update(StakeTo stakeTo) {
        int userId = AuthorizedUser.id();
        service.update(stakeTo, userId);
    }

    public List<Stake> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, String option) {
        int userId = AuthorizedUser.id();
        LOG.info("getBetween dates {} - {} between times {} - {} for {}, for User {}", startDate, endDate, startTime, endTime, option, userId);
        startDate = startDate != null ? startDate : TimeUtil.MIN_DATE;
        endDate = endDate != null ? endDate : TimeUtil.MAX_DATE;
        startTime = startTime != null ? startTime : LocalTime.MIN;
        endTime = endTime != null ? endTime : LocalTime.MAX;
        return StakeUtil.getFilteredByTimeAndWins(
                service.getBetweenDateTimes(of(startDate, startTime), of(endDate, endTime), userId),
                startTime, endTime, option
        );
    }

}

