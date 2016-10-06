package com.uran.gamblingstation.repository;


import com.uran.gamblingstation.model.User;

import java.util.List;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public interface UserRepository {
    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();
}
