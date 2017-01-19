package com.uran.gamblingstation.util.horse;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.dto.HorseDTO;
import org.junit.Assert;
import org.junit.Test;

import static com.uran.gamblingstation.HorseTestData.*;

public class HorseUtilTest {

    @Test
    public void testCreateNewFromTo() throws Exception {
        final HorseDTO horseDTO = new HorseDTO(null, "Gulfstream", "Гольфстрим", 3);
        Horse newFromTo = HorseUtil.createNewFromTo(horseDTO);
        newFromTo.setId(100011);
        HORSE_MATCHER.assertEquals(HORSE_8, newFromTo);
    }

    @Test
    public void testUpdateFromTo() throws Exception {
        final HorseDTO horseDTO = new HorseDTO(null, "Gulfstream", "Гольфстрим", 3);
        final Horse horseToUpdate = new Horse(null, "Test", "Тест", 3, 0, false);
        final Horse updated = HorseUtil.updateFromTo(horseToUpdate, horseDTO);
        updated.setId(100011);
        HORSE_MATCHER.assertEquals(HORSE_8, updated);
    }

    @Test
    public void testGetSerialized() throws Exception {
        final String serialized = HorseUtil.getSerialized(HORSES);
        Assert.assertEquals(serialized, SERIALIZED_HORSES);
    }

}