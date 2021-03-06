package com.uran.gamblingstation.controller.race;

import com.uran.gamblingstation.controller.AbstractControllerTest;
import com.uran.gamblingstation.model.Race;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collections;

import static com.uran.gamblingstation.RaceTestData.*;
import static com.uran.gamblingstation.StakeTestData.*;
import static com.uran.gamblingstation.TestUtil.userHttpBasic;
import static com.uran.gamblingstation.UserTestData.ADMIN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RaceRestControllerTest extends AbstractControllerTest {
    private static final String RACE_REST_URL = RaceRestController.REST_URL;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(RACE_REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(RACE_MATCHER.contentListMatcher(Arrays.asList(RACE_4, RACE_3, RACE_2, RACE_1)));
    }

    @Test
    public void testGetWithStakes() throws Exception {
        ResultActions actions = mockMvc.perform(get(RACE_REST_URL + "/with")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE));
        STAKE_MATCHER.assertCollectionEquals(
                RACE_MATCHER.fromJsonActions(actions).stream().map(Race::getStakes).findFirst().orElse(Collections.emptyList()),
                Arrays.asList(STAKE_4, STAKE_5)
        );
    }

}