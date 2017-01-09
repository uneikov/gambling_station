package com.uran.gamblingstation.controller.race;

import com.uran.gamblingstation.model.Race;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = RaceRestController.REST_URL)
public class RaceRestController extends AbstractRaceController {
    protected static final String REST_URL = "/rest/admin/races";

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Race> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/with", produces = APPLICATION_JSON_VALUE)
    public List<Race> getAllWithStakes() {
        return super.getAllWithStakes();
    }

}
