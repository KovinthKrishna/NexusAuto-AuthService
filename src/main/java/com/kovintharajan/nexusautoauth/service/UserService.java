package com.kovintharajan.nexusautoauth.service;

import com.kovintharajan.nexusautoauth.dto.UserResponse;
import com.kovintharajan.nexusautoauth.model.Role;
import com.kovintharajan.nexusautoauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> getAllEmployees() {
        return userRepository.findAllByRole(Role.ROLE_EMPLOYEE)
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }
}
