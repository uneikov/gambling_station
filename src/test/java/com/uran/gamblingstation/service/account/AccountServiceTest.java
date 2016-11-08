package com.uran.gamblingstation.service.account;

import com.uran.gamblingstation.service.WalletService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.uran.gamblingstation.UserTestData.*;
import static com.uran.gamblingstation.model.BaseEntity.WALLET_ID;
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-database.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:database/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class AccountServiceTest {

    @Autowired private AccountService accountService;
    @Autowired private WalletService walletService;

    @Test
    public void addToStationAccount() throws Exception {
        accountService.addToStationAccount(100.00d);
        Double stationCash = walletService.get(WALLET_ID).getCash();
        Assert.assertEquals(100.0d, stationCash, 0.0001d);
    }

    @Test
    public void transferToStationFromUser() throws Exception {
        accountService.transferToStation(USER_ID_2, 5.00d);
        Double stationCash = walletService.get(WALLET_ID).getCash();
        Double userCash = walletService.get(USER_ID_2).getCash();
        Assert.assertEquals(5.0d, stationCash, 0.0001d);
        Assert.assertEquals(10.0d, userCash, 0.0001d);
    }

    @Test
    public void addToAccount() throws Exception {
        accountService.addToAccount(USER_ID_1, 10.0d);
        Double userCash = walletService.get(USER_ID_1).getCash();
        Assert.assertEquals(20.0d, userCash, 0.0001d);
    }

    @Test
    public void debitAccount() throws Exception {
        accountService.debitAccount(USER_ID_2, 5.0d);
        Double userCash = walletService.get(USER_ID_2).getCash();
        Assert.assertEquals(10.0d, userCash, 0.0001d);
    }

}