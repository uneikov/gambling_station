package com.uran.gamblingstation.controller.race;

import com.uran.gamblingstation.controller.AbstractControllerTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static com.uran.gamblingstation.RaceTestData.*;
import static com.uran.gamblingstation.TestUtil.userHttpBasic;
import static com.uran.gamblingstation.UserTestData.ADMIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RaceRestControllerTest extends AbstractControllerTest{
    private static final String CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;
    private static final String RACE_REST_URL = RaceRestController.REST_URL + '/';

    @Test
    public void getWithStakes() throws Exception {
        mockMvc.perform(get(RACE_REST_URL +"/with")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(CONTENT_TYPE))
                .andExpect(RACE_MATCHER.contentListMatcher(Arrays.asList(RACE_4,RACE_3, RACE_2, RACE_1)));
    }

}