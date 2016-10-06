package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.StakeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.uran.gamblingstation.model.BaseEntity.ADMIN_ID;

@Controller
public class StakeRestController {
    private static final Logger LOG = LoggerFactory.getLogger(StakeRestController.class);

    @Autowired
    private StakeService service;

    public Stake get(int id){
        int userId = ADMIN_ID;
        LOG.info("get stake {} for User {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id){
        int userId = ADMIN_ID;
        LOG.info("delete stake {} for User {}", id, userId);
        service.delete(id, userId);
    }

    public List<Stake> getAll(){
        int userId = ADMIN_ID;
        LOG.info("getAll stakes {} for User {}", userId);
        return  service.getAll(userId);
    }

    public void update(Stake stake, int id){
        int userId = ADMIN_ID;
        LOG.info("update {} for User {}", id, userId);
        service.update(stake, userId);
    }

    public Stake create(Stake stake){
        int userId = ADMIN_ID;
        LOG.info("create {} for User {}", stake,  userId);
        return  service.save(stake, userId);
    }

   /* public Double getAllCash(){
        return service.getAllCash();
    }

    public List<Stake> getAllWinningStakes(){
        return  service.getAllWinningStakes();
    }

    public void setWinningStakes(Horse horse, LocalDateTime startDate, LocalDateTime endDate, int userId){
        service.setWinningStakes(horse, startDate, endDate, userId);
    }*/

}
