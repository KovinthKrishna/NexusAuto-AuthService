package com.kovintharajan.nexusautoauth.controller;

import com.kovintharajan.nexusautoauth.dto.RegisterRequest;
import com.kovintharajan.nexusautoauth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final AuthService authService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createEmployee(@RequestBody RegisterRequest request) {
        authService.createEmployee(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Employee account created successfully.");
    }
}
