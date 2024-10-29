package com.example.lektion7.controller;


import com.example.lektion7.authorities.UserRoles;
import com.example.lektion7.model.CustomUser;
import com.example.lektion7.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserController (UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    // TODO - check for CustomUser as an alternate approach for Model model as args
    @GetMapping("/register")
    public String registerUser (Model model) {

        model.addAttribute("customUser", new CustomUser());

        return "register";
    }

    // TODO - Security aspect for our Object, perhaps DTO?
    @PostMapping("/register")
    public String registerUser (@Valid @ModelAttribute(value = "customUser") CustomUser customUser, // Checks for constraints
                                BindingResult bindingResult,
                                Model model// Checks for error handling
    ) {
        System.out.println("post register");

        // TODO - problem: when we navigate, we lose track of our errors
        if (bindingResult.hasErrors()) {
            //model.addAttribute("customUser", customUser);
            return "register";  //Does our model get injected? || add for query params ?error=someRandomError
        }

        try {
            if (repository.findByUsername(customUser.getUsername()).isPresent()) {
                throw new Exception("du får inte, användare finns redan");
            }
            repository.save(
                    new CustomUser(
                            customUser.getUsername(),
                            encoder.encode(customUser.getPassword()),
                            UserRoles.ADMIN,
                            true,
                            true,
                            true,
                            true
                    )
            );
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
        // Username, Password



        return "redirect:/";
    }


}
