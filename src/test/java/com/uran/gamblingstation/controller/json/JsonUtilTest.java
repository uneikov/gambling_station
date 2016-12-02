package com.uran.gamblingstation.controller.json;

import com.uran.gamblingstation.StakeTestData;
import com.uran.gamblingstation.model.Stake;
import org.junit.Test;

import java.util.List;

import static com.uran.gamblingstation.StakeTestData.STAKE_1;
import static com.uran.gamblingstation.StakeTestData.STAKE_MATCHER;

/**
 * GKislin
 * 22.07.2015.
 */
public class JsonUtilTest {

    @Test
    public void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(StakeTestData.STAKE_1);
        System.out.println(json);
        Stake stake = JsonUtil.readValue(json, Stake.class);
        STAKE_MATCHER.assertEquals(STAKE_1, stake);
    }

    @Test
    public void testReadWriteValues() throws Exception {
        String json = JsonUtil.writeValue(StakeTestData.STAKES);
        System.out.println(json);
        List<Stake> stakes = JsonUtil.readValues(json, Stake.class);
        STAKE_MATCHER.assertCollectionEquals(StakeTestData.STAKES, stakes);
    }
}