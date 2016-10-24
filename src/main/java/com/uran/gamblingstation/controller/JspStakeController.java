package com.uran.gamblingstation.controller;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.HorseService;
import com.uran.gamblingstation.service.UserService;
import com.uran.gamblingstation.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value="/stakes")
public class JspStakeController extends AbstractStakeController{
    @Autowired
    private HorseService horseService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request) {
        super.delete(getId(request));
        return "redirect:/stakes";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("stake", super.get(getId(request)));
        model.addAttribute("horses", horseService.getAll().stream().map(Horse::getName).collect(Collectors.toList()));
        return "stake";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("stake",
                new Stake(null, userService.get(AuthorizedUser.id), null, 0.0d , LocalDateTime.now(), false, 0.0d) );
        model.addAttribute("horses", horseService.getAll().stream().map(Horse::getName).collect(Collectors.toList()));
        return "stake";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String updateOrCreate(HttpServletRequest request) {
        String id = request.getParameter("id");
        //String horseName = request.getParameter("horse_name");
        //String userId = request.getParameter("user_id");
        //LocalDateTime ldt = LocalDateTime.parse(request.getParameter("dateTime"));
        //String stake_value = request.getParameter("stake_value");
        Stake userStake = new Stake(
                id.isEmpty() ? null : Integer.valueOf(id),
                userService.get(Integer.parseInt(request.getParameter("user_id"))),
                horseService.getByName(request.getParameter("horse_name")),
                Double.valueOf(request.getParameter("stake_value")),
                LocalDateTime.now(),
                false,
                0.0d
        );

        if (userStake.isNew()) {
            super.create(userStake);
        } else {
            super.update(userStake, userStake.getId());
        }
        return "redirect:/stakes";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String getBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
        LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
        LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
        LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
        String options = resetParam("option", request);
        model.addAttribute("stakes", super.getBetween(startDate, startTime, endDate, endTime, options));
        return "stakes";
    }

/*    @RequestMapping(value="/create", method= RequestMethod.GET)
    public String createMeal(Model model) {
        final Stake meal = new Stake(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000);
        model.addAttribute("stake", meal);
        return "stake";
    }



    @RequestMapping(value="/create_or_update", method= RequestMethod.POST)
    public String addMeal(HttpServletRequest request) {
        final Stake stake = new Stake(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories"))
        );
        if (request.getParameter("id").isEmpty()) {
            LOG.info("Create stake {}", stake);
            stakeService.create(stake);
        } else {
            LOG.info("Update meal {}", stake);
            stakeService.update(stake, getId(request));
        }
        return "redirect:/meals";
    }
*/

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }
}
