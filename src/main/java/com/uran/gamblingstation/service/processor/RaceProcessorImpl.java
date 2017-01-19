package com.uran.gamblingstation.service.processor;

import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.HorseService;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.service.account.AccountService;
import com.uran.gamblingstation.util.stake.StakeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RaceProcessorImpl implements RaceProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(RaceProcessor.class);

    private final StakeService stakeService;
    private final AccountService accountService;
    private final HorseService horseService;

    public RaceProcessorImpl(StakeService stakeService,
                             AccountService accountService,
                             HorseService horseService) {
        this.stakeService = stakeService;
        this.accountService = accountService;
        this.horseService = horseService;
    }

    @Override
    @Transactional
    public void process(final int horseId, final int raceId) {
        horseService.update(horseService.get(horseId).addWins());
        stakeService.setWinningStakes(horseId, raceId);
        final Double allCash = stakeService.getAllCash(raceId);
        List<Stake> winningStakes = stakeService.getWinningStakes(raceId);
        if (!winningStakes.isEmpty()) {
            final Double winCash = StakeUtil.getValuesSum(winningStakes);
            final Double winRatio = allCash / winCash;
            final Map<Integer, Double> winningMap = winningStakes.stream()
                    .collect(Collectors.toMap(s -> s.getUser().getId(), s -> s.getStakeValue() * winRatio));
            stakeService.processWinningStakes(winningStakes, winningMap);
            stakeService.getWinningStakes(raceId)
                    .forEach(stake -> accountService.transferToUser(stake.getUser().getId(), stake.getAmount()));
            LOG.info("Winning stakes count: {}, all cash from race stakes: {}", winningStakes.size(), allCash);
        } else {
            LOG.info("Nobody wins, station revenue: {}", allCash);
        }
    }

    @Override
    public void forbidStakeEditing(final int raceId) {
        stakeService.setNotEditable(raceId);
    }
}
