package com.uran.gamblingstation.repository;

import com.uran.gamblingstation.model.Race;

import java.time.LocalDateTime;
import java.util.List;

public interface RaceRepository {

    Race save(Race race);

    boolean delete(int id);

    Race get(int id);

    List<Race> getAllWithStakes();

    Race getByDateTime(LocalDateTime start, LocalDateTime finish);

    List<Race> getAll();

    void update(Race race);
}
