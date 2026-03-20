package com.libraryManagement.project1.config;

import com.libraryManagement.project1.entities.Role;
import com.libraryManagement.project1.entities.User;
import com.libraryManagement.project1.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner preloadUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            // Check if users already exist to avoid duplicates
        	System.out.println("DataInitializer running");
            if (userRepository.count() == 0) {

                // ADMIN
                User admin = User.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(admin);

                // LIBRARIAN
                User librarian = User.builder()
                        .email("librarian@gmail.com")
                        .password(passwordEncoder.encode("lib123"))
                        .role(Role.LIBRARIAN)
                        .build();
                userRepository.save(librarian);

                // USER
                User user = User.builder()
                        .email("user@gmail.com")
                        .password(passwordEncoder.encode("user123"))
                        .role(Role.USER)
                        .build();
                userRepository.save(user);

                System.out.println("✅ Default users created: ADMIN, LIBRARIAN, USER");
            }
        };
    }
}