package com.uran.gamblingstation.controller.user;

import com.uran.gamblingstation.Profiles;
import com.uran.gamblingstation.model.BaseEntity;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.service.UserService;
import com.uran.gamblingstation.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractUserController {
    private final Logger log = LoggerFactory.getLogger(AbstractUserController.class);

    @Autowired
    private UserService userService;

    private boolean systemUserForbiddenModification;

    @Autowired
    public void setEnvironment(Environment environment) {
        systemUserForbiddenModification = Arrays.asList(environment.getActiveProfiles()).contains(Profiles.HEROKU);
    }

    private void checkModificationAllowed(Integer id) {
        if (systemUserForbiddenModification && id < BaseEntity.START_SEQ + 4) {
            throw new ValidationException("Admin/User modification is not allowed. <br><br><a class=\"btn btn-primary btn-lg\" role=\"button\" href=\"register\">Register &raquo;</a> your own please.");
        }
    }

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
        return userService.save(user);
    }

    public void delete(int id) {
        checkModificationAllowed(id);
        log.info("delete " + id);
        userService.delete(id);
    }

    public void update(User user, int id) {
        checkModificationAllowed(id);
        user.setId(id);
        log.info("update " + user);
        userService.update(user);
    }

    public void update(UserDTO userDTO) {
        checkModificationAllowed(userDTO.getId());
        log.info("update " + userDTO);
        userService.update(userDTO);
    }

    public User getByMail(String email) {
        log.info("getByEmail " + email);
        return userService.getByEmail(email);
    }

    void enable(int id, boolean enabled) {
        checkModificationAllowed(id);
        log.info((enabled ? "enable " : "disable ") + id);
        userService.enable(id, enabled);
    }
}
