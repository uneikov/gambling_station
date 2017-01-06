package com.uran.gamblingstation.controller.horse;

import com.uran.gamblingstation.model.Horse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = HorseRaceController.REST_URL)
public class HorseRaceController extends AbstractHorseController {
    static final String REST_URL = "/rest/admin/horses";
    private static final String JSON_VALUE = MediaType.APPLICATION_JSON_VALUE;

    @Override
    @GetMapping(produces = JSON_VALUE)
    public List<Horse> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = JSON_VALUE)
    public Horse get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", produces = JSON_VALUE)
    public void update(@RequestBody Horse horse, @PathVariable int id) {
        super.update(horse, id);
    }

    @PostMapping(consumes = JSON_VALUE, produces = JSON_VALUE)
    public ResponseEntity<Horse> createWithLocation(@Valid @RequestBody Horse horse) {
        Horse created = super.create(horse);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
