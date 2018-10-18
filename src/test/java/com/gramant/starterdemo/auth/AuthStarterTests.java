package com.gramant.starterdemo.auth;

import com.gramant.auth.test.WithMockCustomUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthStarterTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/test-data/testcase-auth.sql")
    @WithMockCustomUser(id = "user1", email = "user1@mail.ru")
    public void givenAuthenticated_whenGettingProfile_thenOk() throws Exception {
        mockMvc.perform(get("/api/auth/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("user1@mail.ru"));
    }

    @Test
    @Sql("/test-data/testcase-auth.sql")
    public void givenUnauthenticated_whenGettingProfile_thenNoContent() throws Exception {
        mockMvc.perform(get("/api/auth/user"))
                .andExpect(status().isNoContent());
    }
}
