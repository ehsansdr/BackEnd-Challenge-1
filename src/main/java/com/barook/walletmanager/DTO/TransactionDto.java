package com.barook.walletmanager.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionDto (
        @NotNull(message = "fromWalletId must not be null")
        long fromWalletId,
        @NotNull(message = "toWalletId must not be null")
        long toWalletId,

        @Positive
        long balance

){
}
