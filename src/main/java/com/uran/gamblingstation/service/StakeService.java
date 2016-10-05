package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface StakeService {

    List<Stake> getAll();
    Double getAllCash();
    boolean save(Stake stake, int userId);
    boolean update(Stake stake, int userId);
    void delete(int id);
    Stake get(int id);

    List<Stake> getAllWinningStakes();
    List<User> getWinningUsers(Horse horse, LocalDateTime startDate, LocalDateTime endDate, int userId);
    void setWinningStakes(Horse horse, LocalDateTime startDate, LocalDateTime endDate, int userId);
    List<Stake> getWinningStakes(LocalDateTime startDate, LocalDateTime endDate);
    Collection<Stake> getBetween(LocalDateTime startDate, LocalDateTime endDate);
    Collection<Stake> getBetween(User user, LocalDateTime startDate, LocalDateTime endDate);
    Collection<Stake> getBetween(Horse horse, LocalDateTime startDate, LocalDateTime endDate);
    Collection<Stake> getBetween(User user, Horse horse, LocalDateTime startDate, LocalDateTime endDate);
}
