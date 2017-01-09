package com.uran.gamblingstation.util.stake;

import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.util.TimeUtil;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StakeUtil {

    public static Double getValuesSum(List<Stake> stakes) {
        return stakes.stream().mapToDouble(Stake::getStakeValue).sum();
    }

    public static List<Stake> getFilteredByTimeAndWins(List<Stake> stakes, LocalTime start, LocalTime end, String option) {
        final String filterOption = option == null ? "all" : option;
        Stream<Stake> streamFiltered = stakes.stream()
                .filter(stake -> TimeUtil.isBetween(stake.getDateTime().toLocalTime(), start, end));

        switch (filterOption) {
            case "winned":
                streamFiltered = streamFiltered.filter(Stake::isWins);
                break;
            case "loosed":
                streamFiltered = streamFiltered.filter(stake -> !stake.isWins());
                break;
            default:
                break;
        }

        return streamFiltered.collect(Collectors.toList());
    }

}
