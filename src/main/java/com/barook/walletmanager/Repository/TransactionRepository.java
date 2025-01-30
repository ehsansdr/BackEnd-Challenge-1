package com.barook.walletmanager.Repository;

import com.barook.walletmanager.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // this will find the record that create data is after the given value
    List<Transaction> findAllByTransactionDateAfter(LocalDateTime date);

    // this will find the record that create data is before the given value
    List<Transaction> findAllByTransactionDateBefore(LocalDateTime date);
}
