package com.uran.gamblingstation.controller.user;

import com.uran.gamblingstation.controller.AbstractControllerTest;
import com.uran.gamblingstation.TestUtil;
import com.uran.gamblingstation.controller.json.JsonUtil;
import com.uran.gamblingstation.model.Role;
import com.uran.gamblingstation.model.User;
import com.uran.gamblingstation.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static com.uran.gamblingstation.TestUtil.userHttpBasic;
import static com.uran.gamblingstation.UserTestData.*;
import static com.uran.gamblingstation.controller.user.ProfileRestController.REST_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    protected UserService userService;
    @Test
    public void testGet() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentMatcher(USER_1)));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk());
        USER_MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, STATION, USER_2), userService.getAll());
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER_ID_1, "newName", "newemail@ya.ru", "newPassword", Role.ROLE_USER);
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(USER_1)))
                .andDo(print())
                .andExpect(status().isOk());

        USER_MATCHER.assertEquals(updated, new User(userService.getByEmail("newemail@ya.ru")));
    }
}
