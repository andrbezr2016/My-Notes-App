package com.andrbezr2016.mynotes.constants;

public interface ExceptionConstants {

    String EXCEPTION_GENERIC = "Something went wrong";
    String EXCEPTION_INVALID_USER = "Invalid email or password";
    String EXCEPTION_EXISTING_USER = "User already exists";
    String EXCEPTION_INVALID_TOKEN = "Invalid refresh token";
    String EXCEPTION_UNAUTHORIZED = "Invalid access token";
    String EXCEPTION_USER_NOT_FOUND = "User not found";
    String EXCEPTION_CATEGORY_NOT_FOUND = "Category not found";
    String EXCEPTION_NOTE_NOT_FOUND = "Note not found";

    String EXCEPTION_PASSWORD = "Must not contain spaces";
    String EXCEPTION_EMAIL = "Invalid email format";
    String EXCEPTION_SIZE = "Size must be between 1 and 256";
    String EXCEPTION_PASSWORD_SIZE = "Size must be between 8 and 256";
    String EXCEPTION_EMAIL_SIZE = "Size must be between 1 and 254";
    String EXCEPTION_NOT_NULL = "Must not be null";
    String EXCEPTION_POSITIVE = "Must be positive";
    String EXCEPTION_NOT_BLANK = "Must not be blank";
}
