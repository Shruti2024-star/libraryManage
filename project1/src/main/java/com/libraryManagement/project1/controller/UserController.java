package com.libraryManagement.project1.controller;


import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libraryManagement.project1.dto.UserResponse;
import com.libraryManagement.project1.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('USER_ALLUSERS')")
    public List<UserResponse> getAllUsers(){

        return userService.getAllUsers();
    }
}