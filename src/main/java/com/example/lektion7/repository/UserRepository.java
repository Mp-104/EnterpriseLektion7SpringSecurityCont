package com.example.lektion7.repository;

import com.example.lektion7.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {

    // TODO - UserDetailsService findByUsername

    Optional<CustomUser> findByUsername (String username);

}
