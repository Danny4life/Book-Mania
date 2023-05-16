package com.bookmania.BookMania.services;

import com.bookmania.BookMania.dto.LoginDto;
import com.bookmania.BookMania.dto.UserDto;
import com.bookmania.BookMania.model.User;
import com.bookmania.BookMania.model.VerificationToken;
import com.bookmania.BookMania.response.LoginResponse;

import java.util.Optional;

public interface UserService {
    void saveVerificationTokenForUser(String token, User user);

    User registerUser(UserDto userDto);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    boolean checkIfValidOldPassword(User user, String oldPassword);

    LoginResponse loginUser(LoginDto loginDto);
}
