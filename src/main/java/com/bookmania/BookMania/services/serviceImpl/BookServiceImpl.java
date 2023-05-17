package com.bookmania.BookMania.services.serviceImpl;

import com.bookmania.BookMania.dto.BookDto;
import com.bookmania.BookMania.exceptions.UserNotFoundException;
import com.bookmania.BookMania.model.Book;
import com.bookmania.BookMania.model.User;
import com.bookmania.BookMania.repository.BookRepository;
import com.bookmania.BookMania.repository.UserRepository;
import com.bookmania.BookMania.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public BookDto createBook(Long id, BookDto bookDto) {

        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User does not exists"));

        Book book = Book.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .pages(bookDto.getPages())
                .bookStatus(bookDto.getBookStatus())
                .user(user)
                .build();

        bookRepository.save(book);

        return bookDto;
    }
}
