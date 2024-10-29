package com.example.ms.user.model.response;

import com.example.ms.user.model.enums.UserStatus;
import com.example.ms.user.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private UserStatus status;
    private int age;
    private String email;
    private String password;
    private UserType userType;
    private Date birthDate;
    private String profilePhoto;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
