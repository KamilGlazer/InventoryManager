package org.example.authorizationservice.repo;

import org.example.authorizationservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
    boolean existsByUsername(String username);
}
