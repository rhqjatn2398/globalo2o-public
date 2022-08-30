package com.example.globalO2O.login.domain.user;

import com.example.globalO2O.login.domain.exception.DuplicateMemberException;
import com.example.globalO2O.login.http.dto.AccontRequestDto;
import com.example.globalO2O.login.http.dto.AccountResponseDto;
import com.example.globalO2O.login.http.dto.PasswordDto;
import com.example.globalO2O.login.http.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AccontRequestDto signup(AccontRequestDto userDto, String email) throws DuplicateMemberException {
        if (userRepository.findOneWithAuthoritiesByLoginId(userDto.getLoginId()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        User user = User.builder()
                .loginId(userDto.getLoginId())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .nickname(userDto.getNickname())
                .email(email)
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return AccontRequestDto.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public AccontRequestDto getUserWithAuthorities(String loginId) {
        return AccontRequestDto.from(userRepository.findOneWithAuthoritiesByLoginId(loginId).orElse(null));
    }

    @Transactional(readOnly = true)
    public AccontRequestDto getMyUserWithAuthorities() {
        return AccontRequestDto.from(SecurityUtil.getCurrentLoginId().flatMap(userRepository::findOneWithAuthoritiesByLoginId).orElse(null));
    }

    public String checkDuplication(String loginId, String nickname) {
        if (userRepository.existsByLoginId(loginId)) {
            return "id";
        } else if (userRepository.existsByNickname(nickname)){
            return "nickname";
        } else {
            return "available";
        }
    }

    @Transactional
    public Boolean signOut(String loginId) {
        try {
            userRepository.deleteByLoginId(loginId);
            return true;
        } catch (Exception e){
            log.info(e.getMessage());
            return false;
        }
    }

    @Transactional
    public User resetPassword(PasswordDto passwordDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();

        user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));

        return userRepository.save(user);
    }
}
