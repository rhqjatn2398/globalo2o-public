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
    public ResponseEntity signup(@Valid @RequestBody AccontRequestDto userDto, @RequestHeader(name = "Authorization") String token) {
        token = token.split(" ")[1].trim();

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
    public ResponseEntity<String> resetPassword(@Valid @RequestBody PasswordDto passwordDto, @RequestHeader (name="Authorization") String token) {
        token = token.split(" ")[1].trim();

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
    public ResponseEntity<AccountResponseDto> checkDuplication(@Valid @RequestBody AccontRequestDto userDto) {
        return ResponseEntity.ok(userService.withdraw(userDto.getLoginId(), userDto.getNickname()));
    }

    @PostMapping("/auth/account/withdrawal")
    public ResponseEntity<Boolean> withdrawal(@Valid @RequestBody AccountSignInDto accountSignInDto, @AuthenticationPrincipal User user) {
        // TODO: 일치여부 확인
        return ResponseEntity.ok(userService.signOut(accountSignInDto.getLoginId()));
    }


}