package com.uran.gamblingstation.controller.race;

import com.uran.gamblingstation.model.Race;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = RaceRestController.REST_URL)
public class RaceRestController extends AbstractRaceController{
    static final String REST_URL = "/rest/admin/races";
    private static final String CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;

    @Override
    @GetMapping(produces = CONTENT_TYPE)
    public List<Race> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/with", produces = CONTENT_TYPE)
    public List<Race> getAllWithStakes() {
        return super.getAllWithStakes();
    }

}
