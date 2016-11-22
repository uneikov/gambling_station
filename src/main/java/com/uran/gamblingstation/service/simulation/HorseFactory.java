package com.uran.gamblingstation.service.simulation;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class HorseFactory {

    public List<Horse> getHorses(List<Horse> horses, int size){
        List<Horse> randomHorses = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            randomHorses.add(RandomUtil.getRandomHorseFromList(horses));
        }
        return randomHorses;
    }
}
