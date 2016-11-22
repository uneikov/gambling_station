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

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Horse get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @GetMapping(value = "/names", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllReadyForRaceNamesList() {
        return getAll().stream().filter(Horse::isReady).map(Horse::getName).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@RequestParam("id") Integer id,
                               @RequestParam("name") String name,
                               @RequestParam("ruName") String ru_name,
                               @RequestParam("age") Integer age,
                               @RequestParam("wins") Integer wins,
                               @RequestParam("ready") Boolean ready)
    {
        Horse horse = new Horse(
                id,
                name,
                ru_name.isEmpty() ? "Отсутствует" : ru_name,
                age,
                wins == null ? 0 : wins,
                ready != null
        );
        if (horse.isNew()) {
            super.create(horse);
        } else {
            super.update(horse, id);
        }
    }

    @PutMapping(value = "/{id}")
    public void update(@PathVariable("id") int id) {
        Horse horse = super.get(id);
        horse.setReady(!horse.isReady());
        super.update(horse, id);
    }
}
