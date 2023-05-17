package com.bookmania.BookMania.model;

import com.bookmania.BookMania.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book_tbl")
@Builder
public class Book extends BaseClass {

    @NotBlank(message = "Book must have title")
    @Column(nullable = false)
    private String title;
    @NotBlank(message = "Book must have author name")
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private int pages;
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Book must read status must not be blank")
    @Column(nullable = false)
    private BookStatus bookStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
