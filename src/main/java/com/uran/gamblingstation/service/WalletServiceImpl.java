package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Wallet;
import com.uran.gamblingstation.repository.WalletRepository;
import com.uran.gamblingstation.util.exception.ExceptionUtil;
import com.uran.gamblingstation.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletRepository repository;

    @Override
    public Wallet save(Wallet wallet) {
        Assert.notNull(wallet, "wallet must not be null");
        ExceptionUtil.checkPositive(wallet.getCash());
        return repository.save(wallet);
    }

    @Override
    public void update(Wallet wallet) {
        Assert.notNull(wallet, "wallet must not be null");
        ExceptionUtil.checkPositive(wallet.getCash());
        repository.update(wallet);
    }

    @Override
    public void delete(int id){
        ExceptionUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Wallet get(int id) throws NotFoundException{
        return ExceptionUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public List<Wallet> getAll() {
        return repository.getAll();
    }
}
