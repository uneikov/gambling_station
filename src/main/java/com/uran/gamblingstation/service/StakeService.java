package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Stake;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StakeService {

    Stake get(int id);

    void delete(int id);

    List<Stake> getAll();

    Stake save(Stake stake);

    void update(Stake stake);


    Stake getWithUser(int id); //??? а оно надо?

    List<Stake> getAllByUserId(int userId);

    List<Stake> getAllByRaceId(int raceId);

    void setNotEditable(int raceId);

    Double getAllCash(int raceId);

    List<Stake> getAllWinningStakes();

    void setWinningStakes(int horseId, int raceId);

    void processWinningStakes(List<Stake> winningStakes, Map<Integer, Double> winningMap);

    List<Stake> getWinningStakes(int raceId);

    List<Stake> getBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Stake> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

// void setNotEditable(LocalDateTime start, LocalDateTime finish);
    //Double getAllCash(LocalDateTime startDate, LocalDateTime endDate);
// void setWinningStakes(int horseId, LocalDateTime startDate, LocalDateTime endDate);
//List<Stake> getWinningStakes(LocalDateTime startDate, LocalDateTime endDate);
}
