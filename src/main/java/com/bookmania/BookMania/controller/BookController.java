package com.bookmania.BookMania.controller;

import com.bookmania.BookMania.dto.BookDto;
import com.bookmania.BookMania.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create-book/{id}")
    public ResponseEntity<BookDto> createBook(@PathVariable Long id, @RequestBody BookDto bookDto){
        bookDto = bookService.createBook(id, bookDto);

        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);

    }

    @GetMapping("/get-book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id){
        BookDto bookDto = null;
        bookDto = bookService.getBookById(id);

        return ResponseEntity.ok(bookDto);

    }
}
