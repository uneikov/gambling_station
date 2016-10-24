package com.uran.gamblingstation;

import com.uran.gamblingstation.matcher.ModelMatcher;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.model.User;

import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.uran.gamblingstation.HorseTestData.*;
import static com.uran.gamblingstation.UserTestData.USER_1;
import static com.uran.gamblingstation.UserTestData.USER_2;
import static java.time.LocalDateTime.of;

public class StakeTestData {

    public static final int STAKE_1_ID = 100014;
    public static final int STAKE_2_ID = 100015;
    public static final int STAKE_3_ID = 100016;
    public static final int STAKE_4_ID = 100017;
    public static final int STAKE_5_ID = 100018;
    public static final User WINNING_USER = USER_1;

    public static final Stake STAKE_1 =
            new Stake(USER_1, HORSE_4, 100.25, of(2016, Month.MAY, 30, 10, 0).truncatedTo(ChronoUnit.SECONDS), true, 0.0);

    public static final Stake STAKE_2 =
            new Stake(USER_2, HORSE_5, 100.25, of(2016, Month.JUNE, 12, 13, 30).truncatedTo(ChronoUnit.SECONDS), false, 0.0);

    public static final Stake STAKE_3 =
            new Stake(USER_1, HORSE_6, 100.25, of(2016, Month.JUNE, 13, 19, 45).truncatedTo(ChronoUnit.SECONDS), false, 0.0);

    public static final Stake STAKE_4 =
            new Stake(USER_2, HORSE_4, 100.25, of(2016, Month.AUGUST, 5, 10, 9).truncatedTo(ChronoUnit.SECONDS), false, 0.0);

    public static final Stake STAKE_5 =
            new Stake(USER_1, HORSE_4, 100.25, of(2016, Month.AUGUST, 5, 10, 10).truncatedTo(ChronoUnit.SECONDS), false, 0.0);

    public static final Stake STAKE_4_WIN =
            new Stake(USER_2, HORSE_4, 100.25, of(2016, Month.AUGUST, 5, 10, 9).truncatedTo(ChronoUnit.SECONDS), true, 10.0);

    public static final Stake STAKE_5_WIN =
            new Stake(USER_1, HORSE_4, 100.25, of(2016, Month.AUGUST, 5, 10, 10).truncatedTo(ChronoUnit.SECONDS), true, 20.0);

    public static final List<Stake> STAKES = Arrays.asList(STAKE_5, STAKE_4, STAKE_3, STAKE_2, STAKE_1);

    public static final ModelMatcher<Stake> STAKE_MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    ( Objects.equals(expected.getUser().getId(), actual.getUser().getId())
                            && Objects.equals(expected.getHorse().getId(), actual.getHorse().getId())
                            && Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getStakeValue(), actual.getStakeValue())
                            && Objects.equals(expected.getWins(), actual.getWins())
                    )
    );

    public static Stake getCreated() {
        return new Stake(USER_1, HORSE_1, 80.25, of(2016, Month.OCTOBER, 3, 10, 35).truncatedTo(ChronoUnit.SECONDS), false, 0.0);
    }
    public static Stake getUpdated() {
        return new Stake(STAKE_1_ID, USER_1, HORSE_6, 100.25, of(2016, Month.MAY, 30, 10, 0).truncatedTo(ChronoUnit.SECONDS), false, 0.0);
    }
}
