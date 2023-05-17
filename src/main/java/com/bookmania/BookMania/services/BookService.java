package com.bookmania.BookMania.services;

import com.bookmania.BookMania.dto.BookDto;

public interface BookService {
    BookDto createBook(Long id, BookDto bookDto);

    BookDto getBookById(Long id);
}
