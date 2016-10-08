package com.uran.gamblingstation;

import com.uran.gamblingstation.matcher.ModelMatcher;
import com.uran.gamblingstation.model.Wallet;

import java.util.Objects;

public class WalletTestData {
    public static final ModelMatcher<Wallet> WALLET_MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getCash(), actual.getCash())
                    )
    );
}
