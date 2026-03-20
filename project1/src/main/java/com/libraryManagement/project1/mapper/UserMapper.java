package com.libraryManagement.project1.mapper;

import com.libraryManagement.project1.dto.UserResponse;
import com.libraryManagement.project1.entities.User;

public class UserMapper {

    public static UserResponse toResponse(User user){

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
