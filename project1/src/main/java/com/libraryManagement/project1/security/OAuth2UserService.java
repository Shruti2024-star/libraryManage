//package com.libraryManagement.project1.security;
//
//
//import com.libraryManagement.project1.entities.*;
//import com.libraryManagement.project1.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.oauth2.client.userinfo.*;
//import org.springframework.security.oauth2.core.user.*;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class OAuth2UserService extends DefaultOAuth2UserService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest request) {
//
//        OAuth2User oauthUser = super.loadUser(request);
//
//        String email = oauthUser.getAttribute("email");
//        String name = oauthUser.getAttribute("name");
//
//        User user = userRepository.findByEmail(email).orElse(null);
//
//        if(user == null){
//
//            user = User.builder()
//                    .email(email)
//                    .name(name)
//                    .role(Role.USER)
//                    .provider(AuthProvider.GOOGLE)
//                    .build();
//
//            userRepository.save(user);
//        }
//
//        return oauthUser;
//    }
//}