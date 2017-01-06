package com.uran.gamblingstation.controller.user;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.to.UserTo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * GKislin
 * 06.03.2015.
 */
@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController {
    protected static final String REST_URL = "/rest/profile";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return super.get(AuthorizedUser.id());
    }

    @DeleteMapping
    public void delete() {
        super.delete(AuthorizedUser.id());
    }

    @Override
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody UserTo userTo) {
        userTo.setId(AuthorizedUser.id());
        super.update(userTo);
    }

    @GetMapping(value = "/text")
    public String testUTF() {
        return "Русский текст";
    }
}