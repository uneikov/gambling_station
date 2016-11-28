package com.uran.gamblingstation.repository;

import com.uran.gamblingstation.model.Stake;

import java.time.LocalDateTime;
import java.util.List;

public interface StakeRepository {

    Stake save(Stake stake);

    void update(Stake stake);

    // false if not found
    boolean delete(int id);

    // null if not found
    Stake get(int id);

    // null if not found
    Stake getWithUser(int id);

    List<Stake> getAll();

    List<Stake> getAllByUserId(int userId);

    List<Stake> getAllByRaceId(int raceId);

    List<Stake> getAllByHorseIdAndRaceId(int horseId, int raceId);

    Double getAllCash(int raceId);

    List<Stake> getAllWinningStakes();

    List<Stake> getWinningStakes(int raceId);

    List<Stake> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);

    List<Stake> getBetween(LocalDateTime startDate, LocalDateTime endDate);

}
