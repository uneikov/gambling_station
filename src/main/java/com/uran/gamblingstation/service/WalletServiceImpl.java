package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Wallet;
import com.uran.gamblingstation.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletRepository repository;

    @Override
    public Wallet save(Wallet wallet) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Wallet get(int id) {
        return repository.get(id);
    }

    @Override
    public List<Wallet> getAll() {
        return repository.getAll();
    }
}
