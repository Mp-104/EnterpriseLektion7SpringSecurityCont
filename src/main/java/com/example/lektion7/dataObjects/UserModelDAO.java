package com.example.lektion7.dataObjects;

import com.example.lektion7.model.CustomUser;
import com.example.lektion7.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class UserModelDAO implements IUserModelDAO {

    private final UserRepository userRepository;

    @Autowired
    public UserModelDAO (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUser findUser(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    public Collection<CustomUser> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void save(CustomUser customUser) {
        userRepository.save(customUser);
    }
}
