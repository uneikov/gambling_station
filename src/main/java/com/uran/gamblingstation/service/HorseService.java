package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import util.exception.NotFoundException;

import java.util.List;

public interface HorseService {

    Horse get(int id, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    Horse save(Horse meal, int userId);

    Horse update(Horse meal, int userId) throws NotFoundException;

    List<Horse> getAll();

    /*default Collection<Horse> getBetweenDates(LocalDate startDate, LocalDate endDate, int userId) {
        return getBetweenDateTimes(LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX), userId);
    }

    Collection<Horse> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);*/

}
