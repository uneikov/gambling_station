package com.uran.gamblingstation.controller;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {
    private static final Logger LOG = LoggerFactory.getLogger(RootController.class);

    @Autowired private UserService userService;
    @Autowired private StakeService stakeService;
    @Autowired private HorseService horseService;
    @Autowired private WalletService walletService;
    @Autowired private RaceService raceService;
    @Autowired private RaceRowService raceRowService;

    @GetMapping(value = "/")
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users() {
        return "users";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:stakes";
    }

    @RequestMapping(value = "/stakes", method = RequestMethod.GET)
    public String stakes() {
        return "stakes";
    }

    @RequestMapping(value = "/horses", method = RequestMethod.GET)
    public String horses() {
        return "horses";
    }

    /*@RequestMapping(value = "/wallet", method = RequestMethod.GET)
    public String wallet(Model model) {
        int userId = AuthorizedUser.id();
        final Wallet wallet = walletService.get(userId);
        model.addAttribute("wallet", wallet);
        return "wallet";
    }*/

    @RequestMapping(value = "/races", method = RequestMethod.GET)
    public String races(Model model) {
        model.addAttribute("raceRows", raceRowService.getRows());
        return "races";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}
