package com.bookmania.BookMania.services.serviceImpl;

import com.bookmania.BookMania.dto.UserDto;
import com.bookmania.BookMania.model.User;
import com.bookmania.BookMania.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public User registerUser(UserDto userDto) {
        return null;
    }
    @Override
    public void saveVerificationTokenForUser(String token, User user) {

    }


}
