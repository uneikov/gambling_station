package com.uran.gamblingstation.util.stake;

import com.uran.gamblingstation.model.Stake;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.uran.gamblingstation.StakeTestData.*;

public class StakeUtilTest {

    @Test
    public void testGetValuesSum() throws Exception {
        final Double valuesSum = StakeUtil.getValuesSum(STAKES);
        Assert.assertEquals(valuesSum, 501.25, 0.001);
    }

    @Test
    public void testGetFilteredByWins() throws Exception {
        final List<Stake> all = StakeUtil.getFilteredByWins(STAKES, "success");
        STAKE_MATCHER.assertCollectionEquals(Collections.singleton(STAKE_1), all);
    }

}