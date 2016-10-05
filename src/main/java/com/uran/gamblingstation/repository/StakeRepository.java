package com.uran.gamblingstation.repository;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface StakeRepository {
    List<Stake> getALL();

    Double getAllCash();

    boolean save(Stake stake);

    boolean update(Stake stake);

    void delete(int id);

    Stake get(int id);

    List<Stake> getAllWinningStakes();

    List<Stake> getWinningStakes(LocalDateTime startDate, LocalDateTime endDate);

    Collection<Stake> getBetween(LocalDateTime startDate, LocalDateTime endDate);

    Collection<Stake> getBetween(User user, LocalDateTime startDate, LocalDateTime endDate);

    Collection<Stake> getBetween(Horse horse, LocalDateTime startDate, LocalDateTime endDate);

    Collection<Stake> getBetween(User user, Horse horse, LocalDateTime startDate, LocalDateTime endDate);
}
