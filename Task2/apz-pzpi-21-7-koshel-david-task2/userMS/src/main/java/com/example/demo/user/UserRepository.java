package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserCredential,Long> {

    List<UserCredential> findAllBySaloonId(Long id);
    UserCredential findByEmail(String email);
}
