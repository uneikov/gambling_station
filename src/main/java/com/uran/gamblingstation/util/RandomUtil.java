package com.uran.gamblingstation.util;

import com.uran.gamblingstation.model.Horse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.uran.gamblingstation.model.BaseEntity.NUMBER_OF_HORSES_FOR_RACE;

public class RandomUtil {

    public static List<Horse> getHorsesForRace(List<Horse> allHorses){
        Set<Horse> horsesForRace = new HashSet<>();
        while (horsesForRace.size() < NUMBER_OF_HORSES_FOR_RACE){
            horsesForRace.add(allHorses.get(ThreadLocalRandom.current().nextInt(allHorses.size())));
        }
        return new ArrayList<>(horsesForRace);
    }

    public static Horse getRandomHorseFromList(List<Horse> allHorses) {
        return allHorses.get(ThreadLocalRandom.current().nextInt(6));
    }

    public static List<Integer> getRandomTimePoints(int start, int end, int count) {
        return ThreadLocalRandom.current()
                .ints(count, start, end)
                .boxed()
                .sorted()
                .collect(Collectors.toList());
    }
}
