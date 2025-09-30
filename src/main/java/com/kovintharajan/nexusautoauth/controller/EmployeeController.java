package com.kovintharajan.nexusautoauth.controller;

import com.kovintharajan.nexusautoauth.dto.RegisterRequest;
import com.kovintharajan.nexusautoauth.dto.UserResponse;
import com.kovintharajan.nexusautoauth.service.AuthService;
import com.kovintharajan.nexusautoauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createEmployee(@RequestBody RegisterRequest request) {
        authService.createEmployee(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Employee account created successfully.");
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllEmployees() {
        return ResponseEntity.ok(userService.getAllEmployees());
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> toggleEmployeeStatus(@PathVariable Long id) {
        return ResponseEntity.ok(userService.toggleEmployeeStatus(id));
    }
}
