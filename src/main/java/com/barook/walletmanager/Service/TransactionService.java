package com.barook.walletmanager.Service;

import com.barook.walletmanager.DTO.TransactionDto;
import com.barook.walletmanager.Entity.Transaction;
import com.barook.walletmanager.Entity.Wallet;
import com.barook.walletmanager.Repository.TransactionRepository;
import com.barook.walletmanager.Repository.WalletRepository;
import com.barook.walletmanager.ResponceDTO.TransactionResDto;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

    Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final MessageSource messageSource;
    private TransactionRepository transactionRepository;
    private WalletRepository walletRepository;

    public TransactionService(WalletRepository walletRepository, TransactionRepository transactionRepository, MessageSource messageSource) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.messageSource = messageSource;
    }

    public TransactionResDto saveTransaction(TransactionDto transactionDto) {
        boolean isAllowedToProcessTransaction = transactionpreValidation(transactionDto);
        Transaction transaction = getTransactionFromTransactionDto(transactionDto);

        addAndSubTheWalletBallace(transactionDto);
        transactionRepository.save(transaction);


        return getTransactionToTransactionReDto(transaction);
    }

    private void addAndSubTheWalletBallace(TransactionDto transactionDto) {
        Wallet fromWallet = walletRepository.findById(transactionDto.fromWalletId()).orElse(null);
        Wallet toWallet = walletRepository.findById(transactionDto.toWalletId()).orElse(null);

        walletRepository.updateWalletballance(transactionDto.fromWalletId(),
                fromWallet.getBalance().subtract(new BigDecimal(transactionDto.balance())));

        walletRepository.updateWalletballance(transactionDto.toWalletId(),
                toWallet.getBalance().add(new BigDecimal(transactionDto.balance())));
    }

    private boolean transactionpreValidation(TransactionDto transactionDto) {

        boolean isAlowedToProcessTransaction = true;

        // if both wallet is null
        if (walletRepository.findById(transactionDto.fromWalletId()) == null &&
                walletRepository.findById(transactionDto.toWalletId()) == null
        ){
            isAlowedToProcessTransaction = false;
            throw new NullPointerException("Wallet : " + transactionDto.fromWalletId() +
                    " and " + transactionDto.toWalletId() + " not found");
        }
        // if from wallet is null
        if (walletRepository.findById(transactionDto.fromWalletId()) == null){
            isAlowedToProcessTransaction = false;
            throw new NullPointerException("Wallet : " + transactionDto.fromWalletId() + " not found");
        }
        // if to wallet is null
        if (walletRepository.findById(transactionDto.toWalletId()) == null){
            isAlowedToProcessTransaction = false;
            throw new NullPointerException("Wallet : " + transactionDto.toWalletId() + " not found");
        }
        // if from wallet does not have enough balance
        Wallet fromWallet = walletRepository.findById(transactionDto.fromWalletId()).orElse(null);
        if (fromWallet.getBalance().compareTo(BigDecimal.ZERO) < 0){
            isAlowedToProcessTransaction = false;
//            throw new ConstraintViolationException();
        }

        // if we want to send the money to the same wallet as fromWallet

        return isAlowedToProcessTransaction;
    }

    private Transaction getTransactionFromTransactionDto(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .amount(BigDecimal.valueOf(transactionDto.balance()))
                .fromWalletId(walletRepository.findById(transactionDto.fromWalletId()).orElse(null))
                .toWalletId(walletRepository.findById(transactionDto.toWalletId()).orElse(null))
                .build();
        return transaction;
    }

    private TransactionResDto getTransactionToTransactionReDto(Transaction transaction) {

        return new TransactionResDto(transaction.getReferenceId());

    }
}
