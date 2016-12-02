package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.controller.AbstractControllerTest;
import com.uran.gamblingstation.TestUtil;
import com.uran.gamblingstation.controller.json.JsonUtil;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.StakeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collections;

import static com.uran.gamblingstation.StakeTestData.*;
import static com.uran.gamblingstation.TestUtil.userHttpBasic;
import static com.uran.gamblingstation.UserTestData.ADMIN;
import static com.uran.gamblingstation.UserTestData.USER_1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StakeRestControllerTest extends AbstractControllerTest {
    private static final String CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;
    private static final String STAKE_REST_URL = StakeRestController.REST_URL + '/';

    private static final String START = "2016-06-13T16:00";
    private static final String END = "2016-06-13T20:00";

    @Autowired
    protected StakeService stakeService;

    @Test
    public void testGet() throws Exception {
        final ResultActions perform = mockMvc.perform(get(STAKE_REST_URL + STAKE_1_ID));
        mockMvc.perform(get(STAKE_REST_URL + STAKE_1_ID)
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(CONTENT_TYPE))
                .andExpect(STAKE_MATCHER.contentMatcher(STAKE_1)
                );
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(STAKE_REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(CONTENT_TYPE))
                .andExpect(STAKE_MATCHER.contentListMatcher(STAKES));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(STAKE_REST_URL + STAKE_1_ID)
                .with(userHttpBasic(USER_1)))
                .andDo(print())
                .andExpect(status().isOk());
        STAKE_MATCHER.assertCollectionEquals(
                Arrays.asList(STAKE_5, STAKE_4, STAKE_3, STAKE_2),
                stakeService.getAll()
        );
    }

    @Test
    public void testUpdate() throws Exception {
        Stake updated = getUpdated();
        TestUtil.print(mockMvc.perform(put(STAKE_REST_URL + STAKE_1_ID)
                .contentType(CONTENT_TYPE)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk()));
        STAKE_MATCHER.assertEquals(updated, stakeService.get(STAKE_1_ID));
    }

    @Test
    public void testCreate() throws Exception {
        Stake expected = getCreated();
        ResultActions action = mockMvc.perform(post(STAKE_REST_URL)
                .contentType(CONTENT_TYPE)
                .content(JsonUtil.writeValue(expected))
                .with(userHttpBasic(USER_1)))
                .andDo(print())
                .andExpect(status().isCreated());
        Stake returned = STAKE_MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());
        STAKE_MATCHER.assertEquals(expected, returned);
        /*STAKE_MATCHER.assertCollectionEquals(
                Arrays.asList(expected, STAKE_5, STAKE_4, STAKE_3, STAKE_2, STAKE_1),
                stakeService.getAll()
        );*/
    }

    @Test
    public void testBetween() throws Exception {
        TestUtil.print(mockMvc.perform(get(
                STAKE_REST_URL + "between?startDateTime=" + START + "&endDateTime=" + END + "&option=loosed")
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(CONTENT_TYPE))
                .andExpect(STAKE_MATCHER.contentListMatcher(Collections.singletonList(STAKE_3))));
    }
}