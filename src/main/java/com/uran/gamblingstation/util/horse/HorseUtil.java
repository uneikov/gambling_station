package com.uran.gamblingstation.util.horse;

import com.uran.gamblingstation.model.Horse;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HorseUtil {
    public static String getSerialized(List<Horse> horses){
        return horses.stream()
                .map(horse -> horse.getName() + ":" + horse.getRuName())
                .collect(Collectors.joining(","));
        // return "en_name:ru_name,en_name:ru:name ..."
    }

    public static Map<String,String> getDeserialized(String horses){
        return Arrays.stream(horses.split(","))
                .sorted()
                .map(s -> s.split(":"))
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));
        // return map(en_name->ru_name, ...)
    }

    public static List<String> getEnNames(String serialized) {
        return getDeserialized(serialized).entrySet().stream()
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<String> getRuNames(String serialized) {
        return getDeserialized(serialized).entrySet().stream()
                .map(Map.Entry::getValue)
                .sorted()
                .collect(Collectors.toList());
    }
}
