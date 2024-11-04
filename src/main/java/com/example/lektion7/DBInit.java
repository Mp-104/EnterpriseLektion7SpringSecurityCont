package com.example.lektion7;

import com.example.lektion7.authorities.UserRoles;
import com.example.lektion7.config.AppPasswordConfig;
import com.example.lektion7.model.CustomUser;
import com.example.lektion7.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DBInit {


    private UserRepository repository;

    private AppPasswordConfig encoder;

    @Autowired
    public DBInit (UserRepository repository, AppPasswordConfig encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @PostConstruct
    public void init ( ) {

        CustomUser user = new CustomUser();

        user.setUsername("Test");
        user.setPassword(encoder.bcryptPasswordEncoder().encode("test"));
        user.setUserRole(UserRoles.ADMIN);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialNonExpired(true);
        user.setEnabled(true);

        repository.save(user);

    }
}
