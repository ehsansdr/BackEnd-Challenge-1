package com.barook.walletmanager.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "national_id",
            nullable = false,
    unique = true)
    private long nationalId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "created_date")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude // This will ensure that wallets is not included in the toString() output, thus preventing recursion.
    private List<Wallet> wallets;


    public User(long nationalId, String firstName, String lastName, LocalDateTime createdAt, List<Wallet> wallets) {
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.wallets = wallets;
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
