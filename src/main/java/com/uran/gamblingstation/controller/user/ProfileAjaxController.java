package com.uran.gamblingstation.controller.user;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/ajax/profile/users")
public class ProfileAjaxController extends AbstractUserController{

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public User get() {
        return super.get(AuthorizedUser.id());
    }
}
