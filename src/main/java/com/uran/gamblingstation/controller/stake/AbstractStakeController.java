package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.service.HorseService;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.service.UserService;
import com.uran.gamblingstation.service.account.AccountService;
import com.uran.gamblingstation.to.StakeTo;
import com.uran.gamblingstation.util.TimeUtil;
import com.uran.gamblingstation.util.stake.StakeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.uran.gamblingstation.model.BaseEntity.ADMIN_ID;

public class AbstractStakeController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractStakeController.class);

    @Autowired private StakeService stakeService;
    //@Autowired private WalletService walletService;
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

    Horse getHorse(String name){
        return horseService.getByName(name);
    }

    public Stake get(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("get stake {} for User {}", id, userId);
        return stakeService.get(id);
    }

    // only admin can delete stakes!!!
    public void delete(int id) {
        int userId = ADMIN_ID;
        LOG.info("delete stake {} for User {}", id, userId);
        stakeService.delete(id, userId);
    }
    //---------------------------= take userId from auth service =--------------------------------
    public Stake create(Stake stake) {
        stake.setId(null);
        int userId = AuthorizedUser.id();
        stake.setUser(userService.get(userId));
        LOG.info("create {} for User {} value {}", stake, userId, stake.getStakeValue());
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        accountService.transferToStation(userId, stake.getStakeValue());
        return stakeService.save(stake, userId);
    }

    public void update(Stake stake, int id) {
        stake.setId(id);
        int userId = AuthorizedUser.id();
        LOG.info("update {} for User {}", stake, userId);
        // need to update wallet?!
        stakeService.update(stake, userId);
    }

    public Stake create(StakeTo stakeTo) {
        int userId = AuthorizedUser.id();
        User user = userService.get(userId);
        Horse horse = horseService.getByName(stakeTo.getHorseName());
        Stake stake = new Stake(null, user, horse, stakeTo.getStakeValue(), false, 0.0d);
        LOG.info("create {} for User {} value {}", stake, userId, stake.getStakeValue());
        accountService.transferToStation(userId, stake.getStakeValue());
        return stakeService.save(stake, userId);
    }

    public void update(StakeTo stakeTo) {
        int id = stakeTo.getId();
        int userId = AuthorizedUser.id();
        User user = userService.get(userId);
        Horse horse = horseService.getByName(stakeTo.getHorseName());
        Stake stake = new Stake(id, user, horse, stakeTo.getStakeValue(), false, 0.0d);
        LOG.info("update {} for User {}", stake, userId);
        // need to update wallet !!!
       updateUserAndStationWallets(stakeTo);
        stakeService.update(stake, userId);
    }

    public List<Stake> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, String option) {
        int userId = AuthorizedUser.id();
        LOG.info("getBetween dates {} - {} for scheduler {} - {} for User {}", startDate, endDate, startTime, endTime, userId);
        startDate = startDate != null ? startDate : TimeUtil.MIN_DATE;
        endDate = endDate != null ? endDate : TimeUtil.MAX_DATE;
        startTime = startTime != null ? startTime : LocalTime.MIN;
        endTime = endTime != null ? endTime : LocalTime.MAX;
        return StakeUtil.getFilteredByTimeAndWins(
                stakeService.getBetweenDateTimes(LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime), userId),
                startTime, endTime, option
        );
    }

    private void updateUserAndStationWallets(StakeTo stakeTo){
        Double oldValue = stakeService.get(stakeTo.getId()).getStakeValue();
        Double newValue = stakeTo.getStakeValue();
        Double difValue = oldValue - newValue;
        if (difValue < 0) {
            accountService.transferToStation(AuthorizedUser.id(), difValue);
        }else {
            accountService.transferToUser(AuthorizedUser.id(), difValue);
        }
    }
}

