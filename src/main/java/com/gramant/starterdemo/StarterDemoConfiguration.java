package com.gramant.starterdemo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gramant.auth.adapters.rest.handlers.CreateUserHandler;
import com.gramant.auth.adapters.rest.request.UserRegistrationRequest;
import com.gramant.auth.app.ManageUser;
import com.gramant.auth.app.RoleProvider;
import com.gramant.auth.domain.PrivilegeId;
import com.gramant.auth.domain.PrivilegedRole;
import com.gramant.auth.domain.RoleId;
import com.gramant.auth.domain.User;
import com.gramant.auth.domain.event.UserCreatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@Configuration
public class StarterDemoConfiguration {

    @Bean
    public RoleProvider roleProvider() {
        return new RoleProvider.Default(
                new PrivilegedRole(new RoleId("ADMIN"), Arrays.asList(new PrivilegeId("EDIT_EMPLOYEES"), new PrivilegeId("EDIT_MANAGERS"))),
                new PrivilegedRole(new RoleId("MANAGER"), Collections.singletonList(new PrivilegeId("EDIT_EMPLOYEES"))),
                new PrivilegedRole(new RoleId("EMPLOYEE"), Collections.emptyList())
        );
    }

    @Bean CustomEventListener customEventListener() {
        return new CustomEventListener();
    }

    static class CustomEventListener {

        @EventListener
        // @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) // to link to source transaction
        // @Async // for async listener
        public void processUserCreatedEventSynchronously(UserCreatedEvent event) {
            // execute app logic processing user creation
        }
    }

    @Bean
    public CreateUserHandler createUserHandler(ManageUser manageUser) {
        return registrationRequest -> {
            Map<String, String> additionalProperties = registrationRequest.getAdditionalProperties();
            if (additionalProperties.get("specialProp") == null) {
                return ResponseEntity.badRequest().build();
            } else {
                // process additional properties
                manageUser.add(registrationRequest);
                return ResponseEntity.ok().build();
            }
        };
    }
}
