package com.kovintharajan.nexusautoauth.service;

import com.kovintharajan.nexusautoauth.dto.AuthResponse;
import com.kovintharajan.nexusautoauth.dto.LoginRequest;
import com.kovintharajan.nexusautoauth.dto.RegisterRequest;
import com.kovintharajan.nexusautoauth.exception.EmailAlreadyExistsException;
import com.kovintharajan.nexusautoauth.model.Role;
import com.kovintharajan.nexusautoauth.model.User;
import com.kovintharajan.nexusautoauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        createUser(request, Role.ROLE_CUSTOMER);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(request.getEmail());
        loginRequest.setPassword(request.getPassword());
        return login(loginRequest);
    }

    public void createEmployee(RegisterRequest request) {
        createUser(request, Role.ROLE_EMPLOYEE);
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = (User) authentication.getPrincipal();
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    private void createUser(RegisterRequest request, Role role) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already in use.");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        userRepository.save(user);
    }
}
