package com.gramant.starterdemo.auth;

import com.gramant.auth.adapters.rest.request.UserRegistrationRequest;
import com.gramant.auth.app.ManageUser;
import com.gramant.auth.app.Notifier;
import com.gramant.auth.domain.event.UserCreatedEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthEventsTests {

    @Autowired
    private ManageUser manageUser;

    @Autowired
    private TestConfig.Flagger flagger;

    @MockBean
    private Notifier notifier;

    @Test
    public void givenListenerSetup_whenCreatingUser_thenEventFired() {
        assertFalse(flagger.flagged());
        manageUser.add(new UserRegistrationRequest("test@mail.ru", "test-user", "password", new HashMap<>()));
        assertTrue(flagger.flagged());
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        CustomEventListener customEventListener(Flagger flagger) {
            return new CustomEventListener(flagger);
        }

        static class CustomEventListener {
            private Flagger flagger;
            CustomEventListener(Flagger flagger) {
                this.flagger = flagger;
            }

            @EventListener
            // @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) // to link to source transaction
            // @Async // for async listener
            public void processUserCreatedEventSynchronously(UserCreatedEvent event) {
                this.flagger.flag();
            }
        }

        @Bean
        Flagger flagger() {
            return new Flagger();
        }

        static class Flagger {
            private boolean flag;
            Flagger() {
                this.flag = false;
            }

            void flag() {
                this.flag = true;
            }

            boolean flagged() {
                return this.flag;
            }
        }
    }
}
