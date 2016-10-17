package com.uran.gamblingstation.repository;

import com.uran.gamblingstation.model.Wallet;

import java.util.List;

public interface WalletRepository {

    Wallet save(Wallet wallet);

    void update(Wallet wallet);

    // false if not found
    boolean delete(int id);

    // null if not found
    Wallet get(int id);

    List<Wallet> getAll();
}
