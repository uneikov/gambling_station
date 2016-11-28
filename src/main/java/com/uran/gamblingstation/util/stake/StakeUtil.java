package com.uran.gamblingstation.util.stake;

import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.util.TimeUtil;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

//@Component
public class StakeUtil {

    public static Double getValue(List<Stake> stakes){
        return stakes.stream().mapToDouble(Stake::getStakeValue).sum();
    }

    public static Double getAmount(List<Stake> stakes){
        return stakes.stream().mapToDouble(Stake::getAmount).sum();
    }

    public static List<Stake> getFilteredByTimeAndWins(List<Stake> stakes, LocalTime start, LocalTime end, String option) {
        final String filterOption = option == null ? "all" : option;
        if ("all".equals(filterOption)) {
            return stakes.stream()
                    .filter(stake -> TimeUtil.isBetween(stake.getDateTime().toLocalTime(), start, end))
                    .collect(Collectors.toList());
        } else {
            return stakes.stream()
                    .filter(stake -> TimeUtil.isBetween(stake.getDateTime().toLocalTime(), start, end)
                            && stake.isWins() == "winned".equals(filterOption))
                    .collect(Collectors.toList());
        }
    }

}
