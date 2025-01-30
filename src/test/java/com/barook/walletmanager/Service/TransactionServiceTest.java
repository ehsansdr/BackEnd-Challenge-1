package com.barook.walletmanager.Service;

import com.barook.walletmanager.CustomeException.WalletTransactionException;
import com.barook.walletmanager.DTO.TransactionDto;
import com.barook.walletmanager.DTO.WalletDto;
import com.barook.walletmanager.Entity.Transaction;
import com.barook.walletmanager.Entity.User;
import com.barook.walletmanager.Entity.Wallet;
import com.barook.walletmanager.Repository.TransactionRepository;
import com.barook.walletmanager.Repository.WalletRepository;
import com.barook.walletmanager.ResponceDTO.TransactionResDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private WalletRepository walletRepository;

    private Transaction transaction;
    private TransactionDto transactionDto;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Mock the walletRepository to return mocked wallets when findById is called
        Wallet fromWallet = new Wallet();
        fromWallet.setId(4L);
        fromWallet.setBalance(BigDecimal.valueOf(1000));  // Set initial balance or any other properties

        Wallet toWallet = new Wallet();
        toWallet.setId(1L);
        toWallet.setBalance(BigDecimal.valueOf(500)); // Set initial balance or any other properties

        // Mock walletRepository.findById() to return the wallets
        when(walletRepository.findById(4L)).thenReturn(Optional.of(fromWallet));
        when(walletRepository.findById(1L)).thenReturn(Optional.of(toWallet));

        this.transactionDto = new TransactionDto(
                4L,
                1L,
                1000L
        );

        // Now create the transaction object using the mocked wallets
        this.transaction = Transaction.builder()
                .fromWalletId(fromWallet) // Assign the mocked from wallet
                .toWalletId(toWallet)     // Assign the mocked to wallet
                .amount(new BigDecimal(1000)) // Set the amount for the transaction
                .build();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveTransaction() {
        // Mock the transactionRepository to return the transaction when save is called
        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(transaction);

        // Call the service method to save the transaction
        TransactionResDto transactionResDto = transactionService.saveTransaction(transactionDto);

        // Verify that the save method is called on the repository
        verify(transactionRepository, times(1)).save(any(Transaction.class));

        // Additional assertions can be added here, e.g., to verify the content of the result
    }

    @Test
    void saveTransaction_shouldThrowException_whenWalletIsNull() {
        // Test case for when wallets are not found in repository
        when(walletRepository.findById(4L)).thenReturn(Optional.empty()); // Simulate wallet not found

        assertThrows(WalletTransactionException.class, () -> transactionService.saveTransaction(transactionDto));
    }
}
