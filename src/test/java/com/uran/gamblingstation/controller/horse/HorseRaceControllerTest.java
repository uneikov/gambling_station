package com.uran.gamblingstation.controller.horse;

import com.uran.gamblingstation.TestUtil;
import com.uran.gamblingstation.controller.AbstractControllerTest;
import com.uran.gamblingstation.controller.json.JsonUtil;
import com.uran.gamblingstation.model.Horse;
import com.uran.gamblingstation.service.HorseService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static com.uran.gamblingstation.HorseTestData.*;
import static com.uran.gamblingstation.TestUtil.userHttpBasic;
import static com.uran.gamblingstation.UserTestData.ADMIN;
import static com.uran.gamblingstation.UserTestData.USER_1;
import static com.uran.gamblingstation.controller.horse.HorseRestController.REST_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class HorseRaceControllerTest extends AbstractControllerTest{
    private static final String JSON_VALUE = MediaType.APPLICATION_JSON_VALUE;
    private static final String HORSE_REST_URL = REST_URL + '/';

    @Autowired
    private HorseService horseService;

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(HORSE_REST_URL + HORSE_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetForbidden() throws Exception {
        mockMvc.perform(get(HORSE_REST_URL)
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(HORSE_REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(JSON_VALUE))
                .andExpect(HORSE_MATCHER.contentListMatcher(HORSES));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(HORSE_REST_URL + HORSE_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(JSON_VALUE))
                .andExpect(HORSE_MATCHER.contentMatcher(HORSE_1)
                );
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(HORSE_REST_URL + HORSE_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());
        HORSE_MATCHER.assertCollectionEquals(
                Arrays.asList(HORSE_7, HORSE_10, HORSE_3, HORSE_8, HORSE_6, HORSE_9, HORSE_5, HORSE_4, HORSE_2),
                horseService.getAll()
        );
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(HORSE_REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void update() throws Exception {
        Horse updated = getUpdated();
        TestUtil.print(mockMvc.perform(put(HORSE_REST_URL + HORSE_1_ID)
                .contentType(JSON_VALUE)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk()));
        HORSE_MATCHER.assertEquals(updated, horseService.get(HORSE_1_ID));
    }

    @Test
    public void createWithLocation() throws Exception {
        Horse expected = getCreated();
        ResultActions action = mockMvc.perform(post(HORSE_REST_URL)
                .contentType(JSON_VALUE)
                .content(JsonUtil.writeValue(expected))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isCreated());
        Horse returned = HORSE_MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());
        HORSE_MATCHER.assertEquals(expected, returned);
    }

}