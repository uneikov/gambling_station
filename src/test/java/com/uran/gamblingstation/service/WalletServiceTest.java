package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Wallet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.uran.gamblingstation.UserTestData.USER_ID_1;
import static com.uran.gamblingstation.WalletTestData.WALLET_MATCHER;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-database.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:database/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class WalletServiceTest {

    @Autowired
    private WalletService service;

    @Test
    public void testSave() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        Wallet wallet = service.get(USER_ID_1);
        WALLET_MATCHER.assertEquals(wallet, new Wallet(USER_ID_1, 10.0));
    }

    @Test
    public void testGetAll() throws Exception {
       /* service.getAll();*/
    }

    @Test
    public void testWalletIsEmpty() throws Exception {
        Wallet wallet = service.get(USER_ID_1);
        Assert.assertEquals(wallet.isEmpty(), false);
    }

}