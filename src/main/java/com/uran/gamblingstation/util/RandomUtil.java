package com.uran.gamblingstation.util;

import com.uran.gamblingstation.model.Horse;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.uran.gamblingstation.service.scheduler.RaceScheduler.NUMBER_OF_HORSES_FOR_RACE;

public class RandomUtil {

    public static List<Horse> getHorsesForRace(final List<Horse> allHorses) {
        final List<Horse> randomHorses = new ArrayList<>(allHorses);
        Collections.shuffle(randomHorses, new Random(System.nanoTime()));
        return randomHorses.subList(0, NUMBER_OF_HORSES_FOR_RACE);
    }

    public static Horse getRandomHorseFromList(final List<Horse> allHorses) {
        return allHorses.get(ThreadLocalRandom.current().nextInt(NUMBER_OF_HORSES_FOR_RACE));
    }

    public static List<Integer> getRandomTimePoints(final int start, final int end, final int count) {
        return ThreadLocalRandom.current()
                .ints(count, start, end)
                .boxed()
                .sorted()
                .collect(Collectors.toList());
    }
}
