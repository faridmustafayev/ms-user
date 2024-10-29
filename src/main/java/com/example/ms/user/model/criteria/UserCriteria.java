package com.example.ms.user.model.criteria;

import com.example.ms.user.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCriteria {
    private Integer ageFrom;
    private Integer ageTo;
    private String address;
    private String firstName;
    private UserStatus status;
    private Integer age;
}