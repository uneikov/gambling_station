package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Race;
import com.uran.gamblingstation.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Month;
import java.util.Arrays;

import static com.uran.gamblingstation.RaceTestData.*;
import static java.time.LocalDateTime.of;


public class RaceServiceTest extends AbstractServiceTest{

    @Autowired RaceService raceService;

    @Test
    public void save() throws Exception {
        Race created = getNewRace();
        raceService.save(created);
        RACE_MATCHER.assertCollectionEquals(raceService.getAll(), Arrays.asList(created, RACE_4, RACE_3, RACE_2, RACE_1));
    }

    @Test
    public void delete() throws Exception {
        raceService.delete(RACE_1_ID);
        RACE_MATCHER.assertCollectionEquals(raceService.getAll(), Arrays.asList(RACE_4, RACE_3, RACE_2));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        raceService.delete(1);
    }

    @Test
    public void get() throws Exception {
        RACE_MATCHER.assertEquals(raceService.get(RACE_1_ID), RACE_1);
    }

    @Test
    public void getByDateTime() throws Exception {
        RACE_MATCHER.assertEquals(
                raceService.getByDateTime(of(2016, Month.AUGUST, 5, 10, 0), of(2016, Month.AUGUST, 5, 10, 45)),
                RACE_4);
    }

    @Test
    public void getAll() throws Exception {
        RACE_MATCHER.assertCollectionEquals(raceService.getAll(), Arrays.asList(RACE_4, RACE_3, RACE_2, RACE_1));
    }

    @Test
    public void update() throws Exception {
        Race updated = getUpdatedRace();
        raceService.update(updated);
        RACE_MATCHER.assertCollectionEquals(raceService.getAll(), Arrays.asList(updated, RACE_3, RACE_2, RACE_1));
    }

}