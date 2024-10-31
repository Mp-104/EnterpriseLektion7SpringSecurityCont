package com.example.lektion7.config;


import com.example.lektion7.authorities.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    private final CustomUserDetailService customUserDetailsService;

    private final AppPasswordConfig bcrypt;

    @Autowired
    public AppSecurityConfig(CustomUserDetailService customUserDetails, AppPasswordConfig config) {
        this.customUserDetailsService = customUserDetails;
        this.bcrypt = config;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Override Filter CHain
        // localhost:8080/ <-- Index is now available for EVERYONE
        // But - what's happening with /login?
        // TODO - Question - Why doesn't ("/login").permitAll() <-- work?
        // TODO - Question - FormLogin.html, where is /login?
        // TODO - Question - Do you want this class in .gitignore?
        // TODO - #8 Bean alternative instead of Autowired

        http
                //.csrf(AbstractHttpConfigurer::disable)  // For testing, enable in production
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "login", "/api/**", "/user/**", "/static/**", "/logout").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "user/register", "/api/**").permitAll()
                        //.requestMatchers("/admin").hasRole(UserRoles.ADMIN.name())
                        //.requestMatchers("/user").hasRole(UserRoles.USER.name())
                        //.requestMatchers("/admin").hasAuthority(UserPermission.DELETE.getPermission()) // funkar inte
                        .requestMatchers("/admin").hasAuthority(UserPermission.DELETE.getPermission())    // Funkar
                        .anyRequest().authenticated()
                )
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .loginPage("/login")
                                .permitAll()
                // TODO - implement stuff
                )
                .logout( httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("remember-me", "JSESSIONID")
                        .permitAll())

                .rememberMe(httpSecurityRememberMeConfigurer -> httpSecurityRememberMeConfigurer
                        .rememberMeParameter("remember-me")  // can be overridden
                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) // validity from days to secs
                        .key("appSecureKey")
                        .userDetailsService(customUserDetailsService)
                        );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/h2-console/**", "/user/**", /*"/register",*/ "/update/**", /*"/list/**",*/ "/update/**"/*, "/delete/**"*/);
    }

    // DEBUG USER -
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//               // .withDefaultPasswordEncoder()
//               // .passwordEncoder()
//                .username("benny")
//                .password(bcrypt.bcryptPasswordEncoder().encode("123"))
//                .authorities(UserRoles.USER.getAuthorities()) // ROLE + Permissions
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

}