package com.andrbezr2016.mynotes.utilities;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptEncoder {

    private final int rounds = 10;

    public String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(rounds));
    }

    public boolean check(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
