package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.model.Stake;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(StakeRestController.REST_URL)
public class StakeRestController extends AbstractStakeController {
    protected static final String REST_URL = "/rest/admin/stakes";

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Stake> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/by/{id}", produces = APPLICATION_JSON_VALUE)
    public List<Stake> getAllByUserId(@PathVariable("id") int userId) {
        return super.getAllByUserId(userId);
    }

}
