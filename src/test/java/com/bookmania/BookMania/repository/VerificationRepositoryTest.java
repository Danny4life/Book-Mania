package com.bookmania.BookMania.repository;

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
    }

    @Test
    void testFindByToken_WhenTokenExists_ShouldReturnVerificationToken() {

        //Given
        String token = "some_token";
        User user = new User();
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("john@gmail.com");
        user.setPassword("password");
        user.setRole("USER");
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());


        VerificationToken expectedToken = new VerificationToken(token,  user);
        underTest.save(expectedToken);

        //When
        VerificationToken resultToken = underTest.findByToken(token);

        //Then
        assertNotNull(resultToken);
        assertEquals(expectedToken, resultToken);
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