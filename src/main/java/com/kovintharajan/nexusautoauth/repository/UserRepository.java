package com.kovintharajan.nexusautoauth.repository;

import com.kovintharajan.nexusautoauth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
