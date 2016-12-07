package com.uran.gamblingstation.service.simulation;

import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.model.Wallet;
import com.uran.gamblingstation.service.AbstractServiceTest;
import com.uran.gamblingstation.service.UserService;
import com.uran.gamblingstation.service.WalletService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RaceSimulationHelperlTest extends AbstractServiceTest{

    @Autowired RaceSimulationHelper helper;
    @Autowired UserService userService;
    @Autowired WalletService walletService;

   /* @Test
    public void selectHorsesForRace() throws Exception {

    }

    @Test
    public void getHorsesForRace() throws Exception {

    }*/

    @Test
    public void createBots() throws Exception {
        helper.createBots();
        Assert.assertTrue(userService.getAll().size() >= 30);
    }

    @Test
    public void fillWallets() throws Exception {
        helper.createBots();
        final User user = userService.getByEmail("testuser33@test.com");
        user.getWallet();
        helper.fillWallets();
        final List<Wallet> all = walletService.getAll();
        Assert.assertTrue(all.get(29).getCash() >= 10);
    }

   /* @Test
    public void clearWallets() throws Exception {

    }*/

    @Test
    public void killBots() throws Exception {
        helper.createBots();
        helper.killBots();
        Assert.assertEquals(4, userService.getAll().size());
    }

   /* @Test
    public void startGamble() throws Exception {

    }*/

}