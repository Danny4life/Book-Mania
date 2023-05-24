package com.bookmania.BookMania.repository;

import com.bookmania.BookMania.model.Book;
import com.bookmania.BookMania.model.User;
import com.bookmania.BookMania.model.VerificationToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class VerificationRepositoryTest {

    @Autowired
    private VerificationRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void testFindByToken_WhenTokenExists_ShouldReturnVerificationToken() {

        //Given



        //When


        //Then



    }

    @Test
    public void testFindByToken_WhenTokenDoesNotExist_ShouldReturnNull() {
        // Given
        String token = "non-existent-token";

        // When
        VerificationToken resultToken = underTest.findByToken(token);

        // Then
        assertNull(resultToken);
    }

}