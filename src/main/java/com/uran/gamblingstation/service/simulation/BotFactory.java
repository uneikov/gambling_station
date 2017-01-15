package com.uran.gamblingstation.service.simulation;

import com.uran.gamblingstation.model.Role;
import com.uran.gamblingstation.model.User;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class BotFactory {
    List<User> getBots(int size) {
        return IntStream
                .range(0, size)
                .mapToObj(i -> new User(
                        null,
                        "testuser" + i,
                        "testuser" + i + "@test.com",
                        "testpass" + i,
                        true,
                        Collections.singleton(Role.ROLE_USER)
                ))
                .collect(Collectors.toList());
    }
}