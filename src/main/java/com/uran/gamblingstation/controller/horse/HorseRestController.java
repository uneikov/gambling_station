package com.uran.gamblingstation.controller.horse;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.uran.gamblingstation.model.BaseEntity.ADMIN_ID;

@Controller
public class HorseRestController {
    private static final Logger LOG = LoggerFactory.getLogger(HorseRestController.class);

    @Autowired
    private HorseService service;

    public Horse get(int id) {
        int userId = ADMIN_ID;
        LOG.info("get horse {} for User {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id, int userId) {
       /* int userId = AuthorizedUser.id();*/
        LOG.info("delete horse {} for User {}", id, userId);
        service.delete(id, userId);
    }

    public List<Horse> getAll() {
        /*int userId = AuthorizedUser.id();
        LOG.info("getAll for User {}", userId);*/
        LOG.info("getAll in HORSE CONTROLLER");
        return service.getAll();
    }

    public void update(Horse horse, int id) {
        horse.setId(id);
        int userId = ADMIN_ID;
        LOG.info("update {} for User {}", horse, userId);
        service.update(horse, userId);
    }
    public Horse create(Horse horse) {
        horse.setId(null);
        /*int userId = AuthorizedUser.id();*/
        int userId = ADMIN_ID;
        LOG.info("create {} for User {}", horse, userId);
        return service.save(horse, userId);
    }
}
