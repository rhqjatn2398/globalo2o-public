package com.example.globalO2O.login.domain.community;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bumsoo
 * @version 1.1, 2022.6.4
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long uid;

    @Column(length = 100)
    private String content;

    @Column(length = 30)
    private String date;

    @Column(length = 20)
    private String nickname;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @JsonBackReference
    private Board board;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NestedComment> nestedComments;
}