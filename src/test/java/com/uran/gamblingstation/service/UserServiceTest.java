package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Role;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.util.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.uran.gamblingstation.UserTestData.*;
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-database.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:database/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceTest {
    @Autowired
    private UserService service;

    @Test
    public void testSave() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", Collections.singleton(Role.ROLE_USER));
        User created = service.save(newUser);
        newUser.setId(created.getId());
        USER_MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, newUser, USER_1, USER_2), service.getAll());
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateMailSave() throws Exception {
        service.save(new User(null, "Duplicate", "user1@yandex.ru", "newPass", Role.ROLE_USER));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER_ID_1);
        USER_MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, USER_2), service.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void testGet() throws Exception {
        User user = service.get(USER_ID_2);
        USER_MATCHER.assertEquals(USER_2, user);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void testGetByEmail() throws Exception {
        User user = service.getByEmail("user1@yandex.ru");
        USER_MATCHER.assertEquals(USER_1, user);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER_2);
        updated.setName("UpdatedName");
        service.update(updated);
        USER_MATCHER.assertEquals(updated, service.get(USER_ID_2));
    }

    @Test
    public void testGetAll() throws Exception {
        List<User> users = service.getAll();
        USER_MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, USER_1, USER_2), users);
    }

    @Test
    public void testUserWallet(){
        Double cash = service.get(USER_ID_2).getWallet().getCash();
        Assert.assertEquals(new Double(10.0), cash);
    }

}