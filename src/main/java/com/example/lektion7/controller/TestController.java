package com.example.lektion7.controller;

import com.example.lektion7.authorities.UserRoles;
import com.example.lektion7.config.AppPasswordConfig;
import com.example.lektion7.model.CustomUser;
import com.example.lektion7.model.CustomUserDTO;
import com.example.lektion7.model.RCustomUserDTO;
import com.example.lektion7.model.Task;
import com.example.lektion7.repository.TaskRepository;
import com.example.lektion7.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
public class TestController {

    private final AppPasswordConfig passwordConfig;

    private final UserRepository repository;

    private final PasswordEncoder encoder;  //Interface

    private final TaskRepository taskRepository;


    public TestController(AppPasswordConfig passwordConfig, UserRepository repository, PasswordEncoder encoder, TaskRepository taskRepository) {
        this.passwordConfig = passwordConfig;
        this.repository = repository;
        this.encoder = encoder;
        this.taskRepository = taskRepository;
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

    //Using interface instead, now finds out @Bean from AppPasswordConfig class
    @GetMapping("/api/hash2")
    public String testHash2 () {

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

        if (repository.findByUsername(name).isPresent()){
            return repository.findByUsername(name).get();
        }
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

    @PostMapping("/api/users")
    public ResponseEntity<CustomUser> testValidation (@Valid @RequestBody CustomUser user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping("/api/current-username")
    public ResponseEntity<String> showUsername () {

        Optional<CustomUser> user = repository.findByUsername("Test");

        if (user.isPresent()) {

            RCustomUserDTO customUserDTO = new RCustomUserDTO(user.get());

            RCustomUserDTO userDTO = new RCustomUserDTO(user.get().getUsername());

            userDTO.username();

            return ResponseEntity.status(200).body(customUserDTO.username());
        }

        return ResponseEntity.status(404).build();

    }

    // Using postman - use two tabs, one for logging in and one for posting task, both POST requests
    // x-form-www-urlencoded in body to set log in details, username, password, remember-me with valid values
    // in /api/task use body -> raw -> JSON to set task values
    @PostMapping("/api/task")
    public ResponseEntity<Task> createTask (
            @Valid @RequestBody Task task,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        // TODO - can be implemented in Service class
        // Currently logged in user
        String username = authentication.getName();

        // Null check user in db ( database )
        Optional<CustomUser> currentUser = repository.findByUsername(username);

        System.out.println("-------DEBUG-------");
        //System.out.println(currentUser.get());
        System.out.println(username);
        System.out.println(authentication.getPrincipal().toString());
        System.out.println("-------DEBUG------");

        // Check if user is not null
        if (currentUser.isEmpty()) {

            //task.setCustomUser(currentUser.get()); // Cast Optional<CustomUser> -> CustomUser
            return ResponseEntity.notFound().build();
        }

        // Connect Task -> User
        task.setCustomUser(currentUser.get());

        return ResponseEntity.status(201).body(taskRepository.save(task));
    }

}
