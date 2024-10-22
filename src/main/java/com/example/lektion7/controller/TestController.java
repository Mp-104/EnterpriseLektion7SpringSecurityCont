package com.example.lektion7.controller;

import com.example.lektion7.config.AppPasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final AppPasswordConfig passwordConfig;

    @Autowired
    public TestController(AppPasswordConfig passwordConfig) {
        this.passwordConfig = passwordConfig;
    }


    @GetMapping("/api/hash")
    public String testHash () {

        return passwordConfig.bcryptPasswordEncoder().encode("123");
    }


}
