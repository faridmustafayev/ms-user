package com.example.ms.user.service.abstraction;

import com.example.ms.user.model.criteria.PageCriteria;
import com.example.ms.user.model.criteria.UserCriteria;
import com.example.ms.user.model.request.CreateUserRequest;
import com.example.ms.user.model.response.PageableResponse;
import com.example.ms.user.model.response.UserResponse;
import com.example.ms.user.model.update.UpdateUserRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    void saveUser(CreateUserRequest createUserRequest);

    void deleteUser(Long userId);

    void updateUser(Long userId, UpdateUserRequest updateUserRequest);

    UserResponse getUser(Long userId);

    PageableResponse<UserResponse> getUsers(PageCriteria pageCriteria, UserCriteria userCriteria);

    void uploadUserProfilePhoto(Long userId, MultipartFile file);
}
