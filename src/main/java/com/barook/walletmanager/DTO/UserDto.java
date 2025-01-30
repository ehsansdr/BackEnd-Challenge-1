package com.barook.walletmanager.DTO;

import jakarta.validation.constraints.NotNull;

public record UserDto(
        @NotNull
        long nationalId,

        @NotNull
        String firstName,

        @NotNull
        String lastName
) {
}
