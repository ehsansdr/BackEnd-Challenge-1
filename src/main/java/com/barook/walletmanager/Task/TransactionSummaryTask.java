package com.barook.walletmanager.Task;

import com.barook.walletmanager.Entity.Transaction;
import com.barook.walletmanager.Repository.TransactionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class TransactionSummaryTask {

    private TransactionRepository transactionRepository;

    public TransactionSummaryTask(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")  // Run at midnight every day
    public void printDailyTransactionSummary() {
        // Get the start of the day (midnight) for today
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();

        // Get all transactions that happened after the start of today
        List<Transaction> transactions = transactionRepository.findAllByTransactionDateAfter(startOfDay);

        // Calculate the total transaction amount for the day
        BigDecimal totalAmount = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Total amount of transactions for the day: " + totalAmount);
    }


}
