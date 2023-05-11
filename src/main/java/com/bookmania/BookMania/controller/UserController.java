package com.bookmania.BookMania.controller;

import com.bookmania.BookMania.dto.UserDto;
import com.bookmania.BookMania.event.RegistrationCompleteEvent;
import com.bookmania.BookMania.model.User;
import com.bookmania.BookMania.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserDto userDto, final HttpServletRequest request){

        User user = userService.registerUser(userDto);
        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                applicationUrl(request)
        ));

        return "Account Created Successfully";
    }

    private String applicationUrl(HttpServletRequest request) {

        return "http//" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
