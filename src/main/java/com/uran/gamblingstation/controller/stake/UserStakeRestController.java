package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.model.Stake;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(UserStakeRestController.REST_URL)
public class UserStakeRestController extends AbstractStakeController {
    public static final String REST_URL = "/rest/profile/stakes";

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Stake> getAllForAuthorizedUser() {
        return super.getAllForAuthorizedUser();
    }

    @Override
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Stake get(@PathVariable("id") final int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") final int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public void update(@RequestBody final Stake stake, @PathVariable("id") final int id) {
        super.update(stake, id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Stake> createWithLocation(@Valid @RequestBody final Stake stake) {
        Stake created = super.create(stake);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "/between", produces = APPLICATION_JSON_VALUE)
    public List<Stake> getBetween(
            @RequestParam("startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime start,
            @RequestParam("endDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime end,
            @RequestParam("option") final String option) {
        return super.getBetween(start.toLocalDate(), start.toLocalTime(), end.toLocalDate(), end.toLocalTime(), option);
    }
}
