package com.bookmania.BookMania.services.serviceImpl;

import com.bookmania.BookMania.Util.Util;
import com.bookmania.BookMania.dto.UserDto;
import com.bookmania.BookMania.exceptions.EmailAlreadyExistException;
import com.bookmania.BookMania.exceptions.PasswordNotMatchException;
import com.bookmania.BookMania.model.User;
import com.bookmania.BookMania.model.VerificationToken;
import com.bookmania.BookMania.repository.PasswordResetTokenRepository;
import com.bookmania.BookMania.repository.UserRepository;
import com.bookmania.BookMania.repository.VerificationRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void itShouldValidateAndReturnVerificationToken() {
        //When
        String token = "validToken";
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);

        User user = new User();
        user.setFirstname("john");
        user.setLastname("Doe");
        user.setEmail("john@gmail.com");
        user.setPassword("password");
        user.setRole("USER");
        user.setEnabled(true);
        verificationToken.setUser(user);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 1);
        verificationToken.setExpirationTime(cal.getTime());

        //When
        when(verificationRepository.findByToken(token)).thenReturn(verificationToken);

        //Act
        String result = underTest.validateVerificationToken(token);

        //Then
        verify(userRepository, times(1)).save(user);
        assertEquals("valid", result);
    }

    @Test
    void itShouldGenerateNewVerificationToken() {
        //Given
        String oldToken = "oldToken";
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(oldToken);

        //When
        when(verificationRepository.findByToken(oldToken)).thenReturn(verificationToken);

        //Act
        VerificationToken result = underTest.generateNewVerificationToken(oldToken);

        //Then
        assertNotNull(result);
        assertNotEquals(oldToken, result.getToken());

        verify(verificationRepository, times(1)).save(verificationToken);
    }

    @Test
    void itShouldSaveVerificationTokenForUser() {

        //Given
        String token = "token";
        User user = new User();

        //When
        underTest.saveVerificationTokenForUser(token, user);

        //Then
        Mockito.verify(verificationRepository).save(Mockito.any(VerificationToken.class));
    }

    @Test
    void findUserByEmail() {

        //Given
        String email = "john@gmail.com";
        User user = new User();

        //When
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        //Act
        User result = underTest.findUserByEmail(email);

        //Then
        Assertions.assertEquals(user, result);
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