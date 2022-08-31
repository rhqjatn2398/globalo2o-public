package com.example.globalO2O.login.http.user;

import com.example.globalO2O.login.domain.community.MailService;
import com.example.globalO2O.login.domain.user.User;
import com.example.globalO2O.login.domain.user.UserRepository;
import com.example.globalO2O.login.http.dto.*;
import com.example.globalO2O.login.http.jwt.JwtFilter;
import com.example.globalO2O.login.http.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 토큰을 발급하는 Controller
 * @author Bumsoo
 */

@Slf4j
@RestController
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final Map<String, String> emailAuthCodeMap = new HashMap<>();

    @Autowired
    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserRepository userRepository, MailService mailService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @PostMapping("/account/authenticate")
    public ResponseEntity authorize(@Valid @RequestBody AccountSignInDto accountSignInDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(accountSignInDto.getLoginId(), accountSignInDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        User user = userRepository.findOneWithAuthoritiesByLoginId(accountSignInDto.getLoginId()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 아이디와 비밀번호가 일치하지 않습니다.");
        }

        return new ResponseEntity<>(new TokenDto(user.getId(), user.getLoginId(), user.getNickname(), user.getName(), user.getEmail(), jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/account/email")
    public ResponseEntity<EmailTokenDto> sendEmailAuthCode(@Valid @RequestBody EmailRequestDto emailRequestDto) {
        sendAuthCode(emailRequestDto.getEmail());

        return ResponseEntity.ok(new EmailTokenDto(tokenProvider.createEmailToken(emailRequestDto.getEmail(), false)));
    }

    @PostMapping("/email-auth/account/email/code")
    public ResponseEntity validateAuthCode(@Valid @RequestBody CodeDto codeDto, HttpServletRequest request) {
        String token = getAuthorizationToken(request);

        tokenProvider.validateToken(token);

        String email = tokenProvider.parseToken(token).getSubject();

        log.info("email: {}", email);
        log.info("codeDto: {}", codeDto.getCode());
        log.info("map: {}", emailAuthCodeMap.get(email));

        if (!emailAuthCodeMap.containsKey(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("먼저 인증번호 발송을 수행해주세요.");
        }

        if (emailAuthCodeMap.get(email).equals(codeDto.getCode())) {
            emailAuthCodeMap.remove(email);
            return ResponseEntity.ok(new EmailTokenDto(tokenProvider.createEmailToken(email, true)));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증코드가 일치하지 않습니다.");
    }

    @PostMapping("/account/password/email")
    public ResponseEntity identifyByEmail(@Valid @RequestBody EmailRequestDto emailRequestDto) {
        if (userRepository.findByEmail(emailRequestDto.getEmail()).isEmpty()) {
            log.error("해당 이메일주소로 등록된 사용자가 존재하지 않습니다.");
            return ResponseEntity.badRequest().body("해당 이메일주소로 등록된 사용자가 존재하지 않습니다.");
        }

        sendAuthCode(emailRequestDto.getEmail());

        return ResponseEntity.ok(new EmailTokenDto(tokenProvider.createEmailToken(emailRequestDto.getEmail(), false)));
    }

    @PostMapping("/email-auth/account/password/email/code")
    public ResponseEntity validateAuthCodeForPassword(@Valid @RequestBody CodeDto codeDto, HttpServletRequest request) {
        String token = getAuthorizationToken(request);

        tokenProvider.validateToken(token);

        String email = tokenProvider.parseToken(token).getSubject();

        if (!emailAuthCodeMap.containsKey(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("먼저 인증번호 발송을 수행해주세요.");
        }

        if (emailAuthCodeMap.get(email).equals(codeDto.getCode())) {
            emailAuthCodeMap.remove(email);
            return ResponseEntity.ok(new EmailTokenDto(tokenProvider.createEmailToken(email, true)));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증코드가 일치하지 않습니다.");
    }

    private void sendAuthCode(String email) {
        String authCode = mailService.sendMail(email, "LinkOf 인증메일입니다.");

        emailAuthCodeMap.put(email, authCode);

        log.info("emailAuthCodeMap.get(email): {}", emailAuthCodeMap.get(email));
    }

    private String getAuthorizationToken(HttpServletRequest request) {
        String headerValue = request.getHeader("Authorization");
        return headerValue.substring(headerValue.indexOf(' ') + 1);
    }
}