package com.uran.gamblingstation;

import com.uran.gamblingstation.matcher.ModelMatcher;
import com.uran.gamblingstation.model.Horse;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.uran.gamblingstation.model.BaseEntity.START_SEQ;

public class HorseTestData {

    public static final int HORSE_1_ID = START_SEQ + 4;

    public static final Horse HORSE_1 = new Horse(HORSE_1_ID,     "Black Ghost", "Черный призрак", 5, 0, true);
    public static final Horse HORSE_2 = new Horse(HORSE_1_ID + 1, "White Ghost", "Белый призрак", 5, 0, true);
    public static final Horse HORSE_3 = new Horse(HORSE_1_ID + 2, "Enisei", "Енисей", 3, 0);
    public static final Horse HORSE_4 = new Horse(HORSE_1_ID + 3, "Thunderbird", "Гром", 5, 0, true);
    public static final Horse HORSE_5 = new Horse(HORSE_1_ID + 4, "Ruby Rose", "Рубироуз", 4, 0, true);
    public static final Horse HORSE_6 = new Horse(HORSE_1_ID + 5, "Predator", "Хищник", 5, 0, true);
    public static final Horse HORSE_7 = new Horse(HORSE_1_ID + 6, "Alien", "Чужой", 6, 0, true);
    public static final Horse HORSE_8 = new Horse(HORSE_1_ID + 7, "Gulfstream", "Гольфстрим", 3, 0);
    public static final Horse HORSE_9 = new Horse(HORSE_1_ID + 8, "Rabindranate", "Рабиндранат", 5, 0);
    public static final Horse HORSE_10 = new Horse(HORSE_1_ID + 9,"Angelfire", "Энджелфае", 5, 0);
    static final Horse HORSE_4_WINS = new Horse(HORSE_1_ID + 3, "Thunderbird", "Гром", 5, 1);

    public static final Horse WINNING_HORSE = HORSE_4;

    public static final List<Horse> HORSES =
            Arrays.asList(HORSE_7, HORSE_10, HORSE_1, HORSE_3, HORSE_8, HORSE_6, HORSE_9, HORSE_5, HORSE_4, HORSE_2);

    public static final ModelMatcher<Horse> HORSE_MATCHER = new ModelMatcher<>(Horse.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getRuName(), actual.getRuName())
                            && Objects.equals(expected.getAge(), actual.getAge())
                            && Objects.equals(expected.getWins(), actual.getWins())
                            && Objects.equals(expected.isReady(), actual.isReady())
                    )
    );

    public static Horse getUpdated(){
        return new Horse(HORSE_1_ID, "Captain", "Капитан", 5, 0);
    }
    public static Horse getCreated(){
        return new Horse(HORSE_1_ID + 10, "Captain", "Капитан", 5, 0);
    }

}
