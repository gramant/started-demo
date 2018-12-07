package com.gramant.starterdemo.notification;

import com.gramant.auth.domain.VerificationTokenRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(properties = {"auth-starter.confirm-email:true"})
public class EmailNotificationTests {

    @MockBean
    private MailSender mailSender;

    @MockBean
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Ignore
    public void _whenUserRegisters_thenEmailSent() throws Exception {
        mockMvc.perform(post("/auth/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"test\", \"email\": \"test@mail.ru\", \"password\": \"password\"}"))
                .andExpect(status().isOk());

        // fixme: RegistrationEventListener.processEmailConfirmationRequestedEvent not called
        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}
