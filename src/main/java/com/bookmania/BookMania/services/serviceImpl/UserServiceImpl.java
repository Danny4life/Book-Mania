package com.bookmania.BookMania.services.serviceImpl;

import com.bookmania.BookMania.dto.UserDto;
import com.bookmania.BookMania.exceptions.EmailAlreadyExistException;
import com.bookmania.BookMania.model.User;
import com.bookmania.BookMania.repository.UserRepository;
import com.bookmania.BookMania.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserDto userDto) {

        boolean userExists = userRepository.findByEmail(userDto.getEmail()).isPresent();

        if(userExists){
            throw new EmailAlreadyExistException("Email Already Exists");
        }

        User user = User.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .role("USER")
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        userRepository.save(user);

        return user;
    }

    @Override
    public String validateVerificationToken(String token) {
        return null;
    }


    @Override
    public void saveVerificationTokenForUser(String token, User user) {

    }


}
