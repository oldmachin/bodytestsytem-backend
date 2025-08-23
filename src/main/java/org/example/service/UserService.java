package org.example.service;

import org.example.model.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User register(String username, String password, String roleName);

    UserDetails login(String username, String password);

    Optional<User> findByUsername(String username);

    Optional<User> findByUserId(long userId);

    void resetPassword(long userId, String password);
}
