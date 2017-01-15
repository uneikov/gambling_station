package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Wallet;

import java.util.List;

public interface WalletService {

    Wallet save(Wallet wallet);

    void update(Wallet wallet);

    void delete(int id);

    Wallet get(int id);

    List<Wallet> getAll();
}
