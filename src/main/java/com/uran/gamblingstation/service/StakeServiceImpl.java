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

    @Autowired
    private StakeRepository repository;

    @Override
    public Stake get(int id, int userId) {
        // проверка user ID
        return repository.get(id);
    }

    @Override
    public void delete(int id, int userId) {
        // проверка user ID
        repository.delete(id);
    }

    @Override
    public List<Stake> getAll(int userId) {
        return repository.getAll();
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
        repository.getBetween(startDate, endDate).stream()
                .filter(s -> s.getHorse().getId() == horseId)
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

    @Override //?????????????????????????????????
    public List<User> getWinningUsers(Horse horse, LocalDateTime startDate, LocalDateTime endDate){
        // проверка user ID
        return repository.getBetween(horse, startDate, endDate)
                .stream()
                .filter(s -> s.getHorse().equals(horse))
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
    public List<Stake> getBetween(User user, LocalDateTime startDate, LocalDateTime endDate) {
        // проверка user ID
        return repository.getBetween(user, startDate, endDate);
    }

    @Override
    public List<Stake> getBetween(Horse horse, LocalDateTime startDate, LocalDateTime endDate) {
        // проверка user ID
        return repository.getBetween(horse, startDate, endDate);
    }

    @Override
    public List<Stake> getBetween(User user, Horse horse, LocalDateTime startDate, LocalDateTime endDate) {
        // проверка user ID
        return repository.getBetween(user, horse, startDate, endDate);
    }

}
