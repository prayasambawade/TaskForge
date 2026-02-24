package com.TaskForge.taskForge.Security;

import com.TaskForge.taskForge.Model.User;
import com.TaskForge.taskForge.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Collections;

@org.springframework.stereotype.Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User u = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + email));

        return new org.springframework.security.core.userdetails.User(
                u.getEmail(),         // or u.getUsername() if you have it
                u.getPassword(),
                Collections.emptyList()
        );
    }
}