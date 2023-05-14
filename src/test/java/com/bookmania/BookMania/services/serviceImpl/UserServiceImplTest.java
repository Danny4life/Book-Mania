package com.bookmania.BookMania.services.serviceImpl;

import com.bookmania.BookMania.dto.UserDto;
import com.bookmania.BookMania.model.User;
import com.bookmania.BookMania.repository.UserRepository;
import com.bookmania.BookMania.repository.VerificationRepository;
import com.bookmania.BookMania.services.UserService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationRepository verificationRepository;

   @Autowired
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp(){
       userService = new UserServiceImpl(userRepository, passwordEncoder, verificationRepository);
    }

    @Test
    void testRegisterUser_Success() {

        UserDto userDto = new UserDto();
        userDto.setFirstname("John");
        userDto.setLastname("Doe");
        userDto.setEmail("john@gmail.com");
        userDto.setPassword("password");

        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");

        User createUser = userService.registerUser(userDto);
        verify(userRepository, times(1)).save(user);

        assertEquals(userDto.getFirstname(), createUser.getFirstname());
        assertEquals(userDto.getFirstname(), createUser.getLastname());
        assertEquals(userDto.getEmail(), createUser.getEmail());
        assertEquals("USER", createUser.getRole());
        assertEquals("encodedPassword", createUser.getPassword());


    }

    @Test
    void validateVerificationToken() {
    }

    @Test
    void saveVerificationTokenForUser() {
    }
}