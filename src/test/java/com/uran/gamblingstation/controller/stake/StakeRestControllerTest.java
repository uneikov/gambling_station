package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.TestUtil;
import com.uran.gamblingstation.controller.AbstractControllerTest;
import com.uran.gamblingstation.controller.json.JsonUtil;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.service.StakeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collections;

import static com.uran.gamblingstation.StakeTestData.*;
import static com.uran.gamblingstation.TestUtil.userHttpBasic;
import static com.uran.gamblingstation.UserTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class StakeRestControllerTest extends AbstractControllerTest {
    private static final String JSON_VALUE = MediaType.APPLICATION_JSON_VALUE;
    private static final String STAKE_REST_URL = UserStakeRestController.REST_URL + '/';
    private static final String ADMIN_STAKE_REST_URL = StakeRestController.REST_URL + '/';

    private static final String START = "2016-06-13T16:00";
    private static final String END = "2016-06-13T20:00";

    @Autowired
    protected StakeService stakeService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(STAKE_REST_URL + STAKE_1_ID)
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(JSON_VALUE))
                .andExpect(STAKE_MATCHER.contentMatcher(STAKE_1)
                );
    }

    @Test
    public void testGetAllBelongsToUser() throws Exception {
        mockMvc.perform(get(STAKE_REST_URL)
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(JSON_VALUE))
                .andExpect(STAKE_MATCHER.contentListMatcher(Arrays.asList(STAKE_5, STAKE_3, STAKE_1))
                );
    }

    @Test
    public void testGetAllForbidden() throws Exception {
        mockMvc.perform(get(ADMIN_STAKE_REST_URL)
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(ADMIN_STAKE_REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(JSON_VALUE))
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
                .contentType(JSON_VALUE)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk()));
        STAKE_MATCHER.assertEquals(updated, stakeService.get(STAKE_1_ID, USER_ID_1));
    }

    @Test
    public void testCreate() throws Exception {
        Stake expected = getCreated();
        ResultActions action = mockMvc.perform(post(STAKE_REST_URL)
                .contentType(JSON_VALUE)
                .content(JsonUtil.writeValue(expected))
                .with(userHttpBasic(USER_1)))
                .andDo(print())
                .andExpect(status().isCreated());
        Stake returned = STAKE_MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());
        STAKE_MATCHER.assertEquals(expected, returned);
    }

    @Test
    public void testBetween() throws Exception {
        TestUtil.print(mockMvc.perform(get(
                STAKE_REST_URL + "between?startDateTime=" + START + "&endDateTime=" + END + "&option=loosed")
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(JSON_VALUE))
                .andExpect(STAKE_MATCHER.contentListMatcher(Collections.singletonList(STAKE_3))));
    }
}
