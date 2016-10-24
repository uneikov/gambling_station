package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.uran.gamblingstation.HorseTestData.HORSE_6;
import static com.uran.gamblingstation.HorseTestData.WINNING_HORSE;
import static com.uran.gamblingstation.StakeTestData.*;
import static com.uran.gamblingstation.UserTestData.*;
import static com.uran.gamblingstation.util.TimeUtil.VALID_END_DATETIME;
import static com.uran.gamblingstation.util.TimeUtil.VALID_START_DATETIME;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-database.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:database/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class StakeServiceTest {

    @Autowired
    private StakeService service;

    @Test
    public void testGetAll() throws Exception {
        List<Stake> allStakes = service.getAllByUserId(ADMIN_ID);
        STAKE_MATCHER.assertCollectionEquals(allStakes, STAKES);
    }

    @Test
    public void testGetAllCash() throws Exception {
        Double cash = service.getAllCash();
        Assert.assertEquals(cash, 501.25, 0.0001);
    }

    @Test
    public void testGetAllWinningStakes() throws Exception {
        List<Stake> allStakes = service.getAllWinningStakes();
        STAKE_MATCHER.assertCollectionEquals(allStakes, Collections.singletonList(STAKE_1));
    }
//foreign key no parent
    @Test
    public void testSave() throws Exception {
        Stake created = getCreated();
        service.save(created, USER_ID_1); //
        STAKE_MATCHER.assertCollectionEquals(service.getAllByUserId(ADMIN_ID),
                Arrays.asList(created, STAKE_5, STAKE_4, STAKE_3, STAKE_2, STAKE_1));
    }

    @Test
    public void testUpdate() throws Exception {
        Stake updated = getUpdated();
        service.update(updated, USER_ID_1);
        STAKE_MATCHER.assertEquals(updated, service.get(STAKE_1_ID, ADMIN_ID));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(STAKE_2_ID, ADMIN_ID);
        STAKE_MATCHER.assertCollectionEquals(service.getAllByUserId(ADMIN_ID), Arrays.asList(STAKE_5, STAKE_4, STAKE_3, STAKE_1));
    }

    @Test
    public void testGet() throws Exception {
        Stake stake_3 = service.get(STAKE_3_ID, ADMIN_ID);
        STAKE_MATCHER.assertEquals(stake_3, STAKE_3);
    }

    @Test
    public void testSetWinningStakes() {
        service.setWinningStakes(WINNING_HORSE.getId(), VALID_START_DATETIME, VALID_END_DATETIME);
        List<Stake> list = service.getWinningStakes(VALID_START_DATETIME, VALID_END_DATETIME);
        STAKE_MATCHER.assertCollectionEquals(list, Arrays.asList(STAKE_5_WIN, STAKE_4_WIN));
    }

    @Test
    public void testWinningAmounts() {
        Map<Integer, Double> winningMap = new HashMap<>();
        service.setWinningStakes(WINNING_HORSE.getId(), VALID_START_DATETIME, VALID_END_DATETIME);
        List<Stake> beforeList = service.getWinningStakes(VALID_START_DATETIME, VALID_END_DATETIME);
        beforeList.forEach(stake -> winningMap.put(stake.getUser().getId(), 100.0));
        service.processWinningStakes(beforeList, winningMap);
        List<Stake> afterList = service.getWinningStakes(VALID_START_DATETIME, VALID_END_DATETIME);
        double sum = afterList.stream().mapToDouble(Stake::getAmount).sum();
        Assert.assertEquals(sum, 200.0, 0.0001);
    }

    @Test
    public void testRaceResult() {
        /*Horse winningHorse = HORSES.get(RandomUtil.getWinningHorse());*/
        service.setWinningStakes(WINNING_HORSE.getId(), VALID_START_DATETIME, VALID_END_DATETIME);
        List<User> winningUsers = service.getWinningUsers(WINNING_HORSE.getId(), VALID_START_DATETIME, VALID_END_DATETIME);
        USER_MATCHER.assertCollectionEquals(winningUsers, Arrays.asList(USER_1, USER_2));
    }

    @Test
    public void testGetBetweenWithUserAndHorse(){
        List<Stake> stake = service.getBetween(
                USER_1,
                HORSE_6,
                LocalDateTime.of(2016, Month.JUNE, 1, 0, 0).truncatedTo(ChronoUnit.SECONDS),
                LocalDateTime.of(2016, Month.JUNE, 30, 23, 49).truncatedTo(ChronoUnit.SECONDS)
        );
        STAKE_MATCHER.assertCollectionEquals(stake, Collections.singletonList(STAKE_3));
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
        Stake stake = service.get(STAKE_2_ID, USER_ID_2);
        USER_MATCHER.assertEquals(USER_2, stake.getUser());
    }
}