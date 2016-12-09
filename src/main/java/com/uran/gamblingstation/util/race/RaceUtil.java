package com.uran.gamblingstation.util.race;

import com.uran.gamblingstation.model.Race;
import com.uran.gamblingstation.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.List;

public class RaceUtil {

    public static Race getByDateTime(List<Race> races, LocalDateTime between){
        return races.stream()
                .filter(race -> TimeUtil.isBetween(between, race.getStart(), race.getFinish()))
                .findFirst()
                .orElse(null);
    }

}
