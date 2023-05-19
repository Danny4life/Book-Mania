package com.bookmania.BookMania.dto;

import com.bookmania.BookMania.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {


    private String title;
    private String author;
    private int pages;
    private BookStatus bookStatus;

    public BookDto(String title, String author, int pages, BookStatus bookStatus, Long id) {

    }
}
