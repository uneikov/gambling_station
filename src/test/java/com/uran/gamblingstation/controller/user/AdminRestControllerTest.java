package com.uran.gamblingstation.controller.user;

import com.uran.gamblingstation.AbstractControllerTest;
import com.uran.gamblingstation.TestUtil;
import com.uran.gamblingstation.controller.json.JsonUtil;
import com.uran.gamblingstation.model.Role;
import com.uran.gamblingstation.model.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collections;

import static com.uran.gamblingstation.UserTestData.*;
import static com.uran.gamblingstation.model.BaseEntity.USER_1_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
// https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentMatcher(ADMIN));
    }

    @Test
    public void testGetByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "by?email=" + USER_1.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentMatcher(USER_1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER_1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        USER_MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, STATION, USER_2), userService.getAll());
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER_1);
        updated.setName("updatedName");
        updated.setEmail("user@gamblingstation.com");
        updated.setRoles(Collections.singleton(Role.ROLE_ADMIN));
        mockMvc.perform(put(REST_URL + USER_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
        USER_MATCHER.assertEquals(updated, userService.get(USER_1_ID));
    }

    @Test
    public void testCreate() throws Exception {
        User expected = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        User returned = USER_MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        USER_MATCHER.assertEquals(expected, returned);
        USER_MATCHER.assertCollectionEquals(
                Arrays.asList(ADMIN, expected, STATION, USER_1, USER_2),
                userService.getAll()
        );
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentListMatcher(ADMIN, STATION, USER_1, USER_2)));
    }
}