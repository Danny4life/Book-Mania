package com.bookmania.BookMania.services;

import com.bookmania.BookMania.dto.UserDto;
import com.bookmania.BookMania.model.User;

public interface UserService {
    void saveVerificationTokenForUser(String token, User user);

    User registerUser(UserDto userDto);

    String validateVerificationToken(String token);
}
