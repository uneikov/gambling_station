package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Wallet;

import java.util.List;

public interface WalletService {

    Wallet save(Wallet wallet);

    void update(Wallet wallet);
    // false if not found
    void delete(int id);

    // null if not found
    Wallet get(int id);

    List<Wallet> getAll();
}
