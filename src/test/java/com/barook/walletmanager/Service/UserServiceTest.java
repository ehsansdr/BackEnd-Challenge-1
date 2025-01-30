package com.barook.walletmanager.Service;

import com.barook.walletmanager.DTO.UserDto;
import com.barook.walletmanager.Entity.User;
import com.barook.walletmanager.Repository.UserRepository;
import com.barook.walletmanager.ResponceDTO.UserResDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;


    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void save_user() {

        // given
        User user = User.builder()
                .firstName("Ali")
                .lastName("Poor")
                .nationalId(0110)
                .build();

        // when
        User savedUser = userService.saveUser(user);

        // then
        assertEquals(user.getFirstName(), savedUser.getFirstName());
        assertEquals(user.getLastName(), savedUser.getLastName());
        assertEquals(user.getNationalId(), savedUser.getNationalId());
    }

    @Test
    void get_all_user() {
        List<UserResDto> userList = userService.getAllUser();
        System.out.println(userList);

        List<User> users = userRepository.findAll();
        System.out.println(users);

        assertNotNull(userList);
        assertNotNull(users);
    }
}