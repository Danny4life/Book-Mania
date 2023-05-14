package com.bookmania.BookMania.controller;

import com.bookmania.BookMania.dto.UserDto;
import com.bookmania.BookMania.event.RegistrationCompleteEvent;
import com.bookmania.BookMania.model.User;
import com.bookmania.BookMania.model.VerificationToken;
import com.bookmania.BookMania.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto, final HttpServletRequest request){

        User user = userService.registerUser(userDto);
        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                applicationUrl(request)
        ));

        return ResponseEntity.ok("Account Created Successfully");
    }

    @GetMapping("/verify-registration")
    public ResponseEntity<String> verifyRegistration(@RequestParam("token") String token){
        String result = userService.validateVerificationToken(token);

        if(result.equalsIgnoreCase("valid")){
            return ResponseEntity.ok("User Verify Successfully");
        }
            return ResponseEntity.ok("Bad User");
    }

    @GetMapping("/resend-verification-token")

    public ResponseEntity<String> resendVerificationToken(@RequestParam("token") String oldToken,
                                                          HttpServletRequest request){

        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
        
        return ResponseEntity.ok("Verification link sent");


    }

    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
    }


    private String applicationUrl(HttpServletRequest request) {

        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
