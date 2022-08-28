package com.example.globalO2O.login.domain.community;

import com.example.globalO2O.login.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @JsonBackReference
    @JoinColumn(name = "board_id")
    @ManyToOne
    Board board;
}