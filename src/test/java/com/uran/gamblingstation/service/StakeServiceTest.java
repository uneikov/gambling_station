package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.util.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.uran.gamblingstation.HorseTestData.WINNING_HORSE;
import static com.uran.gamblingstation.RaceTestData.RACE_1_ID;
import static com.uran.gamblingstation.RaceTestData.RACE_4_ID;
import static com.uran.gamblingstation.StakeTestData.*;
import static com.uran.gamblingstation.UserTestData.*;

public class StakeServiceTest extends AbstractServiceTest{

    @Autowired
    private StakeService service;

    @Before
    public void setUp() {
        testName = getClass().getSimpleName();
    }

    @Test
    public void testGetAll() throws Exception {
        List<Stake> allStakes = service.getAll();
        STAKE_MATCHER.assertCollectionEquals(allStakes, STAKES);
    }

    @Test
    public void testGetByRace(){
        List<Stake> allStakes = service.getAllByRaceId(RACE_1_ID);
        STAKE_MATCHER.assertCollectionEquals(allStakes, Collections.singletonList(STAKE_1));
    }

    @Test
    public void testGetAllWinningStakes() throws Exception {
        List<Stake> allStakes = service.getAllWinningStakes();
        STAKE_MATCHER.assertCollectionEquals(allStakes, Collections.singletonList(STAKE_1));
    }

    @Test
    public void testSave() throws Exception {
        Stake created = service.save(getCreated(), USER_ID_1);
        STAKE_MATCHER.assertCollectionEquals(service.getAllByUserId(USER_ID_1),
                Arrays.asList(created, STAKE_5, STAKE_3, STAKE_1));
    }

    @Test
    public void testUpdate() throws Exception {
        Stake updated = getUpdated();
        service.update(updated, USER_ID_1);
        STAKE_MATCHER.assertEquals(updated, service.get(STAKE_1_ID, USER_ID_1));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(STAKE_2_ID, USER_ID_2);
        STAKE_MATCHER.assertCollectionEquals(service.getAllByUserId(USER_ID_2), Collections.singletonList(STAKE_4));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(1, USER_ID_1);
    }

    @Test
    public void testGet() throws Exception {
        Stake stake_3 = service.get(STAKE_3_ID, USER_ID_1);
        STAKE_MATCHER.assertEquals(stake_3, STAKE_3);
    }

    @Test
    public void testIsEditable() throws Exception {
        Stake stake_3 = service.get(STAKE_3_ID, USER_ID_1);
        Assert.assertFalse(stake_3.isEditable());
    }

    @Test
    public void testSetEditable() throws Exception {
        Stake stake_3 = service.get(STAKE_3_ID, USER_ID_1);
        stake_3.setEditable(true);
        service.update(stake_3, USER_ID_1);
        Assert.assertTrue(service.get(STAKE_3_ID, USER_ID_1).isEditable());
    }

    @Test
    public void testArchived() throws Exception {
        service.setNotEditable(RACE_4_ID);
        Assert.assertFalse(service.get(STAKE_3_ID, USER_ID_1).isEditable());
    }

    @Test
    public void testSetWinningStakes() {
        service.setWinningStakes(WINNING_HORSE.getId(), RACE_4_ID);
        List<Stake> list = service.getWinningStakes(RACE_4_ID);
        STAKE_MATCHER.assertCollectionEquals(list, Arrays.asList(STAKE_4_WIN, STAKE_5_WIN));
    }

    @Test
    public void testWinningAmounts() {
        Map<Integer, Double> winningMap = new HashMap<>();
        service.setWinningStakes(WINNING_HORSE.getId(), RACE_4_ID);
        List<Stake> beforeList = service.getWinningStakes(RACE_4_ID);
        beforeList.forEach(stake -> winningMap.put(stake.getUser().getId(), 100.0));
        service.processWinningStakes(beforeList, winningMap);
        List<Stake> afterList = service.getWinningStakes(RACE_4_ID);
        double sum = afterList.stream().mapToDouble(Stake::getAmount).sum();
        Assert.assertEquals(sum, 200.0, 0.0001);
    }

    @Test
    public void testRaceResult() {
        service.setWinningStakes(WINNING_HORSE.getId(), RACE_4_ID);
        List<Stake> winning = service.getWinningStakes(RACE_4_ID);
        Stake winning1 = service.getWithUser(winning.get(0).getId());
        Stake winning2 = service.getWithUser(winning.get(1).getId());
        STAKE_MATCHER.assertCollectionEquals(
                Arrays.asList(winning1, winning2),
                Arrays.asList(STAKE_4_WIN, STAKE_5_WIN)
        );
    }

    @Test
    public void testGetBetween(){
        List<Stake> stake = service.getBetween(
                LocalDateTime.of(2016, Month.JUNE, 13, 16, 0).truncatedTo(ChronoUnit.SECONDS),
                LocalDateTime.of(2016, Month.JUNE, 13, 20, 0).truncatedTo(ChronoUnit.SECONDS)
        );
        STAKE_MATCHER.assertCollectionEquals(stake, Collections.singletonList(STAKE_3));
    }

    @Test
    public void testGetUser(){
        Stake stake = service.getWithUser(STAKE_2_ID);
        USER_MATCHER.assertEquals(USER_2, stake.getUser());
    }
}