package com.kovintharajan.nexusautoauth.service;

import com.kovintharajan.nexusautoauth.dto.UpdateProfileRequest;
import com.kovintharajan.nexusautoauth.dto.UserResponse;
import com.kovintharajan.nexusautoauth.exception.InvalidOperationException;
import com.kovintharajan.nexusautoauth.exception.UserNotFoundException;
import com.kovintharajan.nexusautoauth.model.Role;
import com.kovintharajan.nexusautoauth.model.User;
import com.kovintharajan.nexusautoauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse getMyProfile() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new UserResponse(currentUser);
    }

    public UserResponse updateMyProfile(UpdateProfileRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        currentUser.setFirstName(request.getFirstName());
        currentUser.setLastName(request.getLastName());

        return new UserResponse(userRepository.save(currentUser));
    }

    public List<UserResponse> getAllEmployees() {
        return userRepository.findAllByRole(Role.ROLE_EMPLOYEE)
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public String toggleEmployeeStatus(Long employeeId) {
        User employee = userRepository.findById(employeeId)
                .orElseThrow(() -> new UserNotFoundException("Employee not found with id: " + employeeId + "."));

        if (employee.getRole() != Role.ROLE_EMPLOYEE) {
            throw new InvalidOperationException("Can only change status of users with ROLE_EMPLOYEE.");
        }

        employee.setEnabled(!employee.isEnabled());
        userRepository.save(employee);

        return "Employee with id: " + employeeId + " successfully " + (employee.isEnabled() ? "enabled." : "disabled.");
    }
}
