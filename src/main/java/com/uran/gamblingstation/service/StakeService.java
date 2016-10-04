package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Stake;

import java.util.List;

public interface StakeService {

    List<Stake> getAll();
    Double getAllCash();
    List<Stake> getWinningStakes();
    boolean save(Stake stake, int userId);
    boolean update(Stake stake, int userId);
    void setWinningStakes(Horse horse, int userId);
    void delete(int id);
    Stake get(int id);

}
