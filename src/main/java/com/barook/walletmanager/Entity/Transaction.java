package com.barook.walletmanager.Entity;

import com.barook.walletmanager.Enum.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fromWalletId")
    private Wallet fromWalletId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toWalletId")
    private Wallet toWalletId;

    @Column(precision = 19, scale = 4) // Important for currency!
    private BigDecimal amount;

    @Enumerated(EnumType.STRING) // Store as string for readability
    private TransactionType type; // Add a transaction type (DEPOSIT, WITHDRAWAL, TRANSFER)

    private LocalDateTime transactionDate; // Timestamp of the transaction

    @Column(unique = true) // Enforce uniqueness in the database
    // create the key in db
    private String referenceId; // Optional: A unique transaction reference (UUID)

//    public Transaction(Wallet fromWallet, Wallet toWallet, BigDecimal amount, TransactionType type) {
//        this.fromWalletId = fromWallet;
//        this.toWalletId = toWallet;
//        this.amount = amount;
//        this.type = type;
//        this.transactionDate = LocalDateTime.now();
//        this.referenceId = UUID.randomUUID().toString(); // Generate and set the UUID
//    }

    // Add a pre-persist method to automatically set the timestamp
    @PrePersist
    protected void onCreate() {
        this.referenceId = UUID.randomUUID().toString();
        transactionDate = LocalDateTime.now();
    }
}
