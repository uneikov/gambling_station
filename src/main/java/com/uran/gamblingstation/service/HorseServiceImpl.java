package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.repository.HorseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
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
    public Horse save(Horse horse, int userId) {
        Assert.notNull(horse, "horse must not be null");
        return null;
    }

    @Override
    public Horse update(Horse horse, int userId) throws NotFoundException {
        Assert.notNull(horse, "horse must not be null");
        return null;
    }

    @Override
    public List<Horse> getAll() {
        return repository.getAll();
    }
}
