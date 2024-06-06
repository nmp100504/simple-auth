package com.springmvcapp.service;

import com.springmvcapp.dto.UserCreationRequest;
import com.springmvcapp.mapper.UserMapper;
import com.springmvcapp.model.*;
import com.springmvcapp.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @PostConstruct
    public void postConstruct() {
        if (userRepository.findByUsername("admin") == null) {
            User adminUser = new User();
            adminUser.setRole(Role.ADMIN);
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("abc"));
            userRepository.save(adminUser);
        }
    }

    public void register(UserCreationRequest request) {
        if (userRepository.findByUsername(request.getUsername()) == null) {


            User newUser = new User();
            newUser.setUsername(request.getUsername());
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));
            newUser.setRole(Role.USER);
            newUser.setPosts(new HashSet<>());
            newUser.setComments(new HashSet<>());
            newUser.setOrders(new HashSet<>());

            userRepository.save(newUser);
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
