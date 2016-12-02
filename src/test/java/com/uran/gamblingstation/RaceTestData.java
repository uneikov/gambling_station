package com.uran.gamblingstation;

import com.uran.gamblingstation.matcher.ModelMatcher;
import com.uran.gamblingstation.model.Race;

import java.time.Month;
import java.util.Objects;

import static java.time.LocalDateTime.of;

public class RaceTestData {

    private static final String HORSES_LIST_1 ="Alien:Чужой,Black Ghost:Черный призрак,White Ghost:Белый призрак,Enisei:Енисей,Thunderbird:Гром,Ruby Rose:Рубироуз";
    private static final String HORSES_LIST_2 ="Predator:Хищник,Gulfstream:Гольфстрим,Rabindranate:Рабиндранат,Ruby Rose:Рубироуз,White Ghost:Белый призрак,Angelfire:Энджелфае";

    public static final int RACE_1_ID = 100014;
    public static final int RACE_4_ID = 100017;
    public static final Race RACE_1 = new Race(100014, of(2016, Month.MAY, 30, 10, 0), of(2016, Month.MAY, 30, 10, 45), HORSES_LIST_1, "Ghost:Черный призрак");
    public static final Race RACE_2 = new Race(100015, of(2016, Month.JUNE, 12, 13, 0), of(2016, Month.JUNE, 12, 13, 45), HORSES_LIST_1, "Thunderbird:Гром");
    public static final Race RACE_3 = new Race(100016, of(2016, Month.JUNE, 13, 19, 0), of(2016, Month.JUNE, 13, 19, 45), HORSES_LIST_2, "Predator:Хищник");
    public static final Race RACE_4 = new Race(100017, of(2016, Month.AUGUST, 5, 10, 0), of(2016, Month.AUGUST, 5, 10, 45), HORSES_LIST_2, "Ruby Rose:Рубироуз");

    public static final ModelMatcher<Race> RACE_MATCHER = new ModelMatcher<>(Race.class,
            (expected, actual) -> expected == actual ||
                    ( Objects.equals(expected.getStart(), actual.getStart())
                            && Objects.equals(expected.getFinish(), actual.getFinish())
                            && Objects.equals(expected.getHorses(), actual.getHorses())
                            && Objects.equals(expected.getWinning(), actual.getWinning())
                            //&& Objects.equals(expected.getStakes(), actual.getStakes())
                    )
    );

    public static Race getNewRace(){
        return new Race(null, of(2016, Month.NOVEMBER, 16, 12, 0), of(2016, Month.NOVEMBER, 16, 12, 45), HORSES_LIST_2, "Ruby Rose:Рубироуз");
    }
    public static Race getUpdatedRace(){
        return new Race(100017, of(2016, Month.AUGUST, 5, 10, 0), of(2016, Month.AUGUST, 5, 10, 45), HORSES_LIST_2, "Thunderbird:Гром");
    }

}
