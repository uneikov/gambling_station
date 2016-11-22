package com.uran.gamblingstation.repository;

import com.uran.gamblingstation.model.Race;

import java.time.LocalDateTime;
import java.util.Collection;

public interface RaceRepository {
    Race save(Race race);

    boolean delete(int id);

    Race get(int id);

    Race getByDateTime(LocalDateTime start, LocalDateTime finish);

    Collection<Race> getAll();

    void update(Race race);
}
