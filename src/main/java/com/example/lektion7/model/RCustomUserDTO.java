package com.example.lektion7.model;

import jakarta.validation.constraints.Size;

public record RCustomUserDTO(
        @Size(min = 1, max = 6)
        String username
) {
    // Custom constructor
    public RCustomUserDTO (CustomUser customUser) {
        this(customUser.getUsername());
    }

}
