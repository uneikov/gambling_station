package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.repository.StakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StakeServiceImpl implements StakeService {

    @Autowired private StakeRepository repository;

    @Override
    public Stake get(int id) {
        return repository.get(id);
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public List<Stake> getAll() {
        return repository.getAll();
    }

    @Override
    public Stake save(Stake stake) {
        Assert.notNull(stake, "stake must not be null");
        return repository.save(stake);
    }

    @Override
    public void update(Stake stake) {
        Assert.notNull(stake, "stake must not be null");
        repository.update(stake);
    }

    public List<Stake> getAllByUserId(int userId) {
        return repository.getAllByUserId(userId);
    }

    @Override
    public List<Stake> getAllByRaceId(int raceId) {
        return repository.getAllByRaceId(raceId);
    }

    @Override
    public Double getAllCash(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.getAllCash(startDate, endDate);
    }

    @Override
    public Double getAllCash(int raceId) {
        return repository.getAllCash(raceId);
    }

    @Override
    public void setNotEditable(LocalDateTime start, LocalDateTime finish) {
        getBetween(start, finish).forEach(stake -> { stake.setEditable(false); repository.update(stake); });
    }

    @Override
    public void setNotEditable(int raceId) {
        repository.getAllByRaceId(raceId).forEach(stake -> { stake.setEditable(false); repository.update(stake); });
    }

    @Override
    public void setWinningStakes(int horseId, LocalDateTime startDate, LocalDateTime endDate) {
        repository.getBetweenWithHorse(horseId, startDate, endDate).stream()
                .peek(s -> s.setWins(true))
                .forEach(s -> repository.update(s));
    }

    @Override
    public void setWinningStakes(int horseId, int raceId) {
        repository.getAllByHorseIdAndRaceId(horseId, raceId)
                .forEach(stake -> {stake.setWins(true); repository.update(stake);});
    }

    @Override
    public List<Stake> getWinningStakes(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.getBetween(startDate, endDate).stream()
                .filter(Stake::isWins)
                .collect(Collectors.toList());
    }

    @Override
    public List<Stake> getWinningStakes(int raceId) {
        return repository.getWinningStakes(raceId);
    }

    @Override
    public List<Stake> getLoosingStakes(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.getBetween(startDate, endDate).stream()
                .filter(stake -> !stake.isWins())
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
        return repository.getBetweenWithHorse(horseId, startDate, endDate)
                .stream()
                .map(Stake::getUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<Stake> getAllWinningStakes() {
        return repository.getAllWinningStakes();
    }

    @Override
    public List<Stake> getBetween(LocalDateTime startDate, LocalDateTime endDate) {
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
        return repository.getBetween(user, startDate, endDate);
    }

    @Override
    public List<Stake> getBetween(Horse horse, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.getBetweenWithHorse(horse.getId(), startDate, endDate);
    }

    @Override
    public List<Stake> getBetween(User user, Horse horse, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.getBetween(user, horse, startDate, endDate);
    }

}
