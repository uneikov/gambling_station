package com.uran.gamblingstation.repository;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface StakeRepository {

    List<Stake> getAll();

    List<Stake> getAllByUserId(int userId);

    List<Stake> getAllByRaceId(int raceId);

    List<Stake> getAllByHorseIdAndRaceId(int horseId, int raceId);

    Double getAllCash(LocalDateTime startDate, LocalDateTime endDate);

    Double getAllCash(int raceId);

    Stake save(Stake stake);

    void update(Stake stake);

    // false if not found
    boolean delete(int id);

    // null if not found
    Stake get(int id);

    List<Stake> getAllWinningStakes();

    List<Stake> getWinningStakes(LocalDateTime startDate, LocalDateTime endDate, int userId);

    List<Stake> getWinningStakes(int raceId);

    List<Stake> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);

    List<Stake> getBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Stake> getBetween(User user, LocalDateTime startDate, LocalDateTime endDate);

    List<Stake> getBetweenWithHorse(int horseId, LocalDateTime startDate, LocalDateTime endDate);

    List<Stake> getBetween(User user, Horse horse, LocalDateTime startDate, LocalDateTime endDate);
}
