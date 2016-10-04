package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.repository.StakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StakeServiceImpl implements StakeService {

    @Autowired
    private StakeRepository repository;

    @Override
    public List<Stake> getAll() {
        return repository.getALL();
    }

    @Override
    public Double getAllCash() {
        return repository.getAllCash();
    }

    @Override
    public boolean save(Stake stake, int userId) {
        // проверка user ID
        Assert.notNull(stake, "stake must not be null");
        return repository.save(stake);
    }

    @Override
    public boolean update(Stake stake, int userId) {
        // проверка user ID
        Assert.notNull(stake, "stake must not be null");
        return repository.update(stake);
    }

    @Override
    public void setWinningStakes(Horse horse, int userId) {
        // проверка user ID
        List<Stake> list = repository.getALL().stream()
                .filter(s -> s.getHorse().equals(horse))
                .collect(Collectors.toList());
        list.forEach(s -> s.setWins(true));
        list.forEach(s -> repository.update(s));
    }

    @Override
    public List<Stake> getWinningStakes() {
        return repository.getWinningStakes();
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public Stake get(int id) {
        return repository.get(id);
    }

}
