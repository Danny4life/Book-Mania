package com.bookmania.BookMania.repository;

import com.bookmania.BookMania.model.User;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findOneByEmailAndPassword(String email, String password);

}
