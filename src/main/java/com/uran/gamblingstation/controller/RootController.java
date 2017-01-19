package com.uran.gamblingstation.controller;

import com.uran.gamblingstation.AuthorizedUser;
import com.uran.gamblingstation.controller.user.AbstractUserController;
import com.uran.gamblingstation.dto.UserDTO;
import com.uran.gamblingstation.util.user.UserUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
public class RootController extends AbstractUserController{

    @GetMapping(value = "/")
    public String root() {
        return "redirect:/stakes";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model,
                        @RequestParam(value = "error", required = false) boolean error,
                        @RequestParam(value = "message", required = false) String message) {
        model.put("error", error);
        model.put("message", message);
        return "login";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String users() {
        return "users";
    }

    @GetMapping("/stakes")
    public String stakes() {
        return "stakes";
    }

    @GetMapping("/horses")
    public String horses() {
        return "horses";
    }

    @GetMapping("/races")
    public String races() {
        return "races";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid UserDTO userDTO, BindingResult result, SessionStatus status) {
        if (!result.hasErrors()) {
            try {
                userDTO.setId(AuthorizedUser.id());
                super.update(userDTO);
                AuthorizedUser.get().update(userDTO);
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
        model.addAttribute("userTo", new UserDTO());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserDTO userDTO, BindingResult result, SessionStatus status, ModelMap model) {
        if (!result.hasErrors()) {
            try {
                super.create(UserUtil.createNewFromTo(userDTO));
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
