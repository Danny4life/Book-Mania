package com.bookmania.BookMania.model;

import com.bookmania.BookMania.validations.ValidEmail;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_tbl")
@Builder
public class User extends BaseClass {

    @NotNull(message = "Firstname should not be empty")
    @NotBlank(message = "Firstname should not be empty")
    @Size(min = 2, message = "Firstname should have at least two characters")
    @Column(nullable = false)
    private String firstname;
    @NotNull(message = "Lastname should not be empty")
    @NotBlank(message = "Lastname should not be empty")
    @Size(min = 2, message = "Lastname should have at least two characters")
    @Column(nullable = false)
    private String lastname;
    @ValidEmail
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email should not be empty")
    @Column(unique = true, nullable = false)
    private String email;
    @NotNull(message = "Password should not be empty")
    @NotBlank(message = "Password should not be blank")
    @Size(min = 6)
    private String password;
    private String role;
    private boolean enabled = false;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Book> book;
}
