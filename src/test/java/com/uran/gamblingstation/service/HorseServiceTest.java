package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.util.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.uran.gamblingstation.HorseTestData.*;


public class HorseServiceTest extends AbstractServiceTest {

    @Autowired
    private HorseService service;

    @Before
    public void setUp() {
        testName = getClass().getSimpleName();
    }

    @Test
    public void testGetAll() throws Exception {
        List<Horse> horseList = service.getAll();
        HORSE_MATCHER.assertCollectionEquals(HORSES, horseList);
    }

    @Test
    public void testGet() throws Exception {
        Horse horse = service.get(HORSE_1_ID + 3);
        HORSE_MATCHER.assertEquals(HORSE_4, horse);
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
        HORSE_MATCHER.assertEquals(HORSE_4, service.get(HORSE_1_ID + 3));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(HORSE_1_ID);
        HORSE_MATCHER.assertCollectionEquals(Arrays.asList(
                HORSE_7, HORSE_10, HORSE_3, HORSE_8, HORSE_6, HORSE_9, HORSE_5, HORSE_4, HORSE_2),
                service.getAll()
        );
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(1);
    }

    @Test
    public void testUpdate() throws Exception {
        Horse updated = getUpdated();
        service.update(updated);
        HORSE_MATCHER.assertEquals(updated, service.get(HORSE_1_ID));
    }

    @Test
    public void testSave() throws Exception {
        Horse newHorse = new Horse(null, "Captain", "Капитан", 5, 0);
        Horse created = service.save(newHorse);
        newHorse.setId(created.getId());
        HORSE_MATCHER.assertCollectionEquals(Arrays.asList(
                HORSE_7, HORSE_10, HORSE_1, created, HORSE_3, HORSE_8, HORSE_6, HORSE_9, HORSE_5, HORSE_4, HORSE_2),
                service.getAll()
        );
    }
}
