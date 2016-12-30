package com.uran.gamblingstation.controller.wallet;

import com.uran.gamblingstation.model.Wallet;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.uran.gamblingstation.AuthorizedUser.id;
import static com.uran.gamblingstation.model.BaseEntity.WALLET_ID;

@RestController
@RequestMapping("/ajax/profile/wallets")
public class WalletAjaxController extends AbstractWalletController{

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
        return super.get(WALLET_ID).getCash();
    }

    @PutMapping(value = "/add")
    public void add() {
        Wallet wallet = super.get(id());
        wallet.setCash(10.0d);
        super.update(wallet, id());
    }
}
