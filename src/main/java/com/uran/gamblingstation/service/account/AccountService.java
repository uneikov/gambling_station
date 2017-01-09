package com.uran.gamblingstation.service.account;

import com.uran.gamblingstation.model.Wallet;

public interface AccountService {

    void addToStationAccount(Double value);

    void transferToStation(int userId, Double value);

    void addToAccount(int userId, Double value);

    void debitAccount(int userId, Double value);

    void debitStationAccount(Double value);

    void transferToUser(int userId, Double value);

    Wallet getStationWallet();
}
