package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.service.StakeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class StakeRestController {
    private static final Logger LOG = LoggerFactory.getLogger(StakeRestController.class);

    @Autowired
    private StakeService service;

    public Stake get(int id){
        int userId = AuthorizedUser.id();
        LOG.info("get stake {} for User {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id){
        int userId = AuthorizedUser.id();
        LOG.info("delete stake {} for User {}", id, userId);
        service.delete(id, userId);
    }

    public List<Stake> getAll(){
        int userId = AuthorizedUser.id();
        LOG.info("getAll stakes {} for User {}", userId);
        return service.getAll(userId);
    }

    public void update(Stake stake, int id){
        int userId = AuthorizedUser.id();
        LOG.info("update {} for User {}", id, userId);
        service.update(stake, userId);
    }

    public Stake create(Stake stake, int userId){
        //int userId = AuthorizedUser.id();
        LOG.info("create {} for User {}", stake,  userId);
        return service.save(stake, userId);
    }

    public void processWinningStakes(List<Stake> winningStakes, Map<Integer, Double> winningMap){
        service.processWinningStakes(winningStakes, winningMap);
    }

    public void setWinningStakes(int horseId, LocalDateTime startDate, LocalDateTime endDate){
        service.setWinningStakes(horseId, startDate, endDate);
    }

    public List<Stake> getWinningStakes(LocalDateTime startDate, LocalDateTime endDate){
        return service.getWinningStakes(startDate, endDate);
    }

    public List<Stake> getLoosingStakes(LocalDateTime startDate, LocalDateTime endDate){
        return service.getLoosingStakes(startDate, endDate);
    }

    public Double getAllStakesCash(){
        return service.getAllCash();
    }

    public List<User> getWinningUsers(Horse horse, LocalDateTime startDate, LocalDateTime endDate){
        return service.getWinningUsers(horse, startDate, endDate);
    }
}
