package com.barook.walletmanager.Controller;

import com.barook.walletmanager.DTO.UserDto;
import com.barook.walletmanager.Entity.User;
import com.barook.walletmanager.ResponceDTO.UserResDto;
import com.barook.walletmanager.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserController {
    Logger log = Logger.getLogger(this.getClass().getName());

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save-user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        log.info("Adding user: " + user);
        User UserSaved = userService.saveUser(user);
        return new ResponseEntity<>(UserSaved, HttpStatus.CREATED);
    }

    @PostMapping("/save-user-dto")
    public ResponseEntity<UserResDto> addUser(@RequestBody UserDto userDto) {
        log.info("Adding user: " + userDto);

        User UserSaved = userService.saveUserDto(userDto); // this method save the entity

        UserResDto userResDto = userService.getUserResDtoFromUser(UserSaved);

        return new ResponseEntity<>(userResDto, HttpStatus.CREATED);
    }

    @GetMapping("/all-user")
    public ResponseEntity<List<UserResDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.ACCEPTED);
    }
}
