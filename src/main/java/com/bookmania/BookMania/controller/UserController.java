package com.bookmania.BookMania.controller;

import com.bookmania.BookMania.dto.UserDto;
import com.bookmania.BookMania.event.RegistrationCompleteEvent;
import com.bookmania.BookMania.model.User;
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



    private String applicationUrl(HttpServletRequest request) {

        return "http//" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
