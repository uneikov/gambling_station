package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.to.StakeTo;
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

    @Autowired
    private StakeService service;

    public List<Stake> getAll() {
        LOG.info("getAll for all users");
        return service.getAll();
    }

    public List<Stake> getAllByUserId(final int userId) {
        LOG.info("getAll for User {}", userId);
        return service.getAllByUserId(userId);
    }

    public List<Stake> getAllForAuthorizedUser() {
        int userId = AuthorizedUser.id();
        LOG.info("getAll for User {}", userId);
        return service.getAllByUserId(userId);
    }

    List<Stake> getAllByRaceId(final int raceId) {
        LOG.info("getAll for Race {}", raceId);
        return service.getAllByRaceId(raceId);
    }

    public Stake get(final int id) {
        int userId = AuthorizedUser.id();
        LOG.info("get stake {} for User {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(final int id) {
        int userId = AuthorizedUser.id();
        LOG.info("delete stake {} for User {}", id, userId);
        service.delete(id, userId);
    }

    public Stake create(final Stake stake) {
        stake.setId(null);
        int userId = AuthorizedUser.id();
        LOG.info("create {} for User {} value {}", stake, userId, stake.getStakeValue());
        return service.save(stake, userId);
    }

    public Stake create(final StakeTo stakeTo) {
        int userId = AuthorizedUser.id();
        return service.save(stakeTo, userId);
    }

    public void update(final Stake stake, final int id) {
        stake.setId(id);
        int userId = AuthorizedUser.id();
        LOG.info("update {} for User {}", stake, userId);
        service.update(stake, userId);
    }

    public void update(final StakeTo stakeTo) {
        int userId = AuthorizedUser.id();
        service.update(stakeTo, userId);
    }

    public List<Stake> getBetween(final LocalDate startDate,
                                  final LocalTime startTime,
                                  final LocalDate endDate,
                                  final LocalTime endTime,
                                  final String option) {
        int userId = AuthorizedUser.id();
        LOG.info("getBetween dates {} - {} between times {} - {} for {}, for User {}", startDate, endDate, startTime, endTime, option, userId);
        LocalDate sDate = startDate != null ? startDate : LocalDate.MIN;
        LocalDate eDate = endDate != null ? endDate : LocalDate.MAX;
        LocalTime sTime = startTime != null ? startTime : LocalTime.MIN;
        LocalTime eTime = endTime != null ? endTime : LocalTime.MAX;
        return StakeUtil.getFilteredByTimeAndWins(
                service.getBetweenDateTimes(of(sDate, sTime), of(eDate, eTime), userId),
                startTime, endTime, option
        );
    }

}

