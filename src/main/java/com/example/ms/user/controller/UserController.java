package com.example.ms.user.controller;

import com.example.ms.user.model.criteria.PageCriteria;
import com.example.ms.user.model.criteria.UserCriteria;
import com.example.ms.user.model.request.CreateUserRequest;
import com.example.ms.user.model.response.PageableResponse;
import com.example.ms.user.model.response.UserResponse;
import com.example.ms.user.model.update.UpdateUserRequest;
import com.example.ms.user.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("@authServiceHandler.verify(#accessToken)")
    @PostMapping
    @ResponseStatus(CREATED)
    public void createUser(@RequestHeader(AUTHORIZATION) String accessToken, @RequestBody CreateUserRequest createUserRequest) {
        userService.saveUser(createUserRequest);
    }

    @PreAuthorize("@authServiceHandler.verify(#accessToken)")
    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateUser(@RequestHeader(AUTHORIZATION) String accessToken, @PathVariable Long id, @RequestBody UpdateUserRequest userRequest) {
        userService.updateUser(id, userRequest);
    }

    @PreAuthorize("@authServiceHandler.verify(#accessToken)")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUser(@RequestHeader(AUTHORIZATION) String accessToken, @PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PreAuthorize("@authServiceHandler.verify(#accessToken)")
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public UserResponse getUser(@RequestHeader(AUTHORIZATION) String accessToken, @PathVariable Long id) {
        return userService.getUser(id);
    }

    @PreAuthorize("@authServiceHandler.verify(#accessToken)")
    @GetMapping
    public PageableResponse<UserResponse> getUsers(@RequestHeader(AUTHORIZATION) String accessToken,
                                                   PageCriteria pageCriteria,
                                                   UserCriteria userCriteria) {
        return userService.getUsers(pageCriteria, userCriteria);
    }

    @PreAuthorize("@authServiceHandler.verify(#accessToken)")
    @PatchMapping("/{id}/photo")
    @ResponseStatus(NO_CONTENT)
    public void uploadUserProfilePhoto(@RequestHeader(AUTHORIZATION) String accessToken,
                                       @PathVariable Long id,
                                       @RequestPart("photo") MultipartFile file) {
        userService.uploadUserProfilePhoto(id, file);
    }

}
