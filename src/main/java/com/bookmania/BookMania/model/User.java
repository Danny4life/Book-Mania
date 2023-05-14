package com.bookmania.BookMania.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
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
    @Email(message = "Email must be entered")
    @NotBlank(message = "Email should not be empty")
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank(message = "Password should not be empty")
    @Size(min = 6)
    private String password;
    private String role;
    private boolean enabled = false;
}
