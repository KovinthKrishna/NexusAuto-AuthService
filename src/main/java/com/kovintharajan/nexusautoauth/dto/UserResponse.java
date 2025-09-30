package com.kovintharajan.nexusautoauth.dto;

import com.kovintharajan.nexusautoauth.model.Role;
import com.kovintharajan.nexusautoauth.model.User;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

    public UserResponse(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        role = user.getRole();
    }
}
