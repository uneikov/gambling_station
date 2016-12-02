package com.uran.gamblingstation.service;

import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.to.RaceRow;
import com.uran.gamblingstation.util.horse.HorseUtil;
import com.uran.gamblingstation.util.race.RaceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RaceRowServiceImpl implements RaceRowService{

    @Autowired StakeService stakeService;
    @Autowired RaceService raceService;

    @Override
    public List<RaceRow> getRows() { // date & time filter needed ?
        return raceService.getAll().stream().map(race -> {
            final List<Stake> allByRaceId = stakeService.getAllByRaceId(race.getId());
            //final List<Stake> allByRaceId = race.getStakes();
            return new RaceRow(
                    race.getStart(),
                    race.getFinish(),
                    HorseUtil.getEnNames(race.getHorses()),
                    HorseUtil.getRuNames(race.getHorses()),
                    HorseUtil.getEnNames(race.getWinning()).get(0),
                    HorseUtil.getRuNames(race.getWinning()).get(0),
                    allByRaceId.size(),
                    RaceUtil.getValuesSum(allByRaceId),
                    RaceUtil.getAmountsSum(allByRaceId)
            );
        }).collect(Collectors.toList());
    }
}
