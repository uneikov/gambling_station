package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Race;
import com.uran.gamblingstation.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface RaceService {
    Race save(Race race);

    void delete(int id) throws NotFoundException;

    Race get(int id) throws NotFoundException;

    List<Race> getAllWithStakes();

    Race getByDateTime(LocalDateTime start, LocalDateTime finish) throws NotFoundException;

    List<Race> getAll();

    void update(Race race);
}
