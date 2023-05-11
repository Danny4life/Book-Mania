package com.bookmania.BookMania.services;

import com.bookmania.BookMania.model.User;

public interface UserService {
    void saveVerificationTokenForUser(String token, User user);
}
