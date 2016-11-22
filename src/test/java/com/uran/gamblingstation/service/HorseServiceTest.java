package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.util.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.uran.gamblingstation.HorseTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-database.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:database/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class HorseServiceTest {

    @Rule
    public final ExpectedException expected = ExpectedException.none();

    @Autowired
    protected HorseService service;

    @Test
    public void testGetAll() throws Exception {
        List<Horse> horseList = service.getAll();
        MATCHER.assertCollectionEquals(HORSES, horseList);
    }

    @Test
    public void testGet() throws Exception {
        Horse horse = service.get(HORSE_1_ID + 3);
        MATCHER.assertEquals(HORSE_4, horse);
    }

    @Test
    public void testSerialize() throws Exception {
        String serialized = service.getAll().stream()
                .map(horse -> horse.getName() + ":" + horse.getRuName())
                .collect(Collectors.joining(","));
        Assert.assertEquals(serialized.split(",")[0], "Alien:Чужой");
        Map<String, String> deserialized =
                Arrays.stream(serialized.split(",")).map(s -> s.split(":")).collect(Collectors.toMap(e -> e[0], e -> e[1]));
        Assert.assertEquals(deserialized.get("Alien"), "Чужой");
    }



    @Test
    public void testReady() throws Exception {
        Horse horse = service.get(HORSE_1_ID + 3);
        horse.setReady(true);
        service.update(horse);
        MATCHER.assertEquals(HORSE_4, service.get(HORSE_1_ID + 3));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(HORSE_1_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(
                HORSE_7, HORSE_10, HORSE_3, HORSE_8, HORSE_6, HORSE_9, HORSE_5, HORSE_4, HORSE_2),
                service.getAll()
        );
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(1);
    }

    @Test
    public void testUpdate() throws Exception {
        Horse updated = getUpdated();
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(HORSE_1_ID));
    }

    @Test
    public void testSave() throws Exception {
        Horse newHorse = new Horse(null, "Captain", "Капитан", 5, 0);
        Horse created = service.save(newHorse);
        newHorse.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(
                HORSE_7, HORSE_10, HORSE_1, created, HORSE_3, HORSE_8, HORSE_6, HORSE_9, HORSE_5, HORSE_4, HORSE_2),
                service.getAll()
        );
    }
}
