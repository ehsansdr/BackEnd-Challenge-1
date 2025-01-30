package com.barook.walletmanager.Repository;

import com.barook.walletmanager.Entity.Wallet;
import org.springframework.transaction.annotation.Transactional; // Important!
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    // use the field name of the entity class not the column name
    @Query(value = "select sum(w.balance) as total_balance from wallet w where w.user_id = :userId",
    nativeQuery = true)
    public List<Long> getUserTotalBallance(@Param("userId") long userId);

    @Modifying // Important for update queries
    @Transactional // Essential for data consistency
    @Query(nativeQuery = true, value = "UPDATE wallet SET balance = :amount WHERE id = :walletId")
    void updateWalletballance(@Param("walletId") Long userId,@Param("amount")
    BigDecimal amount);
}
