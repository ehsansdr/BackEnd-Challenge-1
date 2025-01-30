package com.barook.walletmanager;

import com.barook.walletmanager.Entity.User;
import com.barook.walletmanager.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EntityScan(basePackages = "com.barook.walletmanager.Entity")
public class WalletManagerApplication {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {

        SpringApplication.run(WalletManagerApplication.class, args);

    }

//    @Bean
//    public CommandLineRunner commandLineRunner(UserRepository userRepository) {
//        return args ->
//        {User user = User.builder().firstName("John").lastName("Doe").build();
//            //userRepository.save(user);
//            System.out.println(userRepository.save(user));};
//    }

}
