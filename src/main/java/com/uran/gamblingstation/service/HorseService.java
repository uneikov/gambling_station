package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.util.exception.NotFoundException;

import java.util.List;

public interface
HorseService {

    Horse get(int id, int userId) throws NotFoundException;

    Horse getByName(String name);

    void delete(int id, int userId) throws NotFoundException;

    Horse save(Horse horse, int userId);

    Horse update(Horse horse, int userId) throws NotFoundException;

    List<Horse> getAll();

    /*default Collection<Horse> getBetweenDates(LocalDate startDate, LocalDate endDate, int userId) {
        return getBetweenDateTimes(LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX), userId);
    }

    Collection<Horse> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);*/

}
