package com.bookmania.BookMania.repository;

import com.bookmania.BookMania.model.User;
import com.bookmania.BookMania.services.UserService;
import com.bookmania.BookMania.services.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {

    }

    @Test
    void testFindByEmail_UserExists() {
        String email = "johndoe@example.com";

        User user = new User();
        user.setEmail(email);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userRepository.findByEmail(email);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(email, result.get().getEmail());
    }

    @Test
    public void testFindByEmail_UserNotExists() {
        String email = "nonexistinguser@example.com";

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = userRepository.findByEmail(email);

        Assertions.assertFalse(result.isPresent());

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
    }
}