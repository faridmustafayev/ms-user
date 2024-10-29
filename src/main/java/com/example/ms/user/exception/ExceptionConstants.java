package com.example.ms.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionConstants {
    UNEXPECTED_EXCEPTION("UNEXPECTED_EXCEPTION", "Unexpected exception occurred"),
    USER_NOT_FOUND("USER_NOT_FOUND", "User with id %s not found"),
    CLIENT_ERROR("CLIENT_ERROR", "Exception from client"),
    HTTP_METHOD_IS_NOT_CORRECT("HTTP_METHOD_IS_NOT_CORRECT", "http method is not correct");

    private final String code;
    private final String message;

    public String getMessage(Object... params) {
        return String.format(message, params);
    }
}
