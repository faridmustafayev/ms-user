package com.example.ms.user.mapper;

import com.example.ms.user.dao.entity.UserEntity;
import com.example.ms.user.model.request.CreateUserRequest;
import com.example.ms.user.model.response.PageableResponse;
import com.example.ms.user.model.response.UserResponse;
import com.example.ms.user.model.update.UpdateUserRequest;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.example.ms.user.model.enums.UserStatus.ACTIVE;
import static com.example.ms.user.model.enums.UserStatus.IN_PROGRESS;

public enum UserMapper {
    USER_MAPPER;

    public PageableResponse<UserResponse> buildPageableResponse(Page<UserEntity> page) {
        List<UserResponse> userResponses = page.stream()
                .map(USER_MAPPER::buildUserResponse)
                .toList();

        return PageableResponse.<UserResponse>builder()
                .content(userResponses)
                .hasNext(page.hasNext())
                .lastPageNumber(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }

    public UserEntity buildUserEntity(CreateUserRequest createUserRequest) {
        return UserEntity.builder()
                .status(ACTIVE)
                .age(createUserRequest.getAge())
                .username(createUserRequest.getUsername())
                .email(createUserRequest.getEmail())
                .profilePhoto(createUserRequest.getProfilePhoto())
                .password(createUserRequest.getPassword())
                .address(createUserRequest.getAddress())
                .birthDate(createUserRequest.getBirthDate())
                .build();
    }

    public UserResponse buildUserResponse(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .status(user.getStatus())
                .age(user.getAge())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .email(user.getEmail())
                .address(user.getAddress())
                .password(user.getPassword())
                .birthDate(user.getBirthDate())
                .profilePhoto(user.getProfilePhoto())
                .build();
    }

    public void updateUserFields(UserEntity user, UpdateUserRequest updateUserRequest) {
        user.setProfilePhoto(updateUserRequest.getProfilePhoto());
        user.setUsername(updateUserRequest.getUsername());
        user.setBirthDate(updateUserRequest.getBirthDate());
        user.setStatus(IN_PROGRESS);
    }

}
