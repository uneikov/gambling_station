package com.uran.gamblingstation.controller.stake;

import com.uran.gamblingstation.AbstractControllerTest;
import com.uran.gamblingstation.TestUtil;
import com.uran.gamblingstation.controller.json.JsonUtil;
import com.uran.gamblingstation.model.Stake;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

import static com.uran.gamblingstation.StakeTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StakeRestControllerTest extends AbstractControllerTest {
    private static final String contentType = MediaType.APPLICATION_JSON_VALUE;
    private static final String REST_URL = StakeRestController.REST_URL + '/';
    private static final LocalDate START_DATE = LocalDate.of(2016, 6, 13);
    private static final LocalDate END_DATE = LocalDate.of(2016, 6, 13);
    private static final LocalTime START_TIME = LocalTime.of(16, 0);
    private static final LocalTime END_TIME = LocalTime.of(20, 0);

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + STAKE_1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(contentType))
                .andExpect(STAKE_MATCHER.contentMatcher(STAKE_1));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(contentType))
                .andExpect(STAKE_MATCHER.contentListMatcher(STAKES));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + STAKE_1_ID))
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
        TestUtil.print(mockMvc.perform(put(REST_URL + STAKE_1_ID)
                .contentType(contentType)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk()));
        STAKE_MATCHER.assertEquals(updated, stakeService.get(STAKE_1_ID, STAKE_1.getUser().getId()));
    }

    @Test
    public void testCreate() throws Exception {
        Stake expected = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(contentType)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        //TestUtil.print(action);
        Stake returned = STAKE_MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());
        STAKE_MATCHER.assertEquals(expected, returned);
        STAKE_MATCHER.assertCollectionEquals(
                Arrays.asList(expected, STAKE_5, STAKE_4, STAKE_3, STAKE_2, STAKE_1),
                stakeService.getAll()
        );
    }

    @Test
    public void testBetween() throws Exception {
        TestUtil.print(mockMvc.perform(get(
                REST_URL + START_DATE + '/' + START_TIME + '/' + END_DATE + '/' + END_TIME + '/' + "loosed")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(contentType))
                .andExpect(STAKE_MATCHER.contentListMatcher(Collections.singletonList(STAKE_3))));
    }
}
