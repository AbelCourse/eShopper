package edu.miu.cs489.eshopper;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@TestConfiguration
@EnableWebSecurity
public class TestSecurityConfig {

    protected void configure(HttpSecurity http) throws Exception {
        // Disable security (permit all requests)
        http.authorizeRequests().anyRequest().permitAll()
                .and().csrf().disable();
    }
}
