package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static com.uran.gamblingstation.HorseTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-database.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:database/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class HorseServiceTest {

    @Autowired
    protected HorseService service;

    @Test
    public void testGetAll() throws Exception {
        List<Horse> horseList = service.getAll();
        MATCHER.assertCollectionEquals(HORSES, horseList);
    }

    @Test
    public void testGet() throws Exception {
        Horse horse = service.get(HORSE_1_ID + 3, 100002);
        MATCHER.assertEquals(HORSE_4, horse);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(HORSE_1_ID, 100002);
        MATCHER.assertCollectionEquals(
                Arrays.asList(
                        HORSE_7, HORSE_10, HORSE_3, HORSE_8, HORSE_6, HORSE_9, HORSE_5, HORSE_4, HORSE_2
                ),
        service.getAll());
    }

}
