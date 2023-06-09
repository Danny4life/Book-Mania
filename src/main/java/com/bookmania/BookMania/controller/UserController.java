package com.bookmania.BookMania.controller;

import com.bookmania.BookMania.dto.LoginDto;
import com.bookmania.BookMania.dto.PasswordDto;
import com.bookmania.BookMania.dto.UserDto;
import com.bookmania.BookMania.event.RegistrationCompleteEvent;
import com.bookmania.BookMania.model.User;
import com.bookmania.BookMania.model.VerificationToken;
import com.bookmania.BookMania.response.LoginResponse;
import com.bookmania.BookMania.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://127.0.0.1:5173")
@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    private final Validator validator;



    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto userDto, final HttpServletRequest request){

        User user = userService.registerUser(userDto);
        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                applicationUrl(request)
        ));

        return ResponseEntity.ok("Account Created Successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto){
        LoginResponse loginResponse = userService.loginUser(loginDto);

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/register/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        UserDto userDto = null;
        userDto = userService.getUserById(id);

        return ResponseEntity.ok(userDto);


    }


    @GetMapping("/verify-registration")
    public ResponseEntity<String> verifyRegistration(@RequestParam("token") String token){
        String result = userService.validateVerificationToken(token);

        if(result.equalsIgnoreCase("valid")){
            return ResponseEntity.ok("Your Account Has Been Verify Successfully");
        }
            return ResponseEntity.ok("Bad User");
    }

    @GetMapping("/resend-verification-link")

    public ResponseEntity<String> resendVerificationToken(@RequestParam("token") String oldToken,
                                                          HttpServletRequest request){

        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
        
        return ResponseEntity.ok("Verification link sent");
    }

    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {

        String url =
                applicationUrl
                        + "/api/v1/user"
                        + "/verify-registration?token="
                        + verificationToken.getToken();

        // Send verification email
        log.info("Click the link to verify your account: {}",
                url);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordDto passwordDto,HttpServletRequest request){
        User user = userService.findUserByEmail(passwordDto.getEmail());

        String url = "";

        if(user != null){
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(user, applicationUrl(request), token);
        }

        return ResponseEntity.ok(url);

    }

    private String passwordResetTokenMail(User user, String applicationUrl, String token) {

        String url =
                applicationUrl
                + "/api/v1/user"
                + "/save-password?token="
                + token;

        log.info("Click the link to reset your password : {}",
                url);

        return url;

    }

    @PostMapping("/save-password")
    public ResponseEntity<String> savePassword(@RequestParam("token") String token,
                                               @RequestBody PasswordDto passwordDto){

        String result = userService.validatePasswordResetToken(token);

        if(!result.equalsIgnoreCase("valid")){
            return ResponseEntity.ok("Invalid Token");
        }

        Optional<User> user = userService.getUserByPasswordResetToken(token);

        if(user.isPresent()){
            userService.changePassword(user.get(), passwordDto.getNewPassword());

            return ResponseEntity.ok("Password Reset Successfully");
        }else {
            return ResponseEntity.ok("Invalid token");
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordDto passwordDto){
        User user = userService.findUserByEmail(passwordDto.getEmail());

        if(!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())){
            return ResponseEntity.ok("Invalid Old Password");
        }

        userService.changePassword(user, passwordDto.getNewPassword());

        return ResponseEntity.ok("Password Change Successfully");

    }




    private String applicationUrl(HttpServletRequest request) {

        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
