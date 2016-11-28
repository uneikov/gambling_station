package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Race;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.service.HorseService;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.service.UserService;
import com.uran.gamblingstation.service.account.AccountService;
import com.uran.gamblingstation.service.scheduler.RaceScheduler;
import com.uran.gamblingstation.to.StakeTo;
import com.uran.gamblingstation.util.TimeUtil;
import com.uran.gamblingstation.util.stake.StakeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AbstractStakeController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractStakeController.class);

    @Autowired private StakeService stakeService;
    @Autowired private UserService userService;
    @Autowired private AccountService accountService;
    @Autowired private HorseService horseService;

    public List<Stake> getAll() {
        LOG.info("getAll for all users");
        return stakeService.getAll();
    }

    public List<Stake> getAllByUserId(int userId) {
        LOG.info("getAll for User {}", userId);
        return stakeService.getAllByUserId(userId);
    }

    public Stake get(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("get stake {} for User {}", id, userId);
        return stakeService.get(id);
    }

    // only admin can delete stakes !!!
    // chained transactional operations !!!
    @Transactional
    public void delete(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("delete stake {} for User {}", id, userId);
        //update wallet!!
        // move to service?
        final Double deleted = stakeService.get(id).getStakeValue();
        stakeService.delete(id);                // transaction #2
        accountService.transferToUser(userId, deleted); // transaction #1
    }

    public Stake create(Stake stake) {
        stake.setId(null);
        int userId = AuthorizedUser.id();
        stake.setUser(userService.get(userId));
        LOG.info("create {} for User {} value {}", stake, userId, stake.getStakeValue());
        //!!!!!!!!!!!!!!!!!!!!!!= need to update wallet =!!!!!!!!!!!!!!!!!!!!!!!!
        final Stake created = stakeService.save(stake);
        accountService.transferToStation(userId, stake.getStakeValue());
        return created;
    }

    public void update(Stake stake, int id) {
        stake.setId(id);
        int userId = AuthorizedUser.id();
        LOG.info("update {} for User {}", stake, userId);
        //!!!!!!!!!!!!!!!!!!!!!!= need to update wallet =!!!!!!!!!!!!!!!!!!!!!!!!
        stakeService.update(stake);
        updateUserAndStationWallets(stakeService.get(id).getStakeValue() - stake.getStakeValue());
    }

    @Transactional
    public Stake create(StakeTo stakeTo) {
        int userId = AuthorizedUser.id();
        final User user = userService.get(userId);
        final Horse horse = horseService.getByName(stakeTo.getHorseName());
        final Race race = RaceScheduler.getCurrentRace();
        Stake stake = new Stake(null, user, horse, race, stakeTo.getStakeValue(), LocalDateTime.now(), false, 0.0d, true);
        LOG.info("create {} for User {} value {}", stake, userId, stake.getStakeValue());

        final Stake created = stakeService.save(stake);

       /* final List<Stake> stakes = raceService.get(race.getId()).getStakes();
        stakes.add(stake);
        race.setStakes(stakes);
        raceService.update(race);*/

        accountService.transferToStation(userId, stake.getStakeValue());
        return created;
    }

    @Transactional
    public void update(StakeTo stakeTo) {
        int id = stakeTo.getId();
        final User user = userService.get(AuthorizedUser.id());
        final Horse horse = horseService.getByName(stakeTo.getHorseName());
        //final Race race = RaceScheduler.getCurrentRace();
        //Double newStakeValue = stakeTo.getStakeValue() +
        final Race race = stakeService.get(stakeTo.getId()).getRace();
        Stake stake = new Stake(id, user, horse, race,  stakeTo.getStakeValue(), LocalDateTime.now(), false, 0.0d, true);
        LOG.info("update {} for User {}", stake, user);
        updateUserAndStationWallets(stakeService.get(id).getStakeValue() - stakeTo.getStakeValue());
        stakeService.update(stake);


        /*final List<Stake> stakes = raceService.get(race.getId()).getStakes();
        stakes.set(id, stake);
        race.setStakes(stakes);
        raceService.update(race);*/

        //update wallet

    }

    @Transactional
    public List<Stake> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, String option) {
        int userId = AuthorizedUser.id();
        LOG.info("getBetween dates {} - {} between times {} - {} for {}, for User {}", startDate, endDate, startTime, endTime, option, userId);
        startDate = startDate != null ? startDate : TimeUtil.MIN_DATE;
        endDate = endDate != null ? endDate : TimeUtil.MAX_DATE;
        startTime = startTime != null ? startTime : LocalTime.MIN;
        endTime = endTime != null ? endTime : LocalTime.MAX;
        return StakeUtil.getFilteredByTimeAndWins(
                stakeService.getBetweenDateTimes(LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime), userId),
                startTime, endTime, option
        );
    }

    private void updateUserAndStationWallets(Double difValue){
        if (difValue < 0) {
            accountService.transferToStation(AuthorizedUser.id(), Math.abs(difValue));
        }else {
            accountService.transferToUser(AuthorizedUser.id(), difValue);
        }
    }
}

