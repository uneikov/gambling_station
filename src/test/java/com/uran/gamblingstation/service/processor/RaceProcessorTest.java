package com.uran.gamblingstation.service.processor;

import com.uran.gamblingstation.HorseTestData;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.HorseService;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.service.WalletService;
import com.uran.gamblingstation.service.account.AccountService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.uran.gamblingstation.StakeTestData.*;
import static com.uran.gamblingstation.UserTestData.*;
import static java.time.LocalDateTime.of;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-database.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:database/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RaceProcessorTest {

    private static LocalDateTime START = of(2016, 8, 5, 10, 0);
    private static LocalDateTime END = of(2016, 8, 5, 10, 45);


    @Autowired private StakeService stakeService;
    @Autowired private AccountService accountService;
    @Autowired private WalletService walletService;
    @Autowired private HorseService horseService;
    @Autowired private RaceProcessor raceProcessor;

    @Test
    public void testWinningHorses() throws Exception {
        raceProcessor.process(HorseTestData.HORSE_4.getId(), START, END);
        int wins = horseService.get(HorseTestData.HORSE_4.getId(), ADMIN_ID).getWins();
        Assert.assertEquals(1L, (long) wins);
    }

    @Test
    public void testWinningStakes(){
        raceProcessor.process(HorseTestData.HORSE_4.getId(), START, END);
        List<Stake> winningStakes = stakeService.getWinningStakes(START, END);
        STAKE_MATCHER.assertCollectionEquals(winningStakes, Arrays.asList(STAKE_5_WIN, STAKE_4_WIN));
    }

    @Test
    public void testUsersWallets(){
        //Stake loosingStake = new Stake(USER_1, HORSE_7, 100.0, of(2016, Month.AUGUST, 5, 10, 25).truncatedTo(ChronoUnit.SECONDS), false, 0.0);
        stakeService.save(STAKE_6, USER_ID_1);
        raceProcessor.process(HorseTestData.HORSE_4.getId(), START, END);
        final Double cash1 = walletService.get(USER_ID_1).getCash();
        final Double cash2 = walletService.get(USER_ID_2).getCash();
        Assert.assertEquals(cash1, 60.125d, 0.001d);
        Assert.assertEquals(cash2, 65.125d, 0.001d);

    }
}