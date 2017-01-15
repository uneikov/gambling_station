package com.uran.gamblingstation.controller.race;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.uran.gamblingstation.service.scheduler.RaceScheduler.isRaceIsRunning;
import static com.uran.gamblingstation.service.scheduler.RaceScheduler.isUsersCanMakeStakes;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/ajax/profile/races")
public class UserRaceAjaxController {

    @GetMapping(value = "/run", produces = APPLICATION_JSON_VALUE)
    public String getIsUserCanMakeStakes() {
        return isUsersCanMakeStakes() ? "enabled" : "disabled";
    }

    @GetMapping(value = "/can", produces = APPLICATION_JSON_VALUE)
    public String getIsUserCanEditStakes() {
        return !isUsersCanMakeStakes() && !isRaceIsRunning() ? "editable" : "not_editable";
    }
}
