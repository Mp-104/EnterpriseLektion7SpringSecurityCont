package com.example.lektion7.config;


import com.example.lektion7.authorities.UserRoles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Override Filter CHain
        // localhost:8080/ <-- Index is now available for EVERYONE
        // But - what's happening with /login?
        // TODO - Question - Why doesn't ("/login").permitAll() <-- work?
        // TODO - Question - FormLogin.html, where is /login?
        // TODO - Question - Do you want this class in .gitignore?

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "login", "/api/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/**").permitAll()
                        .requestMatchers("/admin").hasRole(UserRoles.ADMIN.name())
                        .requestMatchers("/user").hasRole(UserRoles.USER.name())
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults());

        return http.build();
    }

    // DEBUG USER -
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User
                .withDefaultPasswordEncoder()
                .username("benny")
                .password("123")
                .authorities(UserRoles.USER.getAuthorities()) // ROLE + Permissions
                .build();

        return new InMemoryUserDetailsManager(user);
    }

}