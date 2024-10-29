package com.example.ms.user.model.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdate {
    private String profilePhoto;
    private String firstName;
    private String lastName;
    private Date birthDate;
}
