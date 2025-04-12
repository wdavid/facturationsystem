package com.facturation.system.facturationsystem.repositories;

import com.facturation.system.facturationsystem.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String name);
    boolean existsByUsername(String username);
}

