package com.example.ms.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastNumber;
    private int age;
    private String email;
    private String password;
    private Date birthDate;
}
