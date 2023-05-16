package com.bookmania.BookMania.services.serviceImpl;

import com.bookmania.BookMania.Util.Util;
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
import com.bookmania.BookMania.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final VerificationRepository verificationRepository;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final Util util;



    @Override
    public User registerUser(UserDto userDto) {

        boolean userExists = userRepository.findByEmail(userDto.getEmail()).isPresent();
        boolean isPasswordMatch = util.validatePassword(userDto.getPassword(), userDto.getConfirmPassword());


        if(userExists){
            throw new EmailAlreadyExistException("Email Already Exists");
        }

        if(!isPasswordMatch){
            throw new PasswordNotMatchException("Passwords do not match");
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

        VerificationToken verificationToken = verificationRepository.findByToken(token);

        if(verificationToken == null){
            return "Invalid";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0){
            verificationRepository.delete(verificationToken);

            return "Expired";
        }

        user.setEnabled(true);
        userRepository.save(user);

        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verificationRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationRepository.save(verificationToken);

        return verificationToken;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {

        VerificationToken verificationToken = new VerificationToken(token, user);

        verificationRepository.save(verificationToken);

    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new EmailNotFoundException("Email not found"));
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);

    }

    @Override
    public String validatePasswordResetToken(String token) {

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        return null;
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.empty();
    }

    @Override
    public void changePassword(User user, String newPassword) {

    }
}
