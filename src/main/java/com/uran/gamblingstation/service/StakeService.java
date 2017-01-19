package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.dto.StakeDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StakeService {

    Stake get(int id, int userId);

    void delete(int id, int userId);

    List<Stake> getAll();

    Stake save(Stake stake, int userId);

    Stake save(StakeDTO stakeDTO, int userId);

    Stake update(Stake stake, int userId);

    Stake update(StakeDTO stakeDTO, int userId);

    Stake getWithUser(int id); //??? а оно надо?

    List<Stake> getAllByUserId(int userId);

    List<Stake> getAllByRaceId(int raceId);

    void setNotEditable(int raceId);

    Double getAllCash(int raceId);

    List<Stake> getAllWinningStakes();

    void setWinningStakes(int horseId, int raceId);

    void processWinningStakes(List<Stake> winningStakes, Map<Integer, Double> winningMap);

    List<Stake> getWinningStakes(int raceId);

    List<Stake> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

}
