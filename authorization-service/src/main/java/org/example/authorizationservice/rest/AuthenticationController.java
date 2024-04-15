package org.example.authorizationservice.rest;


import lombok.RequiredArgsConstructor;
import org.example.authorizationservice.dto.AuthenticationRequest;
import org.example.authorizationservice.dto.RegisterRequest;
import org.example.authorizationservice.service.AuthenticationService;
import org.example.authorizationservice.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/validate")
    public boolean validate(@RequestParam String token){
        return !jwtService.isTokenExpired(token);
    }

}
