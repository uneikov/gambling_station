package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Race;
import com.uran.gamblingstation.repository.RaceRepository;
import com.uran.gamblingstation.util.exception.ExceptionUtil;
import com.uran.gamblingstation.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RaceServiceImpl implements RaceService {

    @Autowired private RaceRepository repository;

    @Override
    public Race save(Race race) {
        Assert.notNull(race, "race must not be null");
        return repository.save(race);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        ExceptionUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Race get(int id) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public List<Race> getAllWithStakes() {
        return repository.getAllWithStakes();
    }

    @Override
    public Race getByDateTime(LocalDateTime start, LocalDateTime finish) throws NotFoundException {
        Assert.notNull(start, "start dateTime must not be null");
        Assert.notNull(finish, "finish dateTime must not be null");
        return ExceptionUtil.checkNotFound(repository.getByDateTime(start, finish), "start=" + start + ", finish=" + finish);
    }

    @Override
    public List<Race> getAll() {
        return repository.getAll();
    }

    @Override
    public void update(Race race) {
        Assert.notNull(race, "user must not be null");
        repository.save(race);
    }
}
