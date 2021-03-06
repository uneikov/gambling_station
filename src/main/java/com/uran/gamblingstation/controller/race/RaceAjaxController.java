package com.uran.gamblingstation.controller.race;

import com.uran.gamblingstation.model.Race;
import com.uran.gamblingstation.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.uran.gamblingstation.service.scheduler.RaceScheduler.isUsersCanMakeStakes;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/ajax/admin/races")
public class RaceAjaxController extends AbstractRaceController {

    @Autowired
    private RaceService raceService;

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Race> getAll() {
        return raceService.getAll();
    }

    @GetMapping(value = "/with", produces = APPLICATION_JSON_VALUE)
    public List<Race> getWithStakes() {
        return super.getAllWithStakes();
    }

    @Override
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Race get(@PathVariable("id") final int id) {
        return raceService.get(id);
    }

    @GetMapping(value = "/run", produces = APPLICATION_JSON_VALUE)
    public String get() {
        return isUsersCanMakeStakes() ? "enabled" : "disabled";
    }

}
