package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.to.StakeTo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.uran.gamblingstation.AuthorizedUser.id;

@RestController
@RequestMapping("/ajax/profile/stakes")
public class StakeAjaxController extends AbstractStakeController{

    /*@Autowired
    UserService userService;*/

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Stake> getAll() {
        int userId = id();
        return super.getAllByUserId(userId);
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Stake get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

   /* @PostMapping
    public void createOrUpdate(@RequestParam("id") Integer id,
                               @RequestParam("stakeValue") Double stakeValue,
                               @RequestParam("horseName") String horseName) {

        Horse horse = super.getHorse(horseName);
        Stake stake = new Stake(id, null, horse, stakeValue, false, 0.0);
        if (stake.isNew()) {
            super.create(stake);
        } else {
            super.update(stake, id);
        }
    }*/

    @PostMapping
    public ResponseEntity<String> createOrUpdate(@Valid StakeTo stakeTo, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            result.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("<br>"));
            return new ResponseEntity<>(sb.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (stakeTo.isNew()) {
            super.create(stakeTo);
        } else {
            super.update(stakeTo);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Stake> getBetween(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "startTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "endTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            @RequestParam(value = "option", required = false) String option )
    {
        return super.getBetween(startDate, startTime, endDate, endTime, option);
    }

}
