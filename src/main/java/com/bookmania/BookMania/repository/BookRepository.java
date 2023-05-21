package com.bookmania.BookMania.repository;

import com.bookmania.BookMania.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByUserId(Long userId);

    List<Book> findByTitleContainingIgnoreCase(String title);
}
