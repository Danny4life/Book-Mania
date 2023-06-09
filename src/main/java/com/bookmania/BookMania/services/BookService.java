package com.bookmania.BookMania.services;

import com.bookmania.BookMania.dto.BookDto;
import com.bookmania.BookMania.model.Book;

import java.util.List;

public interface BookService {
    BookDto createBook(Long id, BookDto bookDto);

    BookDto getBookById(Long id);

    List<BookDto> getAllBooksByUserId(Long userId);

    BookDto updateBook(Long id, BookDto bookDto);

    boolean deleteBook(Long id);


    List<Book> getBooksByTitle(String title);
}
