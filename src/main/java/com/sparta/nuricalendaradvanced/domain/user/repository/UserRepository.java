package com.sparta.nuricalendaradvanced.domain.user.repository;

import com.sparta.nuricalendaradvanced.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String subject);

    Optional<User> findByUsername(String username);
}
