package com.uran.gamblingstation.controller.horse;

import com.uran.gamblingstation.model.Horse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = HorseRestController.REST_URL)
public class HorseRestController extends AbstractHorseController {
    protected static final String REST_URL = "/rest/admin/horses";

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Horse> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Horse get(@PathVariable("id") final int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") final int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public void update(@RequestBody final Horse horse, @PathVariable("id") final int id) {
        super.update(horse, id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Horse> createWithLocation(@Valid @RequestBody final Horse horse) {
        Horse created = super.create(horse);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
