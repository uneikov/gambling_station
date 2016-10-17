package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StakeService {

    Stake get(int id, int userId);

    void delete(int id, int userId);

    List<Stake> getAll(int userId);

    Double getAllCash();

    Stake save(Stake stake, int userId);

    void update(Stake stake, int userId);

    List<Stake> getAllWinningStakes();

    List<User> getWinningUsers(Horse horse, LocalDateTime startDate, LocalDateTime endDate);

    void setWinningStakes(int horseId, LocalDateTime startDate, LocalDateTime endDate);

    void processWinningStakes(List<Stake> winningStakes, Map<Integer, Double> winningMap);

    List<Stake> getLoosingStakes(LocalDateTime startDate, LocalDateTime endDate);

    List<Stake> getWinningStakes(LocalDateTime startDate, LocalDateTime endDate);

    List<Stake> getBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Stake> getBetween(User user, LocalDateTime startDate, LocalDateTime endDate);

    List<Stake> getBetween(Horse horse, LocalDateTime startDate, LocalDateTime endDate);

    List<Stake> getBetween(User user, Horse horse, LocalDateTime startDate, LocalDateTime endDate);
}
