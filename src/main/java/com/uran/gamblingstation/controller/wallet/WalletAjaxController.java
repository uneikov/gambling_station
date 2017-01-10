package com.uran.gamblingstation.controller.wallet;

import com.uran.gamblingstation.model.Wallet;
import com.uran.gamblingstation.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.uran.gamblingstation.AuthorizedUser.id;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/ajax/profile/wallets")
public class WalletAjaxController extends AbstractWalletController {

    @Autowired
    private AccountService accountService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public final Wallet get() {
        return super.get(id());
    }

    @GetMapping(value = "/cash", produces = APPLICATION_JSON_VALUE)
    public final Double getUserCash() {
        return super.get(id()).getCash();
    }

    @GetMapping(value = "/station", produces = APPLICATION_JSON_VALUE)
    public final Double getStationCash() {
        return super.get(accountService.getStationWallet().getId()).getCash();
    }

    @PutMapping(value = "/add")
    public final void add() {
        Wallet wallet = super.get(id());
        wallet.setCash(10.0d);
        super.update(wallet, id());
    }
}
