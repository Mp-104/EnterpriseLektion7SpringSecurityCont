package com.example.lektion7.controller.rest;

import com.example.lektion7.authorities.UserRoles;
import com.example.lektion7.model.CustomUser;
import com.example.lektion7.model.CustomUserDTO;
import com.example.lektion7.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserRestController extends BaseController {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserRestController(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @GetMapping("/hello")
    public String test () {
        return "hello world";
    }

    @PostMapping("/register")
    public ResponseEntity<CustomUserDTO> registerUser (@Valid @RequestBody CustomUserDTO customUser, BindingResult result) {

        if (result.hasErrors()) {
            ResponseEntity.badRequest().build();
        }

        repository.save(new CustomUser(customUser.getUsername(),
                encoder.encode(customUser.getPassword()),
                UserRoles.ADMIN,
                true,
                true,
                true,
                true));

        System.out.println("debugging restcontroller");
        System.out.println("user register");
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(customUser);
    }
}
