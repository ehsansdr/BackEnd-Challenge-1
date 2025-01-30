package com.barook.walletmanager.ResponceDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WalletResDto(
        long id,
        long userId,
        BigDecimal balance,
        LocalDateTime createdAt
) {
}
