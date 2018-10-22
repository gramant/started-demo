package com.gramant.starterdemo;

import com.gramant.auth.app.RoleProvider;
import com.gramant.auth.domain.PrivilegeId;
import com.gramant.auth.domain.PrivilegedRole;
import com.gramant.auth.domain.RoleId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

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
}
