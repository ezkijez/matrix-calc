package hu.elte.matrix.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = IndexController.class)
@WebAppConfiguration()
public class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void indexPageTest() throws Exception {
        mockMvc.perform(get("/")
            .accept(MediaType.TEXT_HTML))
            .andExpect(status().isOk())
            .andExpect(view().name("index"))
            .andExpect(model().attribute("pageTitle", equalTo("Kezdőlap")));
    }
}