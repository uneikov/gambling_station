package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.to.HorseTo;
import com.uran.gamblingstation.util.exception.NotFoundException;

import java.util.List;

public interface
HorseService {

    Horse get(int id) throws NotFoundException;

    Horse getByName(String name);

    void delete(int id) throws NotFoundException;

    Horse save(Horse horse);

    void update(Horse horse) throws NotFoundException;

    void update(HorseTo horseTo) throws NotFoundException;

    List<Horse> getAll();
    
    List<Horse> getReady();
}
