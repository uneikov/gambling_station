package com.uran.gamblingstation.util.horse;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.to.HorseTo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HorseUtil {

    public static Horse createNewFromTo(final HorseTo newHorse) {
        return new Horse(null, newHorse.getName(), newHorse.getRuName(), newHorse.getAge(), 0, false);
    }

    public static Horse updateFromTo(final Horse horse, final HorseTo horseTo) {
        horse.setName(horse.getName());
        horse.setRuName(horseTo.getRuName());
        horse.setAge(horseTo.getAge());
        return horse;
    }

    // return "en_name:ru_name,en_name:ru:name ..."
    public static String getSerialized(final List<Horse> horses) {
        return horses.stream()
                .map(horse -> horse.getName() + ":" + horse.getRuName())
                .collect(Collectors.joining(","));

    }

    // return map(en_name->ru_name, ...)
    private static Map<String, String> getDeserialized(final String horses) {
        return Arrays.stream(horses.split(","))
                .sorted()
                .map(s -> s.split(":"))
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));

    }

}
