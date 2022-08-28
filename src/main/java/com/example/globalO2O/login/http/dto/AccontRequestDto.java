package com.example.globalO2O.login.http.dto;

import com.example.globalO2O.login.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccontRequestDto {
    @NotNull
    private String loginId; // Unique
    @NotNull
    private String name;
    @NotNull
    private String nickname; // Unique
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private String password;

    private Set<AuthorityDto> authorityDtoSet;

    public static AccontRequestDto from(User user) {
        if (user == null) return null;

        return AccontRequestDto.builder()
                .loginId(user.getLoginId())
                .nickname(user.getNickname())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet())).build();
    }
}
