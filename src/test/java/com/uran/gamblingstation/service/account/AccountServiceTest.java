package com.uran.gamblingstation.service.account;

import com.uran.gamblingstation.service.AbstractServiceTest;
import com.uran.gamblingstation.service.WalletService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.uran.gamblingstation.UserTestData.USER_ID_1;
import static com.uran.gamblingstation.UserTestData.USER_ID_2;
import static com.uran.gamblingstation.WalletTestData.WALLET_ID;

@Component
public class AccountServiceTest extends AbstractServiceTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private WalletService walletService;

    @Before
    public void setUp() {
        testName = getClass().getSimpleName();
    }

    @Test
    public void addToStationAccount() throws Exception {
        accountService.addToStationAccount(100.00d);
        Double stationCash = walletService.get(WALLET_ID).getCash();
        Assert.assertEquals(300.5d, stationCash, 0.0001d);
    }

    @Test
    public void transferToStationFromUser() throws Exception {
        accountService.transferToStation(USER_ID_2, 5.00d);
        Double stationCash = walletService.get(WALLET_ID).getCash();
        Double userCash = walletService.get(USER_ID_2).getCash();
        Assert.assertEquals(205.5d, stationCash, 0.0001d);
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