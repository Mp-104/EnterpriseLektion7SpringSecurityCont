package com.example.lektion7.controller;

import com.example.lektion7.authorities.UserRoles;
import com.example.lektion7.config.AppPasswordConfig;
import com.example.lektion7.model.CustomUser;
import com.example.lektion7.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final AppPasswordConfig passwordConfig;
    private final UserRepository repository;

    @Autowired
    public TestController(AppPasswordConfig passwordConfig, UserRepository repository) {
        this.passwordConfig = passwordConfig;
        this.repository = repository;
    }


    @GetMapping("/api/hash")
    public String testHash () {

        return passwordConfig.bcryptPasswordEncoder().encode("123");
    }

    // BCryptPasswordEncoder encoder uses default class, not our bean from AppPasswordConfig
    @GetMapping("/api/hash1")
    public String testHash1 (BCryptPasswordEncoder encoder) {

        return encoder.encode("123");
    }

    // TODO - not able create users with identical credentials
    @GetMapping("/api/createuser/{name}")
    public CustomUser createUser (@PathVariable String name, BCryptPasswordEncoder encoder) { // does not use our @Bean from AppPasswordConfig

        CustomUser user = new CustomUser(
                name,
                encoder.encode("123"),
                UserRoles.USER, // TODO check if works, correctly sets authorities
                true,
                true,
                true,
                true
        );



        return repository.save(user);
    }

    @GetMapping("/api/createadmin/{name}")
    public CustomUser createAdmin (@PathVariable String name, BCryptPasswordEncoder encoder) { // does not use our @Bean from AppPasswordConfig

        CustomUser user = new CustomUser(
                name,
                encoder.encode("123"),
                UserRoles.ADMIN, // TODO check if works, correctly sets authorities
                true,
                true,
                true,
                true
        );



        return repository.save(user);
    }

}
