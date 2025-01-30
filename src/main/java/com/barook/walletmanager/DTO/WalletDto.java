package com.barook.walletmanager.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record WalletDto(

        @NotNull(message = "The user id should not be null")
        long userId,

        @NotNull
        @PositiveOrZero(message = "The balance should not be negative")
        BigDecimal balance

) {
}
