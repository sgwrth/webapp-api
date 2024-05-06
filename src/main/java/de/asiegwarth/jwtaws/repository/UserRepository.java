package de.asiegwarth.jwtaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.asiegwarth.jwtaws.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
