package com.example.ms.user.service.abstraction;

import com.example.ms.user.model.client.ProductCriteria;
import com.example.ms.user.model.client.ProductResponse;
import com.example.ms.user.model.criteria.PageCriteria;
import com.example.ms.user.model.criteria.UserCriteria;
import com.example.ms.user.model.request.UserRequest;
import com.example.ms.user.model.response.PageableResponse;
import com.example.ms.user.model.response.UserResponse;
import com.example.ms.user.model.update.UserUpdate;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    void saveUser(UserRequest userRequest);

    void deleteUser(Long userId);

    void updateUser(Long userId, UserUpdate userUpdate);

    UserResponse getUser(Long userId);

    PageableResponse<UserResponse> getUsers(PageCriteria pageCriteria, UserCriteria userCriteria);

    PageableResponse<ProductResponse> getProducts(PageCriteria pageCriteria, ProductCriteria productCriteria);

    String uploadUserProfilePhoto(Long userId, MultipartFile file);
}
