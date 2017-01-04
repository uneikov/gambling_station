package com.uran.gamblingstation.controller.wallet;

import com.uran.gamblingstation.model.Wallet;
import com.uran.gamblingstation.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.uran.gamblingstation.AuthorizedUser.id;

@RestController
@RequestMapping("/ajax/profile/wallets")
public class WalletAjaxController extends AbstractWalletController{

    @Autowired
    AccountService accountService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Wallet get(){
        return super.get(id());
    }

    @GetMapping(value = "/cash", produces = MediaType.APPLICATION_JSON_VALUE)
    public Double getUserCash(){
        return super.get(id()).getCash();
    }

    @GetMapping(value = "/station", produces = MediaType.APPLICATION_JSON_VALUE)
    public Double getStationCash(){
        return super.get(accountService.getStationWallet().getId()).getCash();
    }

    @PutMapping(value = "/add")
    public void add() {
        Wallet wallet = super.get(id());
        wallet.setCash(10.0d);
        super.update(wallet, id());
    }
}
