package com.hoangvu.spring_security.auth;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hoangvu.spring_security.config.JwtService;
import com.hoangvu.spring_security.user.Role;
import com.hoangvu.spring_security.user.User;
import com.hoangvu.spring_security.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Object register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        LinkedHashMap<String, Object> userData = new LinkedHashMap<>();
        userData.put("id", user.getId());
        userData.put("name", user.getName());
        userData.put("email", user.getEmail());
        userData.put("role", user.getRole());
        userData.put("authorities", user.getAuthorities());

        LinkedHashMap<String, Object> response = new LinkedHashMap<>();
        response.put("user", userData);
        response.put("message", "Registered successfully.");

        return response;
    }

    public Object authenticate(AuthenticationRequest request) {

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        var authToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(authToken);

        var jwtToken = jwtService.generateToken(user);

        LinkedHashMap<String, Object> userData = new LinkedHashMap<>();
        userData.put("id", user.getId());
        userData.put("name", user.getName());
        userData.put("email", user.getEmail());
        userData.put("role", user.getRole());
        userData.put("authorities", user.getAuthorities());

        LinkedHashMap<String, Object> response = new LinkedHashMap<>();
        response.put("user", userData);
        response.put("accessToken", jwtToken);
        response.put("message", "Authenticated successfully.");

        return response;
    }
}
