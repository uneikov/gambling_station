package com.uran.gamblingstation.controller.race;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.uran.gamblingstation.service.scheduler.RaceScheduler.RACE_IS_RUNNING;
import static com.uran.gamblingstation.service.scheduler.RaceScheduler.USERS_CAN_MAKE_STAKES;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/ajax/profile/races")
public class UserRaceAjaxController {

    @GetMapping(value = "/run", produces = APPLICATION_JSON_VALUE)
    public String getIsUserCanMakeStakes() {
        return USERS_CAN_MAKE_STAKES ? "enabled" : "disabled";
    }

    @GetMapping(value = "/can", produces = APPLICATION_JSON_VALUE)
    public String getIsUserCanEditStakes() {
        return !USERS_CAN_MAKE_STAKES && !RACE_IS_RUNNING ? "editable" : "not_editable";
    }
}
