package com.example.ms.user.mapper;

import com.example.ms.user.dao.entity.UserEntity;
import com.example.ms.user.model.request.UserRequest;
import com.example.ms.user.model.response.UserResponse;

import static com.example.ms.user.model.enums.UserStatus.ACTIVE;

public enum UserMapper {
    USER_MAPPER;

    public UserEntity buildUserEntity(UserRequest userRequest) {
        return UserEntity.builder()
                .firstName(userRequest.getFirstName())
                .status(ACTIVE)
                .lastName(userRequest.getLastNumber())
                .age(userRequest.getAge())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .birthDate(userRequest.getBirthDate())
                .build();
    }

    public UserResponse buildUserResponse(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .status(user.getStatus())
                .age(user.getAge())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .email(user.getEmail())
                .password(user.getPassword())
                .userType(user.getUserType())
                .birthDate(user.getBirthDate())
                .profilePhoto(user.getProfilePhoto())
                .build();
    }
}
