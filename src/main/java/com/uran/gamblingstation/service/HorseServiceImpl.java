package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.repository.HorseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.exception.NotFoundException;

import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {

    @Autowired
    private HorseRepository repository;


    @Override
    public Horse get(int id, int userId) throws NotFoundException {
        if (userId != 100002)
            throw new NotFoundException("Unauthorized operation");
        else
            return repository.get(id, userId);
    }


    @Override
    public void delete(int id, int userId) throws NotFoundException {
        if (userId != 100002)
            throw new NotFoundException("Unauthorized operation");
        else
            repository.delete(id);
    }

    @Override
    public Horse save(Horse meal, int userId) {
        return null;
    }

    @Override
    public Horse update(Horse meal, int userId) throws NotFoundException {
        return null;
    }

    @Override
    public List<Horse> getAll() {
        return repository.getAll();
    }
}
