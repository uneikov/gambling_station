package com.uran.gamblingstation.controller;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.service.HorseService;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.service.UserService;
import com.uran.gamblingstation.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Controller
public class RootController {
    private static final Logger LOG = LoggerFactory.getLogger(RootController.class);

    @Autowired private UserService userService;
    @Autowired private StakeService stakeService;
    @Autowired private HorseService horseService;
    @Autowired private WalletService walletService;

    @GetMapping(value = "/")
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:stakes";
    }

    @RequestMapping(value = "/stakes", method = RequestMethod.GET)
    public String stakes(Model model) {
        int userId = AuthorizedUser.id();
        model.addAttribute("stakes", stakeService.getAllByUserId(userId));
        model.addAttribute("availableValue", walletService.get(userId).getCash());
        model.addAttribute("horsesNames", horseService.getAll().stream().map(Horse::getName).collect(Collectors.toList()));
        return "stakes";
    }
}
