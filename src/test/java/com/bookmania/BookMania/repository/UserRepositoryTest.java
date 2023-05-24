package com.bookmania.BookMania.repository;

import com.bookmania.BookMania.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfUserEmailExists() {
        // Given
        User user = new User();
        user.setFirstname("john");
        user.setLastname("doe");
        user.setEmail("john@gmail.com");
        user.setPassword("password");
        user.setRole("USER");
        user.setEnabled(true);
        underTest.save(user);

        // When
        Optional<User> userExists = underTest.findByEmail("john@gmail.com");

        // Then
        assertTrue(userExists.isPresent());
        assertEquals("john@gmail.com", userExists.get().getEmail());
    }

    @Test
    void itShouldCheckIfUserEmailDoesNotExists() {
        // Given
        User user = new User();
        user.setFirstname("john");
        user.setLastname("doe");
        user.setEmail("john@gmail.com");
        user.setPassword("password");
        user.setRole("USER");
        user.setEnabled(true);

        // When
        Optional<User> userExists = underTest.findByEmail("john@gmail.com");

        // Then
        assertFalse(userExists.isPresent());

    }

    @Test
    void itShouldFindOneByEmailAndPassword() {

        //Given
        User user = new User();
        user.setFirstname("john");
        user.setLastname("doe");
        user.setEmail("john@gmail.com");
        user.setPassword("password");
        user.setRole("USER");
        user.setEnabled(true);
        underTest.save(user);

        //When
        Optional<User> userExists = underTest.findOneByEmailAndPassword("john@gmail.com", "password");

        //Then
        assertTrue(userExists.isPresent());
        assertEquals("john@gmail.com", userExists.get().getEmail());
        assertEquals("password", userExists.get().getPassword());
    }
}