package org.example.authorizationservice.service;

import lombok.RequiredArgsConstructor;
import org.example.authorizationservice.dto.AuthenticationRequest;
import org.example.authorizationservice.dto.RegisterRequest;
import org.example.authorizationservice.entity.User;
import org.example.authorizationservice.repo.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {
        if(userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("user with this username already exists!");
        }
        User user = new User(request.username(), passwordEncoder.encode(request.password()), request.email(), request.firstName(), request.lastName());

        userRepository.save(user);

        return jwtService.generateToken(user);
    }

    public String authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        User user = userRepository.findUserByUsername(request.username()).orElseThrow(() -> {
            return new RuntimeException("user not found! (from authenticate method)");
        });
        return jwtService.generateToken(user);
    }
}