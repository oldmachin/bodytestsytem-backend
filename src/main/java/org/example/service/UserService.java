package org.example.service;

import jakarta.transaction.Transactional;
import org.example.model.Notification;
import org.example.model.user.Role;
import org.example.model.user.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User register(String username, String password, String roleName) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username has already existed.");
        }
        if (roleName == null || roleName.isEmpty()) {
            throw new RuntimeException("Invalid roleName argument.");
        }
        User newUser = new User(username, passwordEncoder.encode(password), Role.valueOf(roleName));
        userRepository.save(newUser);
        return newUser;
    }

    @NonNull
    public UserDetails login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Password is incorrect.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByUserId(long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public void resetPassword(long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User doesn't exist."));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional
    public List<Long> getRecipientIdByType(Notification.TargetUserType type) {
        List<User> targetUsers;
        if (type.equals(Notification.TargetUserType.ALL)) {
            targetUsers = userRepository.findAll();
        } else if (type.equals(Notification.TargetUserType.TEACHER)) {
            targetUsers = userRepository.findByRoleContains(Role.TEACHER);
        } else {
            targetUsers = userRepository.findByRoleContains(Role.STUDENT);
        }
        List<Long> targetUsersId = new ArrayList<>();
        for (User user : targetUsers) {
            targetUsersId.add(user.getId());
        }
        return targetUsersId;
    }
}
