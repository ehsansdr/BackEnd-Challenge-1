package com.barook.walletmanager.Controller;

import com.barook.walletmanager.DTO.WalletDto;
import com.barook.walletmanager.Entity.Wallet;
import com.barook.walletmanager.ResponceDTO.WalletResDto;
import com.barook.walletmanager.Service.WalletService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        String jsonStr = walletService.getTotalBalanceByPrettyJson(userId);

        return new ResponseEntity<>(jsonStr, HttpStatus.OK);

    }

    @GetMapping("/get-all-wallets")
    public ResponseEntity<List<WalletResDto>> getAllWallets() {
        return new ResponseEntity<>(
                walletService.getAllWallet(),
                HttpStatus.OK);
    }

    @GetMapping("/get-all-wallets-of-user/{userid}")
    public ResponseEntity<List<WalletResDto>> getAllWallets(
            @PathVariable("userid") long userId) throws JsonProcessingException {
        return new ResponseEntity<>(
                walletService.getAllWalletByUserid(userId),
                HttpStatus.OK);
    }


}
