package com.andrbezr2016.mynotes.constants;

public interface ApiConstants {

    String API_AUTH_PATH = "/auth";
    String API_USER_PATH = "/user";
    String API_CATEGORIES_PATH = "/categories";
    String API_NOTES_PATH = "/notes";

    String EXCEPTION_INVALID_USER = "Invalid email or password";
    String EXCEPTION_EXISTING_USER = "User already exists";
    String EXCEPTION_INVALID_TOKEN = "Invalid refresh token";

    int ACCESS_EXPIRED_IN_MINUTES = 60;
    int REFRESH_EXPIRED_IN_MINUTES = 1440;
}
