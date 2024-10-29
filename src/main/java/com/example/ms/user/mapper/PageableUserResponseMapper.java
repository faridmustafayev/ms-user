package com.example.ms.user.mapper;

import com.example.ms.user.dao.entity.UserEntity;
import com.example.ms.user.model.response.PageableResponse;
import com.example.ms.user.model.response.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.example.ms.user.mapper.UserMapper.USER_MAPPER;

public enum PageableUserResponseMapper {
    PAGEABLE_USER_RESPONSE_MAPPER;

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
}
