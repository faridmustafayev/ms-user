//package com.example.ms.user.service.concrete;
//
//import com.example.ms.user.aop.annotation.Log;
//import com.example.ms.user.client.AuthClient;
//import com.example.ms.user.service.abstraction.AuthService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Log
//@Service(value = "authServiceHandler")
//@RequiredArgsConstructor
//public class AuthServiceHandler implements AuthService {
//    private final AuthClient authClient;
//
//    @Override
//    public boolean verify(String accessToken) {
//        return authClient.verifyToken(accessToken);
//    }
//}
