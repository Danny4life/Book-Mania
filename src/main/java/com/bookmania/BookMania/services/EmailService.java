package com.bookmania.BookMania.services;

import com.bookmania.BookMania.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
