package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Stake;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.uran.gamblingstation.StakeTestData.MATCHER;
import static com.uran.gamblingstation.StakeTestData.STAKES;

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
    public void getAllCash() throws Exception {

    }

    @Test
    public void getWinningStakes() throws Exception {

    }

    @Test
    public void save() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

}