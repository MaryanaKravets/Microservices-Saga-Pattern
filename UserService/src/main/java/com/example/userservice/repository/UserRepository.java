package com.example.userservice.repository;

import com.example.userservice.model.User;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Timed("findByEmailAndEncryptedPassword")
  Optional<User> findByEmailAndEncryptedPassword(String email, String password);
  @Timed("findByEmail")
  Optional<User> findByEmail(String email);
}
