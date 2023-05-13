package com.bookmania.BookMania.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class VerificationToken extends BaseClass {

    private static final int  EXPIRATION_TIME = 10;
    private String token;
    private Date expirationTime;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    private User user;


    public VerificationToken(String token, User user) {
        super();
        this.token = token;
        this.user = user;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    private Date calculateExpirationDate(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);

        return new Date(calendar.getTime().getTime());
    }
}
