package com.example.ms.user.service.concrete;

import com.example.ms.user.aop.annotation.Log;
import com.example.ms.user.dao.entity.UserEntity;
import com.example.ms.user.dao.repository.UserRepository;
import com.example.ms.user.exception.NotFoundException;
import com.example.ms.user.model.criteria.PageCriteria;
import com.example.ms.user.model.criteria.UserCriteria;
import com.example.ms.user.model.request.CreateUserRequest;
import com.example.ms.user.model.response.PageableResponse;
import com.example.ms.user.model.response.UserResponse;
import com.example.ms.user.model.update.UpdateUserRequest;
import com.example.ms.user.service.abstraction.UserService;
import com.example.ms.user.service.specification.UserSpecification;
import com.example.ms.user.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

import static com.example.ms.user.exception.ExceptionConstants.USER_NOT_FOUND;
import static com.example.ms.user.mapper.UserMapper.USER_MAPPER;
import static com.example.ms.user.model.enums.UserStatus.DELETED;

@Log
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceHandler implements UserService {
    private final UserRepository userRepository;

    @Override
    public void saveUser(CreateUserRequest createUserRequest) {
        var user = USER_MAPPER.buildUserEntity(createUserRequest);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        var user = fetchUserIfExist(userId);
        user.setStatus(DELETED);
        userRepository.save(user);
    }

    @Override
    public void updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        var user = fetchUserIfExist(userId);
        USER_MAPPER.updateUserFields(user, updateUserRequest);
        userRepository.save(user);
    }

    @Override
    public UserResponse getUser(Long userId) {
        var user = fetchUserIfExist(userId);
        return USER_MAPPER.buildUserResponse(user);
    }

    @Override
    public PageableResponse<UserResponse> getUsers(PageCriteria pageCriteria, UserCriteria userCriteria) {

        Sort sort = Sort.by(Sort.Order.desc("id"));

        var usersPage = userRepository.findAll(
                UserSpecification.of(userCriteria),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount(), sort));

        return USER_MAPPER.buildPageableResponse(usersPage);
    }

    @Override
    public void uploadUserProfilePhoto(Long userId, MultipartFile file) {
        var base64Image = new FileUtil(Base64.getEncoder()).encodeToBase64(file);

        if (base64Image != null) {
            updateUserProfilePhoto(userId, base64Image);
            log.info("Profile photo uploaded successfully!");
        } else {
            log.error("Failed to upload profile photo due to encoding error.");
        }
    }

    private void updateUserProfilePhoto(Long userId, String base64Image) {
        var user = fetchUserIfExist(userId);
        user.setProfilePhoto(base64Image);
        userRepository.save(user);
    }

    private UserEntity fetchUserIfExist(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage()));
    }

}
