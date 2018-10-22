package com.gramant.starterdemo.auth;

import com.gramant.auth.app.QueryUser;
import com.gramant.auth.domain.User;
import com.gramant.auth.domain.UserId;
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

import static org.junit.Assert.assertEquals;
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

    @Autowired
    private QueryUser queryUser;

    @Test
    @Sql("/test-data/testcase-auth.sql")
    @WithMockCustomUser(id = "user1", email = "user1@mail.ru")
    public void givenAuthenticated_whenGettingProfile_thenOk() throws Exception {
        mockMvc.perform(get("/auth/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("user1@mail.ru"));
    }

    @Test
    @Sql("/test-data/testcase-auth.sql")
    public void givenUnauthenticated_whenGettingProfile_thenNoContent() throws Exception {
        mockMvc.perform(get("/auth/profile"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Sql("/test-data/testcase-auth.sql")
    public void givenUserAndRole_whenGetting_thenReturned() throws Exception {
        User user = queryUser.get(UserId.of("user-1"));

        assertEquals(1, user.roles().size());
        assertEquals("MANAGER", user.roles().get(0).id().asString());
        assertEquals(1, user.roles().get(0).privileges().size());
        assertEquals("EDIT_EMPLOYEES", user.roles().get(0).privileges().get(0).asString());
    }
}
