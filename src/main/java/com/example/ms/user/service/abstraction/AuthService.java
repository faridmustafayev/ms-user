package com.example.ms.user.service.abstraction;

public interface AuthService {
    boolean verify(String accessToken);
}
