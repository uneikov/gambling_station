package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.repository.StakeRepository;
import com.uran.gamblingstation.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.uran.gamblingstation.model.BaseEntity.ADMIN_ID;

@Service
public class StakeServiceImpl implements StakeService {

    @Autowired
    private StakeRepository repository;

    @Override
    public Stake get(int id, int userId) {
        // проверка user ID
        return repository.get(id);
    }

    @Override // only admin can delete stakes!!!
    public void delete(int id, int userId) {
        if (userId != ADMIN_ID) throw new NotFoundException("Unauthorized operation!");
        // проверка user ID
        //ExceptionUtil.checkNotFoundWithId(repository.delete(id, userId), id);
        repository.delete(id);
    }

    @Override
    public List<Stake> getAll() {
        return repository.getAll();
    }

    public List<Stake> getAllByUserId(int userId) {
        return repository.getAllByUserId(userId);
    }

    @Override
    public Double getAllCash() {
        return repository.getAllCash();
    }

    @Override
    public Stake save(Stake stake, int userId) {
        // проверка user ID
        Assert.notNull(stake, "stake must not be null");
        return repository.save(stake);
    }

    @Override
    public void update(Stake stake, int userId) {
        // проверка user ID
        Assert.notNull(stake, "stake must not be null");
        repository.update(stake);
    }

    @Override
    public void setWinningStakes(int horseId, LocalDateTime startDate, LocalDateTime endDate) {
        repository.getBetweenWithHorse(horseId, startDate, endDate).stream()
                //.filter(s -> s.getHorse().getId() == horseId)
                .peek(s -> s.setWins(true))
                .forEach(s -> repository.update(s));
    }

    @Override
    public List<Stake> getWinningStakes(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.getBetween(startDate, endDate).stream()
                .filter(Stake::getWins)
                .collect(Collectors.toList());
    }

    @Override
    public List<Stake> getLoosingStakes(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.getBetween(startDate, endDate).stream()
                .filter(s -> !s.getWins())
                .collect(Collectors.toList());
    }

    @Override
    public void processWinningStakes(List<Stake> winningStakes, Map<Integer, Double> winningMap) {
        winningStakes.stream()
                .peek(stake -> stake.setAmount(winningMap.get(stake.getUser().getId())))
                .forEach(stake -> repository.update(stake));
    }

    @Override
    public List<User> getWinningUsers(int horseId, LocalDateTime startDate, LocalDateTime endDate){
        // проверка user ID
        return repository.getBetweenWithHorse(horseId, startDate, endDate)
                .stream()
                //.filter(s -> s.getHorse().equals(horseId))
                .map(Stake::getUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<Stake> getAllWinningStakes() {
        return repository.getAllWinningStakes();
    }

    @Override
    public List<Stake> getBetween(LocalDateTime startDate, LocalDateTime endDate) {
        // проверка user ID
        return repository.getBetween(startDate, endDate);
    }

    @Override
    public List<Stake> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(endDateTime, "endDateTime  must not be null");
        return repository.getBetween(startDateTime, endDateTime, userId);
    }

    @Override
    public List<Stake> getBetween(User user, LocalDateTime startDate, LocalDateTime endDate) {
        // проверка user ID
        return repository.getBetween(user, startDate, endDate);
    }

    @Override
    public List<Stake> getBetween(Horse horse, LocalDateTime startDate, LocalDateTime endDate) {
        // проверка user ID
        return repository.getBetweenWithHorse(horse.getId(), startDate, endDate);
    }

    @Override
    public List<Stake> getBetween(User user, Horse horse, LocalDateTime startDate, LocalDateTime endDate) {
        // проверка user ID
        return repository.getBetween(user, horse, startDate, endDate);
    }

}
