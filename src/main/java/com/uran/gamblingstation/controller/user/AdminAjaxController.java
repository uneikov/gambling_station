package com.uran.gamblingstation.controller.user;

import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.dto.UserDTO;
import com.uran.gamblingstation.util.user.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController {

    @Autowired
    private MessageSource messageSource;

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") final int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@Valid final UserDTO userDTO) {
        try {
            if (userDTO.isNew()) {
                super.create(UserUtil.createNewFromTo(userDTO));
            } else {
                super.update(userDTO);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(messageSource.getMessage("exception.duplicate_email", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    @PostMapping(value = "/{id}")
    public void enable(@PathVariable("id") final int id, @RequestParam("enabled") final boolean enabled) {
        super.enable(id, enabled);
    }
}
