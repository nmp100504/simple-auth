package com.springmvcapp.service;

import com.springmvcapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byLogin = userService.findByUsername(username);
        if (byLogin == null) {
            return null;
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username((byLogin.getUsername()))
                .password(byLogin.getPassword())
                .roles(byLogin.getRole().name())
                .build();
//        return User.builder()
//                .username(byLogin.getUsername())
//                .password(byLogin.getPassword())
//                .roles(byLogin.getRole().name())
//                .build();
    }
}
