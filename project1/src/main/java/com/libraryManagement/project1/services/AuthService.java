package com.libraryManagement.project1.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.libraryManagement.project1.dto.AuthResponse;
import com.libraryManagement.project1.dto.LoginRequest;
import com.libraryManagement.project1.dto.SignupRequest;
import com.libraryManagement.project1.entities.AuthProvider;
import com.libraryManagement.project1.entities.Role;
import com.libraryManagement.project1.entities.User;
import com.libraryManagement.project1.exceptions.ConflictException;
import com.libraryManagement.project1.exceptions.ResourceNotFoundException;
import com.libraryManagement.project1.exceptions.UnauthorizedException;
import com.libraryManagement.project1.mapper.UserMapper;
import com.libraryManagement.project1.repository.UserRepository;
import com.libraryManagement.project1.security.CustomUserDetails;
import com.libraryManagement.project1.security.JwtService;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public AuthResponse signup(SignupRequest request){
    	
        // check if email already exists
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new ConflictException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .provider(AuthProvider.LOCAL)
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(
                new CustomUserDetails(user));

        return AuthResponse.builder()
                .token(token)
                .user(UserMapper.toResponse(user))
                .build();
    }
    
    public AuthResponse login(LoginRequest request){

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

        } catch (BadCredentialsException ex) {
            throw new UnauthorizedException("Invalid email or password");
        }

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        String token = jwtService.generateToken(
                new CustomUserDetails(user));

        return AuthResponse.builder()
                .token(token)
                .user(UserMapper.toResponse(user))
              
                .build();
    }
}