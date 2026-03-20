package com.libraryManagement.project1.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.libraryManagement.project1.dto.UserResponse;
import com.libraryManagement.project1.entities.User;
import com.libraryManagement.project1.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    
    //GET ALL USERS
    public List<UserResponse> getAllUsers(){

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .build())
                .toList();
    }
}
