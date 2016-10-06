package com.uran.gamblingstation.service;


import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.util.exception.NotFoundException;

import java.util.List;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public interface UserService {

    User save(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    List<User> getAll();

    void update(User user);
}
