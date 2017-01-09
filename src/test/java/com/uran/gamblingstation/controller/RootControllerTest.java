package com.uran.gamblingstation.controller;

import org.junit.Test;

import static com.uran.gamblingstation.TestUtil.userAuth;
import static com.uran.gamblingstation.UserTestData.ADMIN;
import static com.uran.gamblingstation.UserTestData.USER_1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RootControllerTest extends AbstractControllerTest {

    @Test
    public void testUsers() throws Exception {
        mockMvc.perform(get("/users")
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"));
    }

    @Test
    public void testUnAuth() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void testMeals() throws Exception {
        mockMvc.perform(get("/stakes")
                .with(userAuth(USER_1)))
                .andDo(print())
                .andExpect(view().name("stakes"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/stakes.jsp"));
    }
}