package com.bookmania.BookMania.repository;

import com.bookmania.BookMania.model.Book;
import com.bookmania.BookMania.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByUserId(Long userId);
}
