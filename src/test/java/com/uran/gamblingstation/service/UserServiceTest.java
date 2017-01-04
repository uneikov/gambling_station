package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Role;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.util.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.uran.gamblingstation.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest{
    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        testName = getClass().getSimpleName();
    }

    @Test
    public void testSave() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", true, Collections.singleton(Role.ROLE_USER));
        userService.save(newUser);
        USER_MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, newUser, STATION, USER_1, USER_2), userService.getAll());
    }

    @Test
    public void testDuplicateMailSave() throws Exception {
        thrown.expect(DataAccessException.class);
        userService.save(new User(null, "Duplicate", "user1@yandex.ru", "newPass", Role.ROLE_USER));
    }

    @Test
    public void testDelete() throws Exception {
        userService.delete(USER_ID_1);
        USER_MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, STATION, USER_2), userService.getAll());
    }

    @Test
    public void testNotFoundDelete() throws Exception {
        thrown.expect(NotFoundException.class);
        userService.delete(1);
    }

    @Test
    public void testGet() throws Exception {
        User user = userService.get(USER_ID_2);
        USER_MATCHER.assertEquals(USER_2, user);
    }

    @Test
    public void testGetNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        userService.get(1);
    }

    @Test
    public void testGetByEmail() throws Exception {
        User user = userService.getByEmail("user1@yandex.ru");
        USER_MATCHER.assertEquals(USER_1, user);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER_2);
        updated.setName("UpdatedName");
        userService.update(updated);
        USER_MATCHER.assertEquals(updated, userService.get(USER_ID_2));
    }

    @Test
    public void testGetAll() throws Exception {
        List<User> users = userService.getAll();
        USER_MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, STATION, USER_1, USER_2), users);
    }

    @Test
    public void testUserWallet(){
        User user = userService.get(USER_ID_2);
        Double cash = user.getWallet().getCash();
        Assert.assertEquals(new Double(15.0), cash);
    }

}