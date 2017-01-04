package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.Race;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.scheduler.RaceScheduler;
import com.uran.gamblingstation.to.StakeTo;
import com.uran.gamblingstation.util.stake.StakeUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/ajax/profile/stakes")
public class StakeAjaxController extends AbstractStakeController{

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Stake> getAll() {
        int userId = AuthorizedUser.id();
        return super.getAllByUserId(userId);
    }

    @GetMapping(value = "/cash",produces = MediaType.APPLICATION_JSON_VALUE)
    public Double getAllCash() {
        Race current = RaceScheduler.getCurrentRace();
        return current == null ? 0.0 : StakeUtil.getValue(super.getAllByRaceId(current.getId()));
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Stake get(@PathVariable("id") int id) {
        return super.get(id);
    }


    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@Valid StakeTo stakeTo) {
        if (stakeTo.isNew()) {
            super.create(stakeTo);
        } else {
            super.update(stakeTo);
        }
    }

    @Override
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Stake> getBetween(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "startTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "endTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            @RequestParam(value = "option", required = false) String option )
    {
        return super.getBetween(startDate, startTime, endDate, endTime, option);
    }

}
