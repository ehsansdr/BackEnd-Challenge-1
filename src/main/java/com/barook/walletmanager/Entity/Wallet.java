package com.barook.walletmanager.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id"
    )
    @JsonBackReference // to avoid infinite recursion ir is neccery
    private User user;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        Logger logger = LoggerFactory.getLogger(Wallet.class); // Get logger within @PrePersist
        logger.info("persist Wallet: ", this.toString());

        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
