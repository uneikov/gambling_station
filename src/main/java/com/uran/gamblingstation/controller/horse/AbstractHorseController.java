package com.uran.gamblingstation.controller.horse;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.service.HorseService;
import com.uran.gamblingstation.to.HorseTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AbstractHorseController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractHorseController.class);

    @Autowired
    private HorseService service;

    public Horse get(int id) {
        LOG.info("get horse {}", id);
        return service.get(id);
    }

    public void delete(int id) {
        LOG.info("delete horse {}", id);
        service.delete(id);
    }

    public List<Horse> getAll() {
        LOG.info("getAll horses");
        return service.getAll();
    }

    public void update(Horse horse, int id) {
        horse.setId(id);
        LOG.info("update horse {}", horse);
        service.update(horse);
    }

    public void update(HorseTo horseTo) {
        LOG.info("update horse from horseTo {}", horseTo);
        service.update(horseTo);
    }

    public Horse create(Horse horse) {
        horse.setId(null);
        LOG.info("create horse {}", horse);
        return service.save(horse);
    }

}
