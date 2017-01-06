package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.model.Stake;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(UserStakeRestController.REST_URL)
public class UserStakeRestController extends AbstractStakeController {

    static final String REST_URL = "/rest/profile/stakes";
    private static final String JSON_VALUE = MediaType.APPLICATION_JSON_VALUE;

    @Override
    @GetMapping(produces = JSON_VALUE)
    public List<Stake> getAllForAuthorizedUser() {
        return super.getAllForAuthorizedUser();
    }

    @Override
    @GetMapping(value = "/{id}", produces = JSON_VALUE)
    public Stake get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id")int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = JSON_VALUE)
    public void update(@RequestBody Stake stake, @PathVariable("id") int id) {
        super.update(stake, id);
    }

    @PostMapping(consumes = JSON_VALUE, produces = JSON_VALUE)
    public ResponseEntity<Stake> createWithLocation(@Valid @RequestBody Stake stake) {
        Stake created = super.create(stake);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "/between", produces = JSON_VALUE)
    public List<Stake> getBetween(
            @RequestParam("startDateTime")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("endDateTime")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam("option") String option) {
        return super.getBetween(start.toLocalDate(), start.toLocalTime(), end.toLocalDate(), end.toLocalTime(), option);
    }
}
