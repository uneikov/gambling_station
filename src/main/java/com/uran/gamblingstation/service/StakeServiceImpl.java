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
    public void setWinningStakes(Horse horse, LocalDateTime startDate, LocalDateTime endDate, int userId) {
        // проверка user ID
        List<Stake> list = repository.getBetween(startDate, endDate).stream()
                .filter(s -> s.getHorse().equals(horse))
                .collect(Collectors.toList());
        list.forEach(s -> s.setWins(true));
        list.forEach(s -> repository.update(s));
    }

    @Override
    public List<Stake> getWinningStakes(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.getBetween(startDate, endDate).stream()
                .filter(Stake::getWins)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getWinningUsers(Horse horse, LocalDateTime startDate, LocalDateTime endDate, int userId){
        // проверка user ID
        return repository.getBetween(horse, startDate, endDate)
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
