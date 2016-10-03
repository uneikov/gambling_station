package com.uran.gamblingstation.repository;

import com.uran.gamblingstation.model.Horse;

import java.util.List;

public interface HorseRepository {

    // null if meal do not belong to userId
    Horse get(int id, int userId);

    // false if meal do not belong to userId
    void delete(int id);

    // null if updated meal do not belong to userId
    Horse save(Horse meal, int userId);

    // ORDERED dateTime
    List<Horse> getAll();

   /* // ORDERED dateTime
    Collection<Horse> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);*/
}
