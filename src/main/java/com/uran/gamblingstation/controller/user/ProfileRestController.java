package com.uran.gamblingstation.controller.user;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.to.UserDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController {
    protected static final String REST_URL = "/rest/profile";

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public User get() {
        return super.get(AuthorizedUser.id());
    }

    @DeleteMapping
    public void delete() {
        super.delete(AuthorizedUser.id());
    }

    @Override
    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody final UserDTO userDTO) {
        userDTO.setId(AuthorizedUser.id());
        super.update(userDTO);
    }

    @GetMapping(value = "/text")
    public String testUTF() {
        return "Русский текст";
    }
}