package com.andrbezr2016.mynotes.constants;

public interface ExceptionConstants {

    String EXCEPTION_INVALID_USER = "Invalid email or password";
    String EXCEPTION_EXISTING_USER = "User already exists";
    String EXCEPTION_INVALID_TOKEN = "Invalid refresh token";
    String EXCEPTION_UNAUTHORIZED = "Invalid access token";

    String EXCEPTION_USER_NOT_FOUND = "User not found";

    String EXCEPTION_CATEGORY_NOT_FOUND = "Category not found";
    String EXCEPTION_NO_ACCESS_TO_CATEGORY = "No access to category";

    String EXCEPTION_NOTE_NOT_FOUND = "Note not found";
    String EXCEPTION_NO_ACCESS_TO_NOTE = "No access to note";
}
