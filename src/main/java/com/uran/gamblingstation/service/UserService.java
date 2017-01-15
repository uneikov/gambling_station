package com.uran.gamblingstation.service;


import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.to.UserDTO;
import com.uran.gamblingstation.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User save(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    void update(UserDTO userDTO);

    List<User> getAll();

    void update(User user);

    void enable(int id, boolean enable);

    // void evictCache();
}
