//package com.libraryManagement.project1.security;
//
//import jakarta.servlet.http.*;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import com.libraryManagement.project1.entities.User;
//import com.libraryManagement.project1.repository.UserRepository;
//
//import java.io.IOException;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
//
//    private final JwtService jwtService;
//    private final UserRepository userRepository;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication)
//            throws IOException {
//
//        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
//
//        String email = oauthUser.getAttribute("email");
//
//        // ✅ Fetch user from DB
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // ✅ GET PERMISSIONS FROM ROLE
//        List<SimpleGrantedAuthority> authorities =
//                user.getRole().getPermissions().stream()
//                        .map(permission ->
//                                new SimpleGrantedAuthority(permission.name()))
//                        .toList();
//
//        // ✅ Create UserDetails WITH authorities
//        org.springframework.security.core.userdetails.User userDetails =
//                new org.springframework.security.core.userdetails.User(
//                        user.getEmail(),
//                        "",
//                        authorities
//                );
//
//        // ✅ Generate JWT
//        String token = jwtService.generateToken(userDetails);
//
//        // ✅ Redirect to frontend with full data
//        String redirectUrl = "http://localhost:5173/oauth-success"
//                + "?token=" + token
//                + "&id=" + user.getId()
//                + "&name=" + user.getName()
//                + "&role=" + user.getRole().name();
//
//        response.sendRedirect(redirectUrl);
//    }
//}