package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.repository.HorseRepository;
import com.uran.gamblingstation.util.exception.ExceptionUtil;
import com.uran.gamblingstation.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.uran.gamblingstation.model.BaseEntity.ADMIN_ID;

@Service
public class HorseServiceImpl implements HorseService {

    @Autowired
    private HorseRepository repository;


    @Override
    public Horse get(int id, int userId) throws NotFoundException {
        if (userId != ADMIN_ID)
            throw new NotFoundException("Unauthorized operation");
        else
            return repository.get(id, userId);
    }

    @Override
    public Horse getByName(String name) throws NotFoundException {
        return getAll().stream().filter(horse -> name.equals(horse.getName())).findFirst().orElse(null);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        if (userId != ADMIN_ID)
            throw new NotFoundException("Unauthorized operation");
        else
            repository.delete(id);
    }

    @Override
    public Horse save(Horse horse, int userId) {
        Assert.notNull(horse, "horse must not be null");
        return ExceptionUtil.checkNotFound(repository.save(horse, userId), "Unauthorized operation");
    }

    @Override
    public Horse update(Horse horse, int userId) throws NotFoundException {
        Assert.notNull(horse, "horse must not be null");
        return ExceptionUtil.checkNotFoundWithId(repository.save(horse, userId), horse.getId());
    }

    @Override
    public List<Horse> getAll() {
        return repository.getAll();
    }
}
