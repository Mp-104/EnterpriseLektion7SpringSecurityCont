package com.example.lektion7.controller;


import com.example.lektion7.authorities.UserRoles;
import com.example.lektion7.model.CustomUser;
import com.example.lektion7.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository repository;

    @Autowired
    public UserController (UserRepository repository) {
        this.repository = repository;
    }

    // TODO - check for CustomUser as an alternate approach for Model model as args
    @GetMapping("/register")
    public String registerUser (Model model) {

        model.addAttribute("user", new CustomUser());

        return "register";
    }

    // TODO - this does not work, permission?
    @PostMapping("/register")
    public String createUser (@Valid CustomUser customUser, // Checks for constraints
                              BindingResult bindingResult,
                              Model model// Checks for error handling
    ) {

        if (bindingResult.hasErrors()) {
            return "register";  // add for query params ?error=someRandomError
        }

        // Username, Password
        repository.save(
                new CustomUser(
                        customUser.getUsername(),
                        customUser.getPassword(),
                        UserRoles.ADMIN,
                        true,
                        true,
                        true,
                        true
                )
        );

        return "redirect:/";
    }


}
