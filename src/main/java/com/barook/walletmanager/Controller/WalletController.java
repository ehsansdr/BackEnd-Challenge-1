package com.barook.walletmanager.Controller;

import com.barook.walletmanager.DTO.WalletDto;
import com.barook.walletmanager.Entity.Wallet;
import com.barook.walletmanager.Service.WalletService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.runtime.ObjectMethods;
import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/save-wallet")
    public ResponseEntity<Wallet> post(@RequestBody WalletDto walletDto) {
        Wallet wallet = walletService.saveWallet(walletDto);
        return new ResponseEntity<Wallet>(wallet, HttpStatus.CREATED);
    }


    @GetMapping("/get-total-balance-by-user-id/{user_id}")
    public ResponseEntity<String> getTotalBalanceOfTheUser(
            @PathVariable("user_id") int userId) throws JsonProcessingException {

        String jsonStr = walletService.getTotalBalance(userId);

        return new ResponseEntity<>(jsonStr, HttpStatus.OK);

    }


}
