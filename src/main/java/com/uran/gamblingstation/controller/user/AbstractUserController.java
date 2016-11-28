package com.uran.gamblingstation.controller.user;

import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.model.Wallet;
import com.uran.gamblingstation.service.UserService;
import com.uran.gamblingstation.service.WalletService;
import com.uran.gamblingstation.to.UserTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired private UserService userService;
    @Autowired private WalletService walletService;

    public List<User> getAll() {
        log.info("getAll");
        return userService.getAll();
    }

    public User get(int id) {
        log.info("get " + id);
        return userService.get(id);
    }

    @Transactional
    public User create(User user) {
        user.setId(null);
        log.info("create " + user);
        //даем ему кошелек!!!
        User created = userService.save(user);
        walletService.save(new Wallet(created.getId(), 0.0d));
        return created;
    }

    public void delete(int id) {
        log.info("delete " + id);
        userService.delete(id);
    }

    public void update(User user, int id) {
        user.setId(id);
        log.info("update " + user);
        userService.update(user);
    }

    public void update(UserTo userTo) {
        log.info("update " + userTo);
        userService.update(userTo);
    }

    public User getByMail(String email) {
        log.info("getByEmail " + email);
        return userService.getByEmail(email);
    }

    void enable(int id, boolean enabled) {
        log.info((enabled ? "enable " : "disable ") + id);
        userService.enable(id, enabled);
    }
}
