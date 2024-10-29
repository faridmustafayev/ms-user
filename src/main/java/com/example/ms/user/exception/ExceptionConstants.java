package com.example.ms.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionConstants {
    UNEXPECTED_EXCEPTION("UNEXPECTED_EXCEPTION", "Unexpected exception occurred"),
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found"),
    CLIENT_ERROR("CLIENT_ERROR", "Exception from client"),
    METHOD_NOT_ALLOWED("METHOD_NOT_ALLOWED", "http method is not correct");
    private String code;
    private String message;
}
