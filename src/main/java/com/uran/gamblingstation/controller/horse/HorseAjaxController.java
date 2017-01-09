package com.uran.gamblingstation.controller.horse;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.to.HorseTo;
import com.uran.gamblingstation.util.horse.HorseUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/ajax/admin/horses")
public class HorseAjaxController extends AbstractHorseController {

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Horse> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Horse get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@Valid HorseTo horseTo) {
        if (horseTo.isNew()) {
            super.create(HorseUtil.createNewFromTo(horseTo));
        } else {
            super.update(horseTo);
        }
    }

    @PostMapping(value = "/{id}")
    public void enableOrDisable(@PathVariable("id") int id) {
        Horse horse = super.get(id);
        horse.setReady(!horse.isReady());
        super.update(horse, id);
    }
}
