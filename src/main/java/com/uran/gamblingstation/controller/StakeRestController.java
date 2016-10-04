package com.uran.gamblingstation.controller;

import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.StakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StakeRestController {

    @Autowired
    private StakeService service;

    List<Stake> getAll(){
        return  service.getAll();
    }

    Double getAllCash(){
        return service.getAllCash();
    }

    List<Stake> getWinningStakes(){
        return  service.getWinningStakes();
    }

    boolean save(Stake stake, int userId){
        return  service.save(stake, userId);
    }
    boolean update(Stake stake, int userId){
        return  service.update(stake, userId);
    }

    void setWinningStakes(Horse horse, int userId){
        service.setWinningStakes(horse, userId);
    }

    void delete(int id){
        service.delete(id);
    }

    Stake get(int id){
        return service.get(id);
    }
}
