package com.ledgerlite.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public record TransactionDto(
        @NotNull LocalDate date,
        @NotBlank String narration,
        @Size(min = 2) List<@Valid EntryDto> entries
) {}