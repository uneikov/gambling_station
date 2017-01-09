package com.uran.gamblingstation.service.simulation;

import com.uran.gamblingstation.model.Wallet;
import com.uran.gamblingstation.service.AbstractServiceTest;
import com.uran.gamblingstation.service.UserService;
import com.uran.gamblingstation.service.WalletService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RaceSimulationHelperTest extends AbstractServiceTest {

    @Autowired
    private RaceSimulationHelper helper;
    @Autowired
    private UserService userService;
    @Autowired
    private WalletService walletService;

    @Before
    public void setUp() {
        testName = getClass().getSimpleName();
    }

    @Test
    public void createBots() throws Exception {
        helper.createBots(5);
        Assert.assertTrue(userService.getAll().size() == 9);
    }

    @Test
    public void fillWallets() throws Exception {
        helper.createBots(5);
        helper.fillWallets();
        final List<Wallet> all = walletService.getAll();
        Assert.assertTrue(all.get(5).getCash() >= 10);
    }

    @Test
    public void killBots() throws Exception {
        helper.createBots(5);
        helper.killBots();
        Assert.assertEquals(4, userService.getAll().size());
    }

}