package com.uran.gamblingstation.controller.race;

import com.uran.gamblingstation.model.Race;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RaceRestController.REST_URL)
public class RaceRestController extends AbstractRaceController{
    static final String REST_URL = "/rest/races";
    private static final String contentType = MediaType.APPLICATION_JSON_VALUE;

    @GetMapping(produces = contentType)
    public List<Race> getAll() {
        return super.getAll();
    }

    @GetMapping(value = "/with", produces = contentType)
    public List<Race> getWithStakes() {
        return super.getAllWithStakes();
    }
}
