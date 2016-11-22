package com.uran.gamblingstation.service.simulation;

import com.uran.gamblingstation.model.Role;
import com.uran.gamblingstation.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BotFactory {
    List<User> getBots(int size){
        List<User> bots = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            User user = new User(
                    null,
                    "testuser"+i,
                    "testuser"+i+"@test.com",
                    "testpass"+i,
                    true,
                    Collections.singleton(Role.ROLE_USER)
            );
            bots.add(user);
        }
        return bots;
    }
}
