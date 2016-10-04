package com.uran.gamblingstation.controller;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class HorseRestController {
    private static final Logger LOG = LoggerFactory.getLogger(HorseRestController.class);

    @Autowired
    private HorseService service;

   /* public Meal get(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("get meal {} for User {}", id, userId);
        return service.get(id, userId);
    }*/

    public void delete(int id, int userId) {
       /* int userId = AuthorizedUser.id();*/
       /* LOG.info("delete meal {} for User {}", id, userId);*/
        service.delete(id, userId);
    }

    public List<Horse> getAll() {
        /*int userId = AuthorizedUser.id();
        LOG.info("getAll for User {}", userId);*/
        LOG.info("getAll in HORSE CONTROLLER");
        return service.getAll();
    }

   /* public void update(Meal meal, int id) {
        meal.setId(id);
        int userId = AuthorizedUser.id();
        LOG.info("update {} for User {}", meal, userId);
        service.update(meal, userId);
    }

    public Meal create(Meal meal) {
        meal.setId(null);
        int userId = AuthorizedUser.id();
        LOG.info("create {} for User {}", meal, userId);
        return service.save(meal, userId);
    }

    public List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = AuthorizedUser.id();
        LOG.info("getBetween dates {} - {} for time {} - {} for User {}", startDate, endDate, startTime, endTime, userId);

        return MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(startDate != null ? startDate : TimeUtil.MIN_DATE, endDate != null ? endDate : TimeUtil.MAX_DATE, userId),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        );
    }*/
}
