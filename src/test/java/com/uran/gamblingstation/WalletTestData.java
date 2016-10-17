package com.uran.gamblingstation;

import com.uran.gamblingstation.matcher.ModelMatcher;
import com.uran.gamblingstation.model.Wallet;

import java.util.Objects;

import static com.uran.gamblingstation.UserTestData.ADMIN_ID;
import static com.uran.gamblingstation.UserTestData.USER_ID_1;
import static com.uran.gamblingstation.UserTestData.USER_ID_2;

public class WalletTestData {
    public static final ModelMatcher<Wallet> WALLET_MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getCash(), actual.getCash())
                    )
    );

    public static final Wallet WALLET_1 = new Wallet(USER_ID_1, 10.0d);
    public static final Wallet WALLET_2 = new Wallet(USER_ID_2, 15.0d);
    public static final Wallet WALLET_ADMIN = new Wallet(ADMIN_ID, 0.0d);

    public static final Wallet GS_WALLET = new Wallet(ADMIN_ID, 150.35d);
    public static final Wallet WALLET_1_UP = new Wallet(USER_ID_1, 200.44d);
}
