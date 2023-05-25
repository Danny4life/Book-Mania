package com.bookmania.BookMania.services.serviceImpl;

import com.bookmania.BookMania.Util.Util;
import com.bookmania.BookMania.dto.UserDto;
import com.bookmania.BookMania.model.User;
import com.bookmania.BookMania.repository.PasswordResetTokenRepository;
import com.bookmania.BookMania.repository.UserRepository;
import com.bookmania.BookMania.repository.VerificationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private VerificationRepository verificationRepository;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private Util util;

    @InjectMocks
    private UserServiceImpl underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldRegisterAndSaveUser() {
        //Given
        UserDto userDto = new UserDto();
        userDto.setFirstname("John");
        userDto.setLastname("Doe");
        userDto.setEmail("john@gmail.com");
        userDto.setPassword("password");
        userDto.setConfirmPassword("password");

        //When
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(util.validatePassword(userDto.getPassword(), userDto.getConfirmPassword())).thenReturn(true);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");

        //Act
        User result = underTest.registerUser(userDto);

        //Then
        verify(userRepository).save(any(User.class));
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
        assertEquals("john@gmail.com", result.getEmail());
        assertEquals("USER", result.getRole());
        assertEquals("encodedPassword", result.getPassword());
    }

    @Test
    void validateVerificationToken() {
    }

    @Test
    void generateNewVerificationToken() {
    }

    @Test
    void saveVerificationTokenForUser() {
    }

    @Test
    void findUserByEmail() {
    }

    @Test
    void createPasswordResetTokenForUser() {
    }

    @Test
    void validatePasswordResetToken() {
    }

    @Test
    void getUserByPasswordResetToken() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void checkIfValidOldPassword() {
    }

    @Test
    void loginUser() {
    }
}