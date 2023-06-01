package com.bookmania.BookMania.controller;

import com.bookmania.BookMania.dto.BookDto;
import com.bookmania.BookMania.dto.UserDto;
import com.bookmania.BookMania.model.Book;
import com.bookmania.BookMania.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://127.0.0.1:5173")
@RestController
@RequestMapping("/api/v1/book")
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


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookDto>> getAllBooksByUserId(@PathVariable Long userId){

        List<BookDto> books = bookService.getAllBooksByUserId(userId);

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBookByTitle(@RequestParam("title") String title){
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @PutMapping("update-book/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto){
        bookDto = bookService.updateBook(id, bookDto);

        return ResponseEntity.ok(bookDto);

    }

    @DeleteMapping("/delete-book/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBook(@PathVariable Long id){

        boolean deleted = false;
        deleted = bookService.deleteBook(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Delete", deleted);

        return ResponseEntity.ok(response);

    }
}
