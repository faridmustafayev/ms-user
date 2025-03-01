package com.example.ms.user.model.response;

import com.example.ms.user.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private UserStatus status;
    private Integer age;
    private String email;
    private String password;
    private LocalDateTime birthDate;
    private String profilePhoto;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
