package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Race;
import com.uran.gamblingstation.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;

public interface RaceService {
    Race save(Race race);

    void delete(int id) throws NotFoundException;

    Race get(int id) throws NotFoundException;

    //Race getByDateTyme(LocalDateTime dateTime) throws NotFoundException;

    Race getByDateTime(LocalDateTime start, LocalDateTime finish) throws NotFoundException;

    Collection<Race> getAll();

    void update(Race race);
}
