package com.ledgerlite.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record EntryDto(
        @NotBlank String account,
        @NotNull BigDecimal amount
) {}