package com.sameer.zapurl.service;

import com.sameer.zapurl.dtos.LoginRequest;
import com.sameer.zapurl.models.User;
import com.sameer.zapurl.repositories.UserRepository;
import com.sameer.zapurl.security.jwt.JwtAuthenticationResponse;
import com.sameer.zapurl.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private JwtUtils jwtUtils;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public JwtAuthenticationResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new JwtAuthenticationResponse(jwtUtils.generateToken(userDetails));
    }

    @Cacheable("users")
    public User findByUsername(String name) {
        return userRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("User doesnt exist"));
    }
}
