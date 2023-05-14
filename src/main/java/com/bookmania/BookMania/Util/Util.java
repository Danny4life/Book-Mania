package com.bookmania.BookMania.Util;

import org.springframework.stereotype.Component;

@Component
public class Util {

    public boolean validatePassword(String password, String confirmPassword){

        return password.equals(confirmPassword);

    }
}
