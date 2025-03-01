package com.example.ms.user.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CreateUserRequest {
    private String username;

    private Integer age;

    private String email;

    private String profilePhoto;

    private String password;

    private String address;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthDate;
}
