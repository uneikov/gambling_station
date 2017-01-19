package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Race;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.repository.StakeRepository;
import com.uran.gamblingstation.service.account.AccountService;
import com.uran.gamblingstation.dto.StakeDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.uran.gamblingstation.service.scheduler.RaceScheduler.getCurrentRace;
import static com.uran.gamblingstation.util.exception.ExceptionUtil.checkNotFoundWithId;

@Service
public class StakeServiceImpl implements StakeService {

    private final StakeRepository repository;
    private final UserService userService;
    private final HorseService horseService;
    private final AccountService accountService;

    public StakeServiceImpl(StakeRepository repository,
                            UserService userService,
                            HorseService horseService,
                            AccountService accountService) {
        this.repository = repository;
        this.userService = userService;
        this.horseService = horseService;
        this.accountService = accountService;
    }

    @Override
    public Stake get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    @Transactional
    public void delete(int id, int userId) {
        Double deleted = get(id, userId).getStakeValue();
        checkNotFoundWithId(repository.delete(id, userId), id);
        accountService.transferToUser(userId, deleted);
    }

    @Override
    public List<Stake> getAll() {
        return repository.getAll();
    }

    @Override
    @Transactional
    public Stake save(Stake stake, int userId) {
        Assert.notNull(stake, "stake must not be null");
        stake.setUser(userService.get(userId));
        Stake created = repository.save(stake, userId);
        accountService.transferToStation(userId, stake.getStakeValue());
        return created;
    }

    @Override
    @Transactional
    public Stake save(StakeDTO stakeDTO, int userId) {
        final User user = userService.get(userId);
        final Horse horse = horseService.getByName(stakeDTO.getHorseName());
        final Race race = getCurrentRace();
        Stake stake = repository.save(
                new Stake(null, user, horse, race, stakeDTO.getStakeValue(), LocalDateTime.now(), false, 0.0d, true),
                userId
        );
        accountService.transferToStation(userId, stake.getStakeValue());
        return stake;
    }

    @Override
    @Transactional
    public Stake update(Stake stake, int userId) {
        Assert.notNull(stake, "stake must not be null");
        final Double oldStakeValue = repository.get(stake.getId(), userId).getStakeValue();
        Stake updated = checkNotFoundWithId(repository.save(stake, userId), stake.getId());
        updateUserAndStationWallets(oldStakeValue - updated.getStakeValue(), userId);
        return updated;
    }

    @Override
    @Transactional
    public Stake update(StakeDTO stakeDTO, int userId) {
        int id = stakeDTO.getId();
        final Double oldStakeValue = repository.get(id, userId).getStakeValue();
        final User user = userService.get(userId);
        final Horse horse = horseService.getByName(stakeDTO.getHorseName());
        final Race race = getCurrentRace();
        Stake updated = repository.save(
                new Stake(id, user, horse, race,  stakeDTO.getStakeValue(), LocalDateTime.now(), false, 0.0d, true),
                userId
        );
        updateUserAndStationWallets(oldStakeValue - updated.getStakeValue(), userId);
        return updated;
    }

    @Override
    public Stake getWithUser(int id) {
        return repository.getWithUser(id);
    }

    @Override
    public List<Stake> getAllByUserId(int userId) {
        return repository.getAllByUserId(userId);
    }

    @Override
    public List<Stake> getAllByRaceId(int raceId) {
        return repository.getAllByRaceId(raceId);
    }

    @Override
    public Double getAllCash(int raceId) {
        return repository.getAllCash(raceId);
    }

    @Override
    @Transactional
    public void setNotEditable(int raceId) {
        repository.getAllByRaceId(raceId)
                .forEach(stake -> { stake.setEditable(false); repository.update(stake); });
    }

    @Override
    @Transactional
    public void setWinningStakes(int horseId, int raceId) {
        repository.getAllByHorseIdAndRaceId(horseId, raceId)
                .forEach(stake -> {stake.setWins(true); repository.update(stake);});
    }

    @Override
    public List<Stake> getWinningStakes(int raceId) {
        return repository.getWinningStakes(raceId);
    }

    @Override
    @Transactional
    public void processWinningStakes(List<Stake> winningStakes, Map<Integer, Double> winningMap) {
        winningStakes.stream()
                .peek(stake -> stake.setAmount(winningMap.get(stake.getUser().getId())))
                .forEach(repository::update);
    }

    @Override
    public List<Stake> getAllWinningStakes() {
        return repository.getAllWinningStakes();
    }

    @Override
    public List<Stake> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(endDateTime, "endDateTime  must not be null");
        return repository.getBetween(startDateTime, endDateTime, userId);
    }

    private void updateUserAndStationWallets(Double difValue, int userId){
        if (difValue < 0) {
            accountService.transferToStation(userId, Math.abs(difValue));
        }else {
            accountService.transferToUser(userId, difValue);
        }
    }
}
