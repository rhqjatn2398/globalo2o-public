package com.example.globalO2O.login.domain.community;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardImagePath {
    @JsonIgnore
    @Id
    private String path;

    @JoinColumn(name = "board_id")
    @ManyToOne
    private Board board;
}