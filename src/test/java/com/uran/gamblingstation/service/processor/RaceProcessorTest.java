package com.uran.gamblingstation.service.processor;

import com.uran.gamblingstation.HorseTestData;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.AbstractServiceTest;
import com.uran.gamblingstation.service.HorseService;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.service.WalletService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static com.uran.gamblingstation.RaceTestData.RACE_4_ID;
import static com.uran.gamblingstation.StakeTestData.*;
import static com.uran.gamblingstation.UserTestData.USER_ID_1;
import static com.uran.gamblingstation.UserTestData.USER_ID_2;

public class RaceProcessorTest extends AbstractServiceTest {

    @Autowired private StakeService stakeService;
    @Autowired private WalletService walletService;
    @Autowired private HorseService horseService;
    @Autowired private RaceProcessor raceProcessor;

    @Test
    public void testWinningStakes(){
        stakeService.save(STAKE_6, USER_ID_1);
        raceProcessor.process(HorseTestData.HORSE_4.getId(), RACE_4_ID);
        //-----------------------= wins =----------------------------
        int wins = horseService.get(HorseTestData.HORSE_4.getId()).getWins();
        Assert.assertEquals(1, wins);
        //-----------------------= wallets =-------------------------
        Double cash1 = walletService.get(USER_ID_1).getCash();
        Double cash2 = walletService.get(USER_ID_2).getCash();
        Assert.assertEquals(cash1, 105.25d, 0.001d);
        Assert.assertEquals(cash2, 120.25d, 0.001d);
        //------------------------= stakes =-------------------------
        List<Stake> winningStakes = stakeService.getWinningStakes(RACE_4_ID);
        STAKE_MATCHER.assertCollectionEquals(
                winningStakes,
                Arrays.asList(STAKE_5_WIN_WITH_AMOUNT, STAKE_4_WIN_WITH_AMOUNT)
        );
    }

}