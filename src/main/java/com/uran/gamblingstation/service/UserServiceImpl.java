package com.uran.gamblingstation.service;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.model.Wallet;
import com.uran.gamblingstation.repository.UserRepository;
import com.uran.gamblingstation.to.UserTo;
import com.uran.gamblingstation.util.exception.ExceptionUtil;
import com.uran.gamblingstation.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.uran.gamblingstation.util.user.UserUtil.prepareToSave;
import static com.uran.gamblingstation.util.user.UserUtil.updateFromTo;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired private UserRepository repository;
    @Autowired private WalletService walletService;

    @Override
    @Transactional
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        User saved = repository.save(prepareToSave(user));
        walletService.save(new Wallet(saved.getId(), 0.0d));
        return saved;
        /*return repository.save(prepareToSave(user));*/
    }

    @Override
    public void delete(int id) {
        ExceptionUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public User get(int id) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return ExceptionUtil.checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    @Transactional
    public void update(UserTo userTo) {
        User user = updateFromTo(get(userTo.getId()), userTo);
        repository.save(prepareToSave(user));
    }

    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        repository.save(prepareToSave(user));
    }

    @Override
    @Transactional
    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
        repository.save(user);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = repository.getByEmail(email.toLowerCase());
        if (u == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(u);
    }
}
