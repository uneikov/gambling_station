package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.Race;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.scheduler.RaceScheduler;
import com.uran.gamblingstation.to.StakeDTO;
import com.uran.gamblingstation.util.stake.StakeUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/ajax/profile/stakes")
public class StakeAjaxController extends AbstractStakeController {

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Stake> getAll() {
        int userId = AuthorizedUser.id();
        return super.getAllByUserId(userId);
    }

    @GetMapping(value = "/cash", produces = APPLICATION_JSON_VALUE)
    public Double getAllCashOfCurrentRace() {
        Race current = RaceScheduler.getCurrentRace();
        return current == null ? 0.0 : StakeUtil.getValuesSum(super.getAllByRaceId(current.getId()));
    }

    @Override
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Stake get(@PathVariable("id") final int id) {
        return super.get(id);
    }


    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") final int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@Valid final StakeDTO stakeDTO) {
        if (stakeDTO.isNew()) {
            super.create(stakeDTO);
        } else {
            super.update(stakeDTO);
        }
    }

    @Override
    @PostMapping(value = "/filter", produces = APPLICATION_JSON_VALUE)
    public List<Stake> getBetween(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate startDate,
            @RequestParam(value = "startTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) final LocalTime startTime,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate endDate,
            @RequestParam(value = "endTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) final LocalTime endTime,
            @RequestParam(value = "option", required = false) final String option) {
        return super.getBetween(startDate, startTime, endDate, endTime, option);
    }

}
