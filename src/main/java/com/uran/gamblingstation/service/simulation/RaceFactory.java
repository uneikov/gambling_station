package com.uran.gamblingstation.service.simulation;

import com.uran.gamblingstation.model.Race;

public class RaceFactory {

    private static Race RACE = null;

    private static Race getInstance() {
        if (RACE == null) {
            synchronized (RaceFactory.class) {
                if (RACE == null) // check again within synchronized block to guard for race condition
                    RACE = new Race();
            }
        }
        return RACE;
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
