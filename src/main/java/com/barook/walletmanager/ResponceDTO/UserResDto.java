package com.barook.walletmanager.ResponceDTO;

import java.time.LocalDateTime;

public record UserResDto (
        Long id,
        long nationalId,
        String firstName,
        String lastName,
        LocalDateTime createdAt
){
}
