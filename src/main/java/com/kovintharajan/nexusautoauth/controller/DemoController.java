package com.kovintharajan.nexusautoauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {
    @GetMapping("/all")
    public ResponseEntity<String> sayHelloToAll() {
        return ResponseEntity.ok("Hello! This is accessible to all authenticated users.");
    }

    @GetMapping("/customers")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> sayHelloToCustomers() {
        return ResponseEntity.ok("Hello from a CUSTOMER-only endpoint!");
    }

    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> sayHelloToAdmins() {
        return ResponseEntity.ok("Hello from an ADMIN-only endpoint!");
    }
}
