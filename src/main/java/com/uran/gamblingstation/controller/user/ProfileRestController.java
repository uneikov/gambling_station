package com.uran.gamblingstation.controller.user;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * GKislin
 * 06.03.2015.
 */
@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController {
    public static final String REST_URL = "/rest/profile";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return super.get(AuthorizedUser.id());
    }

    @DeleteMapping
    public void delete() {
        super.delete(AuthorizedUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user) {
        super.update(user, AuthorizedUser.id());
    }

    @GetMapping(value = "/text")
    public String testUTF() {
        return "Русский текст";
    }
}