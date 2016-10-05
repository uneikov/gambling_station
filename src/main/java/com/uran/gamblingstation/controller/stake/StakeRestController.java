package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.StakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class StakeRestController {

    @Autowired
    private StakeService service;

    public List<Stake> getAll(){
        return  service.getAll();
    }

    public Double getAllCash(){
        return service.getAllCash();
    }

    public List<Stake> getAllWinningStakes(){
        return  service.getAllWinningStakes();
    }

    public boolean save(Stake stake, int userId){
        return  service.save(stake, userId);
    }

    public boolean update(Stake stake, int userId){
        return  service.update(stake, userId);
    }

    public void setWinningStakes(Horse horse, LocalDateTime startDate, LocalDateTime endDate, int userId){
        service.setWinningStakes(horse, startDate, endDate, userId);
    }

    public void delete(int id){
        service.delete(id);
    }

    public Stake get(int id){
       /* int userId = AuthorizedUser.id();
        LOG.info("get meal {} for User {}", id, userId);*/
        return service.get(id);
    }
}
