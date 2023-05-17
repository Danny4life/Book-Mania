package com.bookmania.BookMania.model;

import com.bookmania.BookMania.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book_tbl")
@Builder
public class Book extends BaseClass {

    private String title;
    private String author;
    private int pages;
    private BookStatus bookStatus;
}
