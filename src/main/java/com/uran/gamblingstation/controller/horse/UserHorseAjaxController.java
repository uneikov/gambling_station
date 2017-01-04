package com.uran.gamblingstation.controller.horse;

import com.uran.gamblingstation.model.Horse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ajax/profile/horses")
public class UserHorseAjaxController extends AbstractHorseController {

    @GetMapping(value = "/names", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllReadyForRaceHorsesNamesAsList() {
        return getAll().stream().filter(Horse::isReady).map(Horse::getName).collect(Collectors.toList());
    }
}
