package com.tourist_spot_management.controller;

import com.tourist_spot_management.payload.JWTAuthResponse;
import com.tourist_spot_management.payload.LoginRequest;
import com.tourist_spot_management.payload.LogoutRequest;
import com.tourist_spot_management.payload.TokenValidationRequest;
import com.tourist_spot_management.security.JwtTokenProvider;
import com.tourist_spot_management.service.Impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthService authService;

    // http://localhost:8080/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(jwt));
    }

    // http://localhost:8080/api/auth/logout
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest logoutRequest) {
        jwtTokenProvider.invalidateToken(logoutRequest.getToken());
        return ResponseEntity.ok("Successfully logged out");
    }

    // http://localhost:8080/api/auth/check-token
    @PostMapping("/check-token")
    public ResponseEntity<?> checkToken(@RequestBody TokenValidationRequest tokenValidationRequest) {
        String token = tokenValidationRequest.getToken().replace("\"", ""); // Remove any quotes
        boolean isValid = jwtTokenProvider.validateToken(token);
        return ResponseEntity.ok(isValid ? "Token is valid" : "Token is invalid");
    }
}
