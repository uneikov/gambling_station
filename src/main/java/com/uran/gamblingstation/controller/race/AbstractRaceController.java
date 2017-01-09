package com.uran.gamblingstation.controller.race;

import com.uran.gamblingstation.model.Race;
import com.uran.gamblingstation.service.RaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class AbstractRaceController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RaceService raceService;

    public List<Race> getAll() {
        log.info("getAll");
        return raceService.getAll();
    }

    public List<Race> getAllWithStakes() {
        log.info("getAllWithStakes");
        return raceService.getAllWithStakes();
    }

    public Race get(int id) {
        log.info("get {}", id);
        return raceService.get(id);
    }

    @Transactional
    public Race create(Race race) {
        race.setId(null);
        log.info("create {}", race);
        return raceService.save(race);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        raceService.delete(id);
    }

    public void update(Race race, int id) {
        race.setId(id);
        log.info("update {}", race);
        raceService.update(race);
    }
}
