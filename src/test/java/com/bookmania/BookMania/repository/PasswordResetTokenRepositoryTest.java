package com.bookmania.BookMania.repository;

import com.bookmania.BookMania.model.PasswordResetToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PasswordResetTokenRepositoryTest {

    @Autowired
    private PasswordResetTokenRepository underTest;


    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByToken_WhenTokenExists_ShouldReturnPasswordResetToken() {

        // Given
        String token = "token";
        PasswordResetToken expectedToken = new PasswordResetToken();
        expectedToken.setToken(token);

        underTest.save(expectedToken);

        // When
        PasswordResetToken resultToken = underTest.findByToken(token);

        // Then
        assertNotNull(resultToken);
        assertEquals(expectedToken, resultToken);

    }
}