package com.uran.gamblingstation.controller.horse;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.service.HorseService;
import com.uran.gamblingstation.dto.HorseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AbstractHorseController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractHorseController.class);

    @Autowired
    private HorseService service;

    public Horse get(final int id) {
        LOG.info("get horse {}", id);
        return service.get(id);
    }

    public void delete(final int id) {
        LOG.info("delete horse {}", id);
        service.delete(id);
    }

    public List<Horse> getAll() {
        LOG.info("getAll horses");
        return service.getAll();
    }

    public void update(final Horse horse, final int id) {
        horse.setId(id);
        LOG.info("update horse {}", horse);
        service.update(horse);
    }

    public void update(final HorseDTO horseDTO) {
        LOG.info("update horse from horseDTO {}", horseDTO);
        service.update(horseDTO);
    }

    public Horse create(final Horse horse) {
        horse.setId(null);
        LOG.info("create horse {}", horse);
        return service.save(horse);
    }

}
