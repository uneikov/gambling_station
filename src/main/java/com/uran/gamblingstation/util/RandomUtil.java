package com.uran.gamblingstation.util;

import com.uran.gamblingstation.model.Horse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    public static List<Horse> getHorsesListForRace(List<Horse> allHorses){
        List<Horse> res = new ArrayList<>();
        for (int i = 0; i <6 ; i++) {
            res.add(allHorses.get(ThreadLocalRandom.current().nextInt(allHorses.size())));
        }
        return res;
    }

    public static int getWinningHorse(){
        return ThreadLocalRandom.current().nextInt(6);
    }
}
