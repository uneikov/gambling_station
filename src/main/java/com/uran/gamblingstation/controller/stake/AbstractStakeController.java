package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.to.StakeDTO;
import com.uran.gamblingstation.util.TimeUtil;
import com.uran.gamblingstation.util.stake.StakeUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.slf4j.LoggerFactory.getLogger;

public class AbstractStakeController {
    private static final Logger LOG = getLogger(AbstractStakeController.class);

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

    public Stake create(final StakeDTO stakeDTO) {
        int userId = AuthorizedUser.id();
        return service.save(stakeDTO, userId);
    }

    public void update(final Stake stake, final int id) {
        stake.setId(id);
        int userId = AuthorizedUser.id();
        LOG.info("update {} for User {}", stake, userId);
        service.update(stake, userId);
    }

    public void update(final StakeDTO stakeDTO) {
        int userId = AuthorizedUser.id();
        service.update(stakeDTO, userId);
    }

    public List<Stake> getBetween(final LocalDate startDate,
                                  final LocalTime startTime,
                                  final LocalDate endDate,
                                  final LocalTime endTime,
                                  final String option) {
        int userId = AuthorizedUser.id();
        LOG.info("getBetween dates {} - {} between times {} - {} for {}, with {} for User {}", startDate, endDate, startTime, endTime, option, userId);
        LocalDate sDate = startDate != null ? startDate : TimeUtil.MIN_DATE;
        LocalDate eDate = endDate != null ? endDate : TimeUtil.MAX_DATE;
        LocalTime sTime = startTime != null ? startTime : LocalTime.MIN;
        LocalTime eTime = endTime != null ? endTime : LocalTime.MAX;
        return StakeUtil.getFilteredByWins(
                service.getBetweenDateTimes(of(sDate, sTime), of(eDate, eTime), userId),
                option
        );
    }

}

