package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Wallet;

import java.util.List;

public interface WalletService {
    Wallet save(Wallet wallet);

    // false if not found
    boolean delete(int id);

    // null if not found
    Wallet get(int id);

    List<Wallet> getAll();
}
