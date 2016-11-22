package com.uran.gamblingstation.controller.horse;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AbstarctHorseController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstarctHorseController.class);

    @Autowired
    private HorseService service;

    public Horse get(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("get horse {} for User {}", id, userId);
        return service.get(id);
    }

    public void delete(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("delete horse {} for User {}", id, userId);
        service.delete(id);
    }

    public List<Horse> getAll() {
        int userId = AuthorizedUser.id();
        LOG.info("getAll horses for User {}", userId);
        return service.getAll();
    }

    public void update(Horse horse, int id) {
        horse.setId(id);
        int userId = AuthorizedUser.id();
        LOG.info("update {} for User {}", horse, userId);
        service.update(horse);
    }
    public Horse create(Horse horse) {
        horse.setId(null);
        int userId = AuthorizedUser.id();
        LOG.info("create {} for User {}", horse, userId);
        return service.save(horse);
    }
}
