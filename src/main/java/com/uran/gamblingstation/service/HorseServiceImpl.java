package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.repository.HorseRepository;
import com.uran.gamblingstation.to.HorseDTO;
import com.uran.gamblingstation.util.exception.ExceptionUtil;
import com.uran.gamblingstation.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

import static com.uran.gamblingstation.util.horse.HorseUtil.updateFromTo;

@Service
public class HorseServiceImpl implements HorseService {

    @Autowired
    private HorseRepository repository;

    @Override
    public Horse get(int id) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public Horse getByName(String name) throws NotFoundException {
        Assert.notNull(name, "horse name must not be null");
        return getAll().stream().filter(horse -> name.equals(horse.getName())).findFirst().orElse(null);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        ExceptionUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Horse save(Horse horse) {
        Assert.notNull(horse, "horse must not be null");
        return repository.save(horse);
    }

    @Override
    public void update(Horse horse) throws NotFoundException {
        Assert.notNull(horse, "horse must not be null");
        repository.save(horse);
    }

    @Override
    @Transactional
    public void update(HorseDTO horseDTO) {
        Horse horse = updateFromTo(get(horseDTO.getId()), horseDTO);
        repository.save(horse);
    }

    @Override
    public List<Horse> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Horse> getReady() {
        return repository.getAll().stream().filter(Horse::isReady).collect(Collectors.toList());
    }
}
