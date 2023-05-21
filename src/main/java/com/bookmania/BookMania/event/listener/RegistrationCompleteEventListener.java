package com.bookmania.BookMania.event.listener;

import com.bookmania.BookMania.event.RegistrationCompleteEvent;
import com.bookmania.BookMania.model.User;
import com.bookmania.BookMania.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@AllArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        // Create the verification token for the user with link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);

        // Send mail to user
        String url =
                event.getApplicationUrl()
                //+ "/api/v1/user"
                + "/verify-registration?token="
                + token;

        // Send verification email
        log.info("Click the link to verify your account: {}",
                url);
    }
}
