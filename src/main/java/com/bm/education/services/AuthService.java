package com.bm.education.services;

import com.bm.education.dto.auth.AuthRequest;
import com.bm.education.dto.auth.AuthResponse;
import com.bm.education.dto.auth.RefreshTokenRequest;
import com.bm.education.dto.auth.RegisterRequest;
import com.bm.education.models.RefreshToken;
import com.bm.education.models.User;
import com.bm.education.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    @Transactional
    public AuthResponse login(@NotNull AuthRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.findByUsername(request.getUsername());
        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        AuthResponse response = AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .user(userService.toUserDto(user))
                .expiresIn(jwtService.getJwtExpiration())
                .build();

        return response;
    }

    @Transactional
    public AuthResponse refreshToken(@NotNull RefreshTokenRequest request) {
        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    // The old refresh token is valid, now rotate it.
                    // Delete the old one.
                    refreshTokenService.deleteByToken(request.getRefreshToken());

                    // Create new tokens
                    String newAccessToken = jwtService.generateToken(user);
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getId());

                    AuthResponse response = AuthResponse.builder()
                            .accessToken(newAccessToken)
                            .refreshToken(newRefreshToken.getToken())
                            .user(userService.toUserDto(user))
                            .expiresIn(jwtService.getJwtExpiration())
                            .build();
                    return response;
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        User savedUser = userService.registerNewUser(request);

        String accessToken = jwtService.generateToken(savedUser);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(savedUser.getId());

        AuthResponse response = AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .user(userService.toUserDto(savedUser))
                .expiresIn(jwtService.getJwtExpiration())
                .build();

        return response;
    }
}
