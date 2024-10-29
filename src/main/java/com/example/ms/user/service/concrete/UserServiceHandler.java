package com.example.ms.user.service.concrete;

import com.example.ms.user.client.ProductClient;
import com.example.ms.user.dao.entity.UserEntity;
import com.example.ms.user.dao.repository.UserRepository;
import com.example.ms.user.exception.NotFoundException;
import com.example.ms.user.model.client.ProductCriteria;
import com.example.ms.user.model.client.ProductResponse;
import com.example.ms.user.model.criteria.PageCriteria;
import com.example.ms.user.model.criteria.UserCriteria;
import com.example.ms.user.model.request.UserRequest;
import com.example.ms.user.model.response.PageableResponse;
import com.example.ms.user.model.response.UserResponse;
import com.example.ms.user.model.update.UserUpdate;
import com.example.ms.user.service.abstraction.UserService;
import com.example.ms.user.service.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static com.example.ms.user.exception.ExceptionConstants.USER_NOT_FOUND;
import static com.example.ms.user.mapper.PageableUserResponseMapper.PAGEABLE_USER_RESPONSE_MAPPER;
import static com.example.ms.user.mapper.UserMapper.USER_MAPPER;
import static com.example.ms.user.model.enums.UserStatus.DELETED;
import static com.example.ms.user.model.enums.UserStatus.IN_PROGRESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceHandler implements UserService {
    private final UserRepository userRepository;
    private final ProductClient<ProductResponse> productClient;

    @Override
    public void saveUser(UserRequest userRequest) {
        log.info("ActionLog.saveUser.start with request: {}", userRequest);
        UserEntity user = USER_MAPPER.buildUserEntity(userRequest);
        userRepository.save(user);
        log.info("ActionLog.saveUser.success with request: {}", userRequest);
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("ActionLog.deleteUser.start with userId: {}", userId);
        UserEntity user = fetchUserIfExist(userId);
        user.setStatus(DELETED);
        userRepository.save(user);
        log.info("ActionLog.deleteUser.success with userId: {}", userId);
    }

    @Override
    public void updateUser(Long userId, UserUpdate userUpdate) {
        log.info("ActionLog.updateUserAddress.start with userId: {}", userId);
        UserEntity user = fetchUserIfExist(userId);
        updateUserFields(user, userUpdate);
        userRepository.save(user);
        log.info("ActionLog.updateUserAddress.success with userId: {}", userId);
    }

    public UserResponse getUser(Long userId) {
        log.info("ActionLog.getUser.start with userId: {}", userId);
        UserEntity user = fetchUserIfExist(userId);
        log.info("ActionLog.getUser.success with userId: {}", userId);
        return USER_MAPPER.buildUserResponse(user);
    }

    @Override
    public PageableResponse<UserResponse> getUsers(PageCriteria pageCriteria, UserCriteria userCriteria) {
        List<Sort.Order> setOrders = List.of(
                new Sort.Order(Sort.Direction.DESC, "id"),
                new Sort.Order(Sort.Direction.ASC, "createdAt"));

        log.info("ActionLog.getUsers.start");
        var usersPage = userRepository.findAll(
                new UserSpecification(userCriteria),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount(), Sort.by(setOrders).descending()
                        .and(Sort.by("createdAt").descending())));
        log.info("ActionLog.getUsers.success");

        return PAGEABLE_USER_RESPONSE_MAPPER.buildPageableResponse(usersPage);
    }

    @Override
    public PageableResponse<ProductResponse> getProducts(PageCriteria pageCriteria,
                                                         ProductCriteria productCriteria) {
        log.info("ActionLog.getProducts.start");
        return productClient.getProducts(pageCriteria, productCriteria);
    }

    @Override
    public String uploadUserProfilePhoto(Long userId, MultipartFile file) {
        log.info("ActionLog.uploadUserProfilePhoto.start with userId: {}", userId);
        try {
            String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
            updateUserProfilePhoto(userId, base64Image);
            return "Profile photo uploaded successfully!";
        } catch (IOException exception) {
            return "Error uploading file: " + exception.getMessage();
        }
    }


    public void updateUserProfilePhoto(Long userId, String base64Image) {
        log.info("ActionLog.updateUserProfilePhoto.start with userId: {}", userId);
        UserEntity user = fetchUserIfExist(userId);
        user.setProfilePhoto(base64Image);
        userRepository.save(user);
        log.info("ActionLog.updateUserProfilePhoto.success with userId: {}", userId);
    }

    private UserEntity fetchUserIfExist(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage()));
    }

    private void updateUserFields(UserEntity user, UserUpdate userUpdate) {
        user.setProfilePhoto(userUpdate.getProfilePhoto());
        user.setFirstName(userUpdate.getFirstName());
        user.setLastName(userUpdate.getLastName());
        user.setBirthDate(userUpdate.getBirthDate());
        user.setStatus(IN_PROGRESS);
    }

}
