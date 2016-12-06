package com.uran.gamblingstation.controller.race;

import com.uran.gamblingstation.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.uran.gamblingstation.model.BaseEntity.RACE_IS_RUNNING;
import static com.uran.gamblingstation.model.BaseEntity.USERS_CAN_MAKE_STAKES;

@RestController
@RequestMapping("/ajax/profile/races")
public class UserRaceAjaxController extends AbstractRaceController{

    @Autowired RaceService raceService;

    @GetMapping(value = "/run", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getEnabled() {
        return USERS_CAN_MAKE_STAKES ? "enabled" : "disabled";
    }

    @GetMapping(value = "/can", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getIsEditable() {
        return !USERS_CAN_MAKE_STAKES && !RACE_IS_RUNNING ? "editable" : "not_editable";
    }
}
