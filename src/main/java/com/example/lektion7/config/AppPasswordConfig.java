package com.example.lektion7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppPasswordConfig {

    @Bean
    public PasswordEncoder bcryptPasswordEncoder () {

        return new BCryptPasswordEncoder(15); // Default value = 10. Time based = higher number,
    }
}
