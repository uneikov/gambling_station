package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Stake;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.uran.gamblingstation.HorseTestData.WINNING_HORSE;
import static com.uran.gamblingstation.StakeTestData.*;
import static com.uran.gamblingstation.UserTestData.USER_ID_1;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-database.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:database/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class StakeServiceTest {

    @Autowired
    private StakeService service;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetAll() throws Exception {
        List<Stake> allStakes = service.getAll();
        MATCHER.assertCollectionEquals(allStakes, STAKES);
    }

    @Test
    public void testGetAllCash() throws Exception {
        Double cash = service.getAllCash();
        Assert.assertEquals(cash, 501.25, 0.0001);
    }

    @Test
    public void testGetWinningStakes() throws Exception {
        List<Stake> allStakes = service.getWinningStakes();
        MATCHER.assertCollectionEquals(allStakes, Collections.singletonList(STAKE_1));
    }

    @Test
    public void testSave() throws Exception {
        Stake created = getCreated();
        service.save(created, USER_ID_1);
        MATCHER.assertCollectionEquals(service.getAll(),
                Arrays.asList(created, STAKE_5, STAKE_4, STAKE_3, STAKE_2, STAKE_1) );
    }

    @Test
    public void testUpdate() throws Exception {
        Stake updated = getUpdated();
        service.update(updated, USER_ID_1);
        MATCHER.assertEquals(updated, service.get(STAKE_1_ID));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(STAKE_2_ID);
        MATCHER.assertCollectionEquals(service.getAll(), Arrays.asList(STAKE_5, STAKE_4, STAKE_3, STAKE_1));
    }

    @Test
    public void testGet() throws Exception {
        Stake stake_3 = service.get(STAKE_3_ID);
        MATCHER.assertEquals(stake_3, STAKE_3);
    }

    @Test
    public void testSetWinningStakes(){
        service.setWinningStakes(WINNING_HORSE, USER_ID_1);
        List<Stake> list = service.getWinningStakes();
        MATCHER.assertCollectionEquals(list, Arrays.asList(STAKE_1, STAKE_4_WIN, STAKE_5_WIN) );
    }

}