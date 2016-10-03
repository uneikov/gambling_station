package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.repository.StakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<Stake> getWinningStakes() {
        return repository.getWinningStakes();
    }

    @Override
    public boolean save(Stake stake) {
        return repository.save(stake);
    }

    @Override
    public boolean update(Stake stake) {
        return repository.update(stake);
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

}
