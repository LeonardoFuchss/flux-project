package com.leoprojects.flux.repository;

import com.leoprojects.flux.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);
}
