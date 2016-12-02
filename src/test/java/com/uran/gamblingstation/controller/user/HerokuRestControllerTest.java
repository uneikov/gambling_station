package com.uran.gamblingstation.controller.user;

import com.uran.gamblingstation.controller.AbstractControllerTest;
import com.uran.gamblingstation.controller.json.JsonUtil;
import org.junit.Test;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static com.uran.gamblingstation.Profiles.DB_IMPLEMENTATION;
import static com.uran.gamblingstation.Profiles.HEROKU;
import static com.uran.gamblingstation.TestUtil.userHttpBasic;
import static com.uran.gamblingstation.UserTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({HEROKU, DB_IMPLEMENTATION})
public class HerokuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + '/';

    // Set DATABASE_URL environment for heroku profile
    static {
        Resource resource = new ClassPathResource("database/postgres.properties");
        try {
            PropertySource propertySource = new ResourcePropertySource(resource);
            String herokuDbUrl = String.format("postgres://%s:%s@%s",
                    propertySource.getProperty("database.username"),
                    propertySource.getProperty("database.password"),
                    ((String) propertySource.getProperty("database.url")).substring(18));
            System.out.println(herokuDbUrl);

            System.setProperty("DATABASE_URL", herokuDbUrl);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER_ID_1)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testUpdate() throws Exception {
        mockMvc.perform(put(REST_URL + USER_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(USER_1)))
                .andExpect(status().is5xxServerError());
    }
}