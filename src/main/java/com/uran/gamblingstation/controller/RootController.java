package com.uran.gamblingstation.controller;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.controller.user.AbstractUserController;
import com.uran.gamblingstation.service.RaceRowService;
import com.uran.gamblingstation.to.UserTo;
import com.uran.gamblingstation.util.user.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
public class RootController extends AbstractUserController{
    private static final Logger LOG = LoggerFactory.getLogger(RootController.class);

    @Autowired private RaceRowService raceRowService;

    @GetMapping(value = "/")
    public String root() {
        return "redirect:/stakes";
        //return "index";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String users() {
        return "users";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model,
                        @RequestParam(value = "error", required = false) boolean error,
                        @RequestParam(value = "message", required = false) String message) {
        model.put("error", error);
        model.put("message", message);
        return "login";
    }
    /*@RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:stakes";
    }*/

    @GetMapping("/stakes")
    public String stakes() {
        return "stakes";
    }

    @GetMapping("/horses")
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

    @GetMapping("/races")
    public String races(Model model) {
        model.addAttribute("raceRows", raceRowService.getRows());
        return "races";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status) {
        if (!result.hasErrors()) {
            try {
                userTo.setId(AuthorizedUser.id());
                super.update(userTo);
                AuthorizedUser.get().update(userTo);
                status.setComplete();
                return "redirect:stakes";
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("email", "exception.duplicate_email");
            }
        }
        return "profile";
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (!result.hasErrors()) {
            try {
                super.create(UserUtil.createNewFromTo(userTo));
                status.setComplete();
                return "redirect:login?message=app.registered";
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("email", "exception.duplicate_email");
            }
        }
        model.addAttribute("register", true);
        return "profile";
    }
}
