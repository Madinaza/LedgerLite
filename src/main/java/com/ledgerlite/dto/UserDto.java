package com.ledgerlite.dto;

import jakarta.validation.constraints.*;
import java.util.Set;

public record UserDto(
        @NotBlank String username,
        @NotBlank String password,
        Set<@NotBlank String> roles
) {}