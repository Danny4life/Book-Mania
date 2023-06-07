package com.bookmania.BookMania.services.serviceImpl;

import com.bookmania.BookMania.Util.Util;
import com.bookmania.BookMania.dto.LoginDto;
import com.bookmania.BookMania.dto.UserDto;
import com.bookmania.BookMania.exceptions.EmailAlreadyExistException;
import com.bookmania.BookMania.exceptions.EmailNotFoundException;
import com.bookmania.BookMania.exceptions.PasswordNotMatchException;
import com.bookmania.BookMania.model.PasswordResetToken;
import com.bookmania.BookMania.model.User;
import com.bookmania.BookMania.model.VerificationToken;
import com.bookmania.BookMania.repository.PasswordResetTokenRepository;
import com.bookmania.BookMania.repository.UserRepository;
import com.bookmania.BookMania.repository.VerificationRepository;
import com.bookmania.BookMania.response.LoginResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.AssertTrue;
import java.util.Calendar;
import java.util.Date;
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
    void toFindUserByEmail() {

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
    void testFindUserByEmail_WhenEmailNotFound(){

        //Given
        String email = "john@gmail.com";

        //When
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(EmailNotFoundException.class, ()-> underTest.findUserByEmail(email));

    }

    @Test
    void itShouldCreatePasswordResetTokenForUser() {

        //Given
        User user = new User();
        String token = "reset-token";

        //Act
        underTest.createPasswordResetTokenForUser(user, token);

        //Verify
        Mockito.verify(passwordResetTokenRepository).save(Mockito.any(PasswordResetToken.class));
    }

    @Test
    void itShouldValidatePasswordResetTokenWhenTokenIsValid() {

        //Given
        String token = "valid-token";
        User user = new User();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, 10);
        Date expirationTime = calendar.getTime();


        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        passwordResetToken.setExpirationTime(expirationTime);

        //When
        Mockito.when(passwordResetTokenRepository.findByToken(token)).thenReturn(passwordResetToken);

        //Act
        String result = underTest.validatePasswordResetToken(token);

        //Then
        Assertions.assertEquals("valid", result);
    }

    @Test
    void testValidatePasswordResetTokenWhenTokenIsInvalid(){
        //Given
        String token = "invalid-token";

        //When
        Mockito.when(passwordResetTokenRepository.findByToken(token)).thenReturn(null);

        //Act
        String result = underTest.validatePasswordResetToken(token);

        //Verify
        Assertions.assertEquals("Invalid", result);
    }

    @Test
    void testValidatePasswordResetToken_WhenTokenIsExpired(){
        //Given
        String token = "expired-token";
        User user = new User();
        Date expirationTime = new Date();

        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        passwordResetToken.setExpirationTime(expirationTime);

        //When
        Mockito.when(passwordResetTokenRepository.findByToken(token)).thenReturn(passwordResetToken);

        //Act
        String result = underTest.validatePasswordResetToken(token);

        //Then
        Assertions.assertEquals("Expired", result);
    }

    @Test
    void itShouldGetUserByPasswordResetToken() {
        //Given
        String token = "reset-token";
        User user = new User();
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);

        //When
        Mockito.when(passwordResetTokenRepository.findByToken(token)).thenReturn(passwordResetToken);

        //Act
        Optional<User> result = underTest.getUserByPasswordResetToken(token);

        //Then
        Assertions.assertEquals(Optional.of(user), result);
    }

//    @Test
//    void testGetUserByPasswordResetToken_WhenTokenNotFound(){
//        //Given
//        String token = "reset-token";
//
//        //When
//        Mockito.when(passwordResetTokenRepository.findByToken(token)).thenReturn(null);
//
//        //Act
//        Optional<User> result = underTest.getUserByPasswordResetToken(token);
//
//        //Then
//        Assertions.assertEquals(Optional.empty(), result);
//    }

    @Test
    void itShouldChangePassword() {
        //Given
        User user = Mockito.mock(User.class);
        String newPassword = "new-password";
        String encodedPassword = "encoded-password";

        //When
        Mockito.when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);

        //Act
        underTest.changePassword(user, newPassword);

        //Verify
        Mockito.verify(user).setPassword(encodedPassword);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    void itShouldCheckIfValidOldPassword() {
        //Given
        User user = new User();
        String oldPassword = "old-password";
        String encodedPassword = "encoded-password";

        //When
        Mockito.when(passwordEncoder.matches(oldPassword, encodedPassword)).thenReturn(true);
        user.setPassword(encodedPassword);

        //Then
        assertTrue(underTest.checkIfValidOldPassword(user, oldPassword));
    }

    @Test
    void testCheckIfInvalidOldPassword(){
        //Given
        User user = new User();
        String oldPassword = "old-password";
        String encodedPassword = "encoded-password";

        //When
        Mockito.when(passwordEncoder.matches(oldPassword, encodedPassword)).thenReturn(false);
        user.setPassword(encodedPassword);

        //Then
        assertFalse(underTest.checkIfValidOldPassword(user, oldPassword));
    }

    @Test
    void itShouldTestLoginUser_Successful() {
        //Given
       String email = "john@gmail.com";
       String password = "password";
       String encodedPassword = "encodedPassword";

       LoginDto loginDto = new LoginDto(email, password);
        //When
       User user = new User();
       user.setPassword(encodedPassword);
       when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
       when(userRepository.findOneByEmailAndPassword(email, encodedPassword)).thenReturn(Optional.of(user));
       when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        //Act
        LoginResponse response = underTest.loginUser(loginDto);

        LoginResponse expectedResponse = new LoginResponse("Login Successful", true);

        //Then
        assertEquals(expectedResponse, response);

    }

    @Test
    void test_Login_user_IncorrectPassword(){

    }

    @Test
    void testLoginUser_EmailNotFound(){
        //Given
        LoginDto loginDto = new LoginDto("john@gmail.com", "password");

        //When
        Mockito.when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.empty());

        //Act
        LoginResponse response = underTest.loginUser(loginDto);

        //Then
        assertFalse(response.getStatus());
        assertEquals(response.getMessage(), "Email does not exists");
    }
}