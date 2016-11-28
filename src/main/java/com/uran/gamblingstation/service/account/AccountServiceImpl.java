package com.uran.gamblingstation.service.account;

import com.uran.gamblingstation.model.Role;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.model.Wallet;
import com.uran.gamblingstation.service.UserService;
import com.uran.gamblingstation.service.WalletService;
import com.uran.gamblingstation.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired private WalletService walletService;
    @Autowired private UserService userService;

    @Override
    public void transferToStation(int userId, Double value) {
        debitAccount(userId, value);
        addToStationAccount(value);
    }

    @Override
    public void transferToUser(int userId, Double value) {
        debitStationAccount(value);
        addToAccount(userId, value);
    }

    @Override
    public void addToStationAccount(Double value) {
        Wallet stationWallet = getStationWallet();
        stationWallet.setCash(stationWallet.getCash() + value);
        walletService.update(stationWallet);
    }

    @Override
    public void debitStationAccount(Double value) {
        Wallet stationWallet = getStationWallet();
        stationWallet.setCash(stationWallet.getCash() - value);
        walletService.update(stationWallet);
    }

    @Override
    public void addToAccount(int userId, Double value) {
        Wallet userWallet = walletService.get(userId);
        userWallet.setCash(userWallet.getCash() + value);
        walletService.update(userWallet);
    }

    public void debitAccount(int userId, Double value){
        Wallet userWallet = walletService.get(userId);
        userWallet.setCash(userWallet.getCash() - value);
        walletService.update(userWallet);
    }

    /* public void debitAccounts(LocalDateTime startDate, LocalDateTime endDate){
        stakeService.getBetween(startDate, endDate)
                .forEach(stake -> debitAccount(stake.getUser().getId(), stake.getStakeValue()));
    }
    */
    private Wallet getStationWallet(){
        Wallet walletStation;

        User userStation = userService.getAll().stream()
                .filter(user -> user.getRoles().contains(Role.ROLE_STATION))
                .findFirst()
                .orElse(null);

        if (userStation != null) {
            walletStation = userStation.getWallet();
        }
        else throw new NotFoundException("No station as user.");

        if (walletStation != null) {
            return walletStation;
        }
        else throw new NotFoundException("No station wallet.");
    }
}
