package com.uran.gamblingstation.controller.resource;

import com.uran.gamblingstation.controller.AbstractControllerTest;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourceControllerTest extends AbstractControllerTest {
    private static final String RESOURCES_URL = "/resources/css/style.css";

    @Test
    public void testResourceCSS() throws Exception{
        mockMvc.perform(get(RESOURCES_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("text/css"));
    }

}
