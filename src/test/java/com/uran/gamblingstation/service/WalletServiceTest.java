package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Role;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.model.Wallet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.uran.gamblingstation.UserTestData.USER_ID_1;
import static com.uran.gamblingstation.WalletTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-database.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:database/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class WalletServiceTest {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Test
    public void testSave() throws Exception {
        User newUser = userService.save(
                new User(null, "agent007", "agent007@mi6.com", "stationpass", true, Collections.singleton(Role.ROLE_USER))
        );
        Wallet newWallet = new Wallet(newUser.getId(), 0.0d);
        walletService.save(newWallet);
        newUser.setWallet(newWallet);
        userService.update(newUser);
        newWallet = newUser.getWallet();
        WALLET_MATCHER.assertCollectionEquals(
                walletService.getAll(),
                Arrays.asList(WALLET_2, WALLET_1, WALLET_ADMIN, WALLET_STATION, newWallet)
        );
    }

    @Test
    public void testUpdate() throws Exception {
        walletService.update(new Wallet(USER_ID_1, 200.44d));
        List<Wallet> wallets = walletService.getAll();
        WALLET_MATCHER.assertCollectionEquals(wallets, Arrays.asList(WALLET_1_UP, WALLET_2, WALLET_ADMIN, WALLET_STATION));
    }

    @Test
    public void testDelete() throws Exception {
        walletService.delete(USER_ID_1);
        List<Wallet> wallets = walletService.getAll();
        WALLET_MATCHER.assertCollectionEquals(wallets, Arrays.asList(WALLET_2, WALLET_ADMIN, WALLET_STATION));
    }

    @Test
    public void testGet() throws Exception {
        Wallet wallet = walletService.get(USER_ID_1);
        WALLET_MATCHER.assertEquals(wallet, new Wallet(USER_ID_1, 10.0));
    }

    @Test
    public void testGetAll() throws Exception {
        List<Wallet> wallets = walletService.getAll();
        WALLET_MATCHER.assertCollectionEquals(wallets, Arrays.asList(WALLET_2, WALLET_1, WALLET_ADMIN, WALLET_STATION));
    }

    @Test
    public void testWalletIsEmpty() throws Exception {
        Wallet wallet = walletService.get(USER_ID_1);
        Assert.assertEquals(wallet.isEmpty(), false);
    }

}