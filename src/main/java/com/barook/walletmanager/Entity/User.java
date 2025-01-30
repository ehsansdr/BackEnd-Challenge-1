package com.barook.walletmanager.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    @ToString.Exclude // This will ensure that wallets is not included in the toString() output, thus preventing recursion.
    private List<Wallet> wallets;


    public User(long nationalId, String firstName, String lastName, List<Wallet> wallets) {
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.wallets = wallets;
    }

    public User(long nationalId, String firstName, String lastName) {
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        Logger logger = LoggerFactory.getLogger(User.class); // Get logger within @PrePersist
        logger.info("persist user: ", this.toString());

        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
