package com.uran.gamblingstation.controller.race;

import com.uran.gamblingstation.model.Race;
import com.uran.gamblingstation.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.uran.gamblingstation.service.scheduler.RaceScheduler.USERS_CAN_MAKE_STAKES;

@RestController
@RequestMapping(value = "/ajax/admin/races")
public class RaceAjaxController extends AbstractRaceController{

    @Autowired RaceService raceService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Race> getAll() {
        return raceService.getAll();
    }

    @GetMapping(value = "/with", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Race> getWithStakes() {
        return super.getAllWithStakes();
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Race get(@PathVariable("id") int id) {
        return raceService.get(id);
    }

    @GetMapping(value = "/run", produces = MediaType.APPLICATION_JSON_VALUE)
    public String get() {
        return USERS_CAN_MAKE_STAKES ? "enabled" : "disabled";
    }

}
