package com.barook.walletmanager.Service;

import com.barook.walletmanager.DTO.UserDto;
import com.barook.walletmanager.Entity.User;
import com.barook.walletmanager.Repository.UserRepository;
import com.barook.walletmanager.ResponceDTO.UserResDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserService {

    Logger log = Logger.getLogger(this.getClass().getName());

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByid(long id) {
        log.info("Find user by id: " + id);

        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        log.info("Save user: " + user);

        return userRepository.save(user);
    }

    public User saveUserDto(UserDto userDto) {
        log.info("Save user: " + userDto);

        User user = getUserFromuserDto(userDto);

        return userRepository.save(user);
    }

    public User getUserFromuserDto(UserDto userDto) {
        log.info("Get user from userDto: " + userDto);
        User user = User.builder()
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .nationalId(userDto.nationalId())
                .build();
        return user;
    }

    public UserResDto getUserResDtoFromUser(User user) {
        UserResDto userResDto = new UserResDto(user.getId(),
                user.getNationalId(),
                user.getFirstName(),
                user.getLastName(),
                user.getCreatedAt());

        return userResDto;
    }


    public List<UserResDto> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream()  // Create a stream from the List<User>
                .map(this::getUserResDtoFromUser) // Transform each User into UserResDto
                .collect(Collectors.toList());  // Collect the results into a List<UserResDto>
    }
}
