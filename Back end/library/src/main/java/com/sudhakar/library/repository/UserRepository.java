package com.sudhakar.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sudhakar.library.entity.Role;
import com.sudhakar.library.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameOrEmail(String username, String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByRole(Role role);

    Optional<User> findByUsername(String username);

}
