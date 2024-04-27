package edu.miu.cs489.eshopper.config;

import edu.miu.cs489.eshopper.service.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebApiSecurityConfig {
    private final AppUserDetailsService appUserDetailService;
    private final JWTAuthFilter jwtAuthFilter;


    private static final String[] AUTH_WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**",
            "/api/v1/**"
    };

    public WebApiSecurityConfig(AppUserDetailsService appUserDetailService,
                                JWTAuthFilter jwtAuthFilter) {
        this.appUserDetailService = appUserDetailService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        System.err.println("NOT");
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers(AUTH_WHITE_LIST).permitAll()


//                                    .requestMatchers("/api/v1/user/**").permitAll()
//                                    .requestMatchers("/api/v1/admin/**").hasAuthority("all")
//                                    .requestMatchers("/api/v1/**").hasRole("USER")
//                                    .requestMatchers("/admin/**").hasRole("ADMIN")


//                            auth.requestMatchers("/adsweb/api/v1/patient/**").permitAll()
//                                    .requestMatchers("/adsweb/api/v1/**").permitAll()
//                                    .requestMatchers("/adsweb/api/v1/patient/admin/**").hasRole("ADMIN")
//                                    .requestMatchers("/api/v1/**").hasRole("ADMIN")
//                                    .requestMatchers("/api/v1/user/**").hasRole("USER")
                            
                            ;
                        }
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(appUserDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
