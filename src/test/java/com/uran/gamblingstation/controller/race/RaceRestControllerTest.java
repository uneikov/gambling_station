package com.uran.gamblingstation.controller.race;

import com.uran.gamblingstation.controller.AbstractControllerTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

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
    private static final String RACE_REST_URL = RaceRestController.REST_URL;
    private static final String RACE_4_STAKES_AS_JSON =
            "{\"id\":100021,\"stakeValue\":100.25,\"dateTime\":\"2016-08-05T10:09:00\",\"wins\":false,\"amount\":0.0," +
                    "\"editable\":false,\"horse\":{\"id\":100007,\"name\":\"Thunderbird\",\"ruName\":\"Гром\",\"age\":5," +
                    "\"wins\":0,\"ready\":true},\"user\":null},{\"id\":100022,\"stakeValue\":100.25,\"dateTime\":" +
                    "\"2016-08-05T10:10:00\",\"wins\":false,\"amount\":0.0,\"editable\":false,\"horse\":{\"id\":100007," +
                    "\"name\":\"Thunderbird\",\"ruName\":\"Гром\",\"age\":5,\"wins\":0,\"ready\":true}";

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(RACE_REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(CONTENT_TYPE))
                .andExpect(RACE_MATCHER.contentListMatcher(Arrays.asList(RACE_4,RACE_3, RACE_2, RACE_1)));
    }

    @Test
    public void testGetWithStakes() throws Exception {
        final ResultActions resultActions = mockMvc.perform(get(RACE_REST_URL + "/with")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(CONTENT_TYPE));
        Assert.assertTrue(resultActions.andReturn().getResponse().getContentAsString().contains(RACE_4_STAKES_AS_JSON));
    }

}