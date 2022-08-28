package com.example.globalO2O.login.domain.community;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

/**
 * @author Bumsoo
 * @version 1.1, 2022.7.22
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NestedComment {
    @Id
    @Column(name = "NESTED_COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @Column
    private String date;

    @Column
    private String content;

    @Column
    private Long uid;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    @JsonBackReference
    Comment comment;
}