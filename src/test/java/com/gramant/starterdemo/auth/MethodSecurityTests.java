package com.gramant.starterdemo.auth;

import com.gramant.auth.test.WithMockCustomUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MethodSecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockCustomUser
    public void whenUpdateUser_notBeingAdmin_thenForbidden() throws Exception {
        mockMvc.perform(put("/auth/users/deactivation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userIds\":[]}"))
                .andExpect(status().isForbidden());
    }
}
