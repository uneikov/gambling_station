package com.uran.gamblingstation.controller.horse;

import com.uran.gamblingstation.model.Horse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ajax/horses")
public class HorseAjaxController extends AbstarctHorseController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Horse> getAll() {
        return super.getAll();
    }

    @GetMapping(value = "/names", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllNames() {
        return getAll().stream().map(Horse::getName).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@RequestParam("id") Integer id,
                               @RequestParam("name") String name,
                               @RequestParam("ru_name") String ru_name,
                               @RequestParam("age") Integer age,
                               @RequestParam("wins") Integer wins) {

        Horse horse = new Horse(id, name, ru_name, age, wins);
        if (horse.isNew()) {
            super.create(horse);
        } else {
            super.update(horse, id);
        }
    }
}
