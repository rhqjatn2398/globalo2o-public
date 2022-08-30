package com.example.globalO2O.login.domain.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByLoginId(String loginId);
    Boolean existsByLoginId(String loginId);
    Boolean existsByNickname(String nickname);
    void deleteByLoginId(String loginId);
    Optional<User> findByEmail(String email);
}
