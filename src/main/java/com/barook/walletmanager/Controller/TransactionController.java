package com.barook.walletmanager.Controller;

import com.barook.walletmanager.DTO.TransactionDto;
import com.barook.walletmanager.ResponceDTO.TransactionResDto;
import com.barook.walletmanager.Service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/save-transaction")
    public ResponseEntity<TransactionResDto> saveTransaction(@RequestBody TransactionDto transactionDto) {

        TransactionResDto transactionResDto = transactionService.saveTransaction(transactionDto);

        return new ResponseEntity<>(transactionResDto, HttpStatus.CREATED);
    }
}
