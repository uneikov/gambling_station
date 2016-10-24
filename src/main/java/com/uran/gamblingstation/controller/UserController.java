package com.uran.gamblingstation.controller;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.service.StakeService;
import com.uran.gamblingstation.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
@Controller
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private StakeService stakeService;

    @GetMapping(value = "/")
    public String root() {
        return "index";
    }

    @GetMapping(value = "/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @PostMapping(value = "/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:stakes";
    }

    @GetMapping(value = "/stakes")
    public String stakes(Model model) {
        model.addAttribute("stakes", stakeService.getAllByUserId(AuthorizedUser.id()));
        return "stakes";
    }
}
