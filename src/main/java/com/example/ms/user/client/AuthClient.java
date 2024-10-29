package com.example.ms.user.client;

import com.example.ms.user.client.decoder.CustomErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@FeignClient(name = "ms-auth",
        url = "${client.urls.ms-auth}",
        configuration = CustomErrorDecoder.class)
public interface AuthClient {
    @PostMapping("internal/v1/auth/verify")
    boolean verifyToken(@RequestHeader(AUTHORIZATION) String accessToken);
}
