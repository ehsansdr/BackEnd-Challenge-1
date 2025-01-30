package com.barook.walletmanager.Service;

import com.barook.walletmanager.DTO.WalletDto;
import com.barook.walletmanager.Entity.User;
import com.barook.walletmanager.Entity.Wallet;
import com.barook.walletmanager.Repository.UserRepository;
import com.barook.walletmanager.Repository.WalletRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.logging.Logger;

@Service
public class WalletService {

    Logger log = Logger.getLogger(WalletService.class.getName());

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public WalletService(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    public Wallet saveWallet(WalletDto walletDto) {
        Wallet wallet = getWalletFromDto(walletDto);
        User user;
        if (userRepository.findById(walletDto.userId()) != null) {
            long userId = walletDto.userId();
            user = userRepository.findById(userId).orElse(null);
        }else {
            throw new IllegalArgumentException("User id cannot be null.");
        }
        wallet.setUser(user);

        Wallet walletSaved = walletRepository.save(wallet);
        return walletRepository.save(walletSaved);
    }

    public Wallet getWalletFromDto(WalletDto walletDto){
        Wallet wallet =  Wallet.builder()
                .balance(walletDto.balance())
                .build();
        return wallet;
    }

    public String getTotalBalanceByPrettyJson(int userId) throws JsonProcessingException {
        ObjectMapper jsonObject = new ObjectMapper();

        // Create an ObjectNode to represent a JSON object
        ObjectNode objectNode = jsonObject.createObjectNode();


        List<Long> totalBalnce;
        totalBalnce = walletRepository.getUserTotalBallance(userId);

        // map.put("user_id", userId);
        //map.put("total_balance", totalBalnce.get(0));

        // Add fields to the ObjectNode
        objectNode.put("total_balance", totalBalnce.get(0));

        // if you use writeValueAsString you will get string value
        // Pretty-print the JSON by using the writerWithDefaultPrettyPrinter method
        String prettyJson = jsonObject.writerWithDefaultPrettyPrinter()
                .writeValueAsString(objectNode);


        System.out.println("jsonObject : " + prettyJson);
        return prettyJson;
    }

    public String getTotalBalanceAsString(int userId) throws JsonProcessingException {
        ObjectMapper jsonObject = new ObjectMapper();

        // Create an ObjectNode to represent a JSON object
        ObjectNode objectNode = jsonObject.createObjectNode();


        List<Long> totalBalnce;
        totalBalnce = walletRepository.getUserTotalBallance(userId);

        // Add fields to the ObjectNode
        objectNode.put("total_balance", totalBalnce.get(0));

        // if you use writeValueAsString you will get string value
        // Pretty-print the JSON by using the writerWithDefaultPrettyPrinter method
        String prettyJson = jsonObject.writerWithDefaultPrettyPrinter()
                .writeValueAsString(objectNode);


        System.out.println("jsonObject : " + prettyJson);
        return prettyJson;
    }


}
