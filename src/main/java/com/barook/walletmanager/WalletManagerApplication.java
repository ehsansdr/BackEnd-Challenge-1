package com.barook.walletmanager;

import com.barook.walletmanager.Entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EntityScan(basePackages = "com.barook.walletmanager.Entity")
public class WalletManagerApplication {

    public static void main(String[] args) {

        SpringApplication.run(WalletManagerApplication.class, args);
//        User user = User.builder().firstName("John").lastName("Doe").build();
//        System.out.println(user);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
