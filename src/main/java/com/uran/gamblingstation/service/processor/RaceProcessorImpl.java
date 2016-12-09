package com.uran.gamblingstation.service.processor;

import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.HorseService;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.service.account.AccountService;
import com.uran.gamblingstation.util.stake.StakeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RaceProcessorImpl implements RaceProcessor{
    private static final Logger LOG = LoggerFactory.getLogger(RaceProcessor.class);

    @Autowired private StakeService stakeService;
    @Autowired private AccountService accountService;
    @Autowired private HorseService horseService;

    @Override
    public void process(int horseId, int raceId) {
        stakeService.setNotEditable(raceId);
        horseService.update(horseService.get(horseId).addWins());
        stakeService.setWinningStakes(horseId, raceId);
        Double allCash = stakeService.getAllCash(raceId);
        List<Stake> winningStakes = stakeService.getWinningStakes(raceId);
        if (!winningStakes.isEmpty()) {
            Double winCash = StakeUtil.getValue(winningStakes);
            Double winRatio = allCash / winCash;
            Map<Integer, Double> winningMap = winningStakes.stream()
                    .collect(Collectors.toMap(s -> s.getUser().getId(), s -> s.getStakeValue() * winRatio));
            stakeService.processWinningStakes(winningStakes, winningMap);
            stakeService.getWinningStakes(raceId)
                    .forEach(stake -> accountService.transferToUser(stake.getUser().getId(), stake.getAmount()));
            LOG.info("Winning stakes count: {}, all cash from race stakes: {}", winningStakes.size(), allCash);
        } else {
            LOG.info("Nobody wins, station revenue: {}", allCash);
        }
    }
}
