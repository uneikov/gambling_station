package com.uran.gamblingstation.repository;

import com.uran.gamblingstation.model.Stake;

import java.util.List;

public interface StakeRepository {
    List<Stake> getALL();
    Double getAllCash();
    List<Stake> getWinningStakes();
    boolean save(Stake stake);
    boolean update(Stake stake);
    void delete(int id);
}
