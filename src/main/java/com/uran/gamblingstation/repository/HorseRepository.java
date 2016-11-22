package com.uran.gamblingstation.repository;

import com.uran.gamblingstation.model.Horse;

import java.util.List;

public interface HorseRepository {

    Horse get(int id);

    boolean delete(int id);

    Horse save(Horse meal);

    List<Horse> getAll();

}
