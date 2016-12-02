package com.uran.gamblingstation.controller.race;

import com.uran.gamblingstation.model.Race;
import com.uran.gamblingstation.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.uran.gamblingstation.model.BaseEntity.USERS_CAN_MAKE_STAKES;

@RestController
@RequestMapping("/ajax/profile/races")
public class UserRaceAjaxController extends AbstractRaceController{

    @Autowired RaceService raceService;

    @GetMapping(value = "/run", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getEnabled() {
        return USERS_CAN_MAKE_STAKES ? "enabled" : "disabled";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Race> getWithStakes() {
        return raceService.getAllWithStakes();
    }
}