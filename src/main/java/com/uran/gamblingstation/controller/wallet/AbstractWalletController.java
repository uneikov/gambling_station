package com.uran.gamblingstation.controller.wallet;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.Wallet;
import com.uran.gamblingstation.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractWalletController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractWalletController.class);

    @Autowired private WalletService service;

    public Wallet get(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("get wallet {} for User {}", id, userId);
        return service.get(id);
    }

    public void delete(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("delete wallet {} for User {}", id, userId);
        service.delete(id);
    }

    public List<Wallet> getAll() {
        int userId = AuthorizedUser.id();
        LOG.info("getAll wallets for User {}", userId);
        return service.getAll();
    }

    public void update(Wallet wallet, int id) {
        wallet.setId(id);
        int userId = AuthorizedUser.id();
        LOG.info("update {} for User {}", wallet, userId);
        service.update(wallet);
    }

    public Wallet create(Wallet wallet, int userId) {
        wallet.setId(userId);
        LOG.info("create {} for User {}", wallet, userId);
        return service.save(wallet);
    }
}