package com.example.ms.user.controller;

import com.example.ms.user.model.criteria.PageCriteria;
import com.example.ms.user.model.criteria.UserCriteria;
import com.example.ms.user.model.request.UserRequest;
import com.example.ms.user.model.response.PageableResponse;
import com.example.ms.user.model.response.UserResponse;
import com.example.ms.user.model.update.UserUpdate;
import com.example.ms.user.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createUser(@RequestBody UserRequest userRequest) {
        userService.saveUser(userRequest);
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(NO_CONTENT)
    public void updateUser(@PathVariable Long userId, UserUpdate user) {
        userService.updateUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping
    public PageableResponse<UserResponse> getUsers(PageCriteria pageCriteria,
                                                   UserCriteria userCriteria) {
        return userService.getUsers(pageCriteria, userCriteria);
    }

    @PostMapping("/{userId}/photo")
    public String uploadUserProfilePhoto(@PathVariable Long userId, @RequestPart("photo") MultipartFile file) {
        return userService.uploadUserProfilePhoto(userId, file);
    }

}
