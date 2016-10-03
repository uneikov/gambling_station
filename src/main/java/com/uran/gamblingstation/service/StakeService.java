package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Stake;

import java.util.List;

public interface StakeService {

    List<Stake> getAll();
    Double getAllCash();
    List<Stake> getWinningStakes();
    boolean save(Stake stake);
    boolean update(Stake stake);
    void delete(int id);

}
