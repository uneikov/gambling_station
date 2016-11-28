package com.uran.gamblingstation.service.account;

public interface AccountService {
    void addToStationAccount(Double value);
    void transferToStation(int userId, Double value);
    void addToAccount(int userId, Double value);
    void debitAccount(int userId, Double value);
    //void debitAccounts(LocalDateTime startDate, LocalDateTime endDate);
    void debitStationAccount(Double value);
    void transferToUser(int userId, Double value);
}
