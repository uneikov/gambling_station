package com.uran.gamblingstation.controller.user;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.User;
import org.springframework.stereotype.Controller;


/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class ProfileRestController extends AbstractUserController {

    public User get() {
        return super.get(AuthorizedUser.id());
    }

    public void delete() {
        super.delete(AuthorizedUser.id());
    }

    public void update(User user) {
        super.update(user, AuthorizedUser.id());
    }
}