package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Wallet;

import java.util.List;

public interface WalletService {

    Wallet save(Wallet wallet, int userId);

    void update(Wallet wallet, int userId);
    // false if not found
    boolean delete(int id, int userId);

    // null if not found
    Wallet get(int id, int userId);

    List<Wallet> getAll();
}
