package com.example.globalO2O.login.domain.user;

import com.example.globalO2O.login.domain.community.Recommendation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @JsonIgnore
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loginId", length = 50, unique = true)
    @NotNull
    private String loginId;

    @Column(name = "name", length = 50)
    @NotNull
    private String name;

    @Column(name = "password", length = 100)
    @NotNull
    private String password;

    @Column(name = "nickname", length = 20, unique = true)
    @NotNull
    private String nickname;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

    @JsonIgnore
    @Column(name = "registration_token")
    private String registrationToken;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")}
    )
    private Set<Authority> authorities;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommendation> recommendations;
}
