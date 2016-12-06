package com.uran.gamblingstation;

import com.uran.gamblingstation.matcher.ModelMatcher;
import com.uran.gamblingstation.model.Wallet;

import java.util.Objects;

import static com.uran.gamblingstation.UserTestData.*;

public class WalletTestData {
    public static final ModelMatcher<Wallet> WALLET_MATCHER = new ModelMatcher<>(Wallet.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getCash(), actual.getCash())
                    )
    );

    public static final Wallet WALLET_1 = new Wallet(USER_ID_1, 10.0d);
    public static final Wallet WALLET_2 = new Wallet(USER_ID_2, 15.0d);
    public static final Wallet WALLET_ADMIN = new Wallet(ADMIN_ID, 0.0d);

    public static final Wallet WALLET_STATION = new Wallet(STATION_ID, 200.5d);
    public static final Wallet WALLET_1_UP = new Wallet(USER_ID_1, 200.44d);
}
