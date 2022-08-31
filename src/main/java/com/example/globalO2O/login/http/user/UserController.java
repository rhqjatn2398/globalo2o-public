package com.example.globalO2O.login.http.user;

import com.example.globalO2O.login.domain.user.UserService;
import com.example.globalO2O.login.http.dto.*;
import com.example.globalO2O.login.http.jwt.TokenProvider;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Autowired
    public UserController(UserService userService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/email-auth/account/sign_up")
    public ResponseEntity signup(@Valid @RequestBody AccountRequestDto userDto, HttpServletRequest request) {
        String token = getAuthorizationToken(request);

        if (!tokenProvider.validateToken(token)) {
            return ResponseEntity.badRequest().body("유효하지 않은 토큰입니다.");
        }

        Claims claims = tokenProvider.parseToken(token);

        if (!(Boolean)claims.get("isAuthenticated")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자입니다.");
        }

        try {
            userService.signup(userDto, claims.getSubject());
            return ResponseEntity.ok(new AccountResponseDto("success"));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(new AccountResponseDto("failed"));
        }
    }

    @PostMapping("/email-auth/password/reset")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody PasswordDto passwordDto, HttpServletRequest request) {
        String token = getAuthorizationToken(request);

        if (!tokenProvider.validateToken(token)) {
            return ResponseEntity.badRequest().body("유효하지 않은 토큰입니다.");
        }

        Claims claims = tokenProvider.parseToken(token);

        if (!(Boolean)claims.get("isAuthenticated")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자입니다.");
        }

        userService.resetPassword(passwordDto, claims.getSubject());

        return ResponseEntity.ok("패스워드변경이 완료되었습니다.");
    }

    @PostMapping("/account/duplication")
    public ResponseEntity<AccountResponseDto> checkNicknameDuplication(@Valid @RequestBody AccountRequestDto userDto) {
        String result = userService.checkDuplication(userDto.getNickname());
        return ResponseEntity.ok(new AccountResponseDto(result));
    }

    @PostMapping("/auth/account/withdrawal")
    public ResponseEntity<Boolean> withdrawal(@Valid @RequestBody AccountSignInDto accountSignInDto, @AuthenticationPrincipal User user) {
        // TODO: 일치여부 확인 해야 하나? 안해도 되지 않나?
        return ResponseEntity.ok(userService.signOut(accountSignInDto.getLoginId()));
    }

    @PostMapping("/auth/account/update")
    public ResponseEntity update(@Valid @RequestBody AccountRequestDto userDto, @AuthenticationPrincipal User user) {
        String result = userService.checkDuplication(userDto.getNickname());
        if (result.equals("available")) {
            userService.resetNameAndNickname(user.getUsername(), userDto.getName(), userDto.getNickname());
        } else {
            userService.resetName(user.getUsername(), userDto.getName());
        }
        return ResponseEntity.ok("success");
    }

    private String getAuthorizationToken(HttpServletRequest request) {
        String headerValue = request.getHeader("Authorization");
        return headerValue.substring(headerValue.indexOf(' ') + 1);
    }
}