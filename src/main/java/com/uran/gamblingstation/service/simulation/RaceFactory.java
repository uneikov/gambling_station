package com.uran.gamblingstation.service.simulation;

import com.uran.gamblingstation.model.Race;

public class RaceFactory {

    private static Race RACE = null;

    private static Race getInstance() {
        return RACE == null ? new Race() : RACE;
    }

    public static Race open(){
        return getInstance();
    }

    public static void close(){
        RACE = null;
    }

    private RaceFactory() {
    }
}
