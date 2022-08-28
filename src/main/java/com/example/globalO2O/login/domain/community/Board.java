package com.example.globalO2O.login.domain.community;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bumsoo
 * @version 1.2, 2022.8.22
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {
    @Id
    @Column(name = "BOARD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer category;

    @Column
    private Long uid;

    @Column(length = 50)
    private String date;

    @Column
    private Integer recommend;

    @Column(length = 30)
    private String boardTitle;

    @Column(length = 500)
    private String boardContent;

    @Column(length = 20)
    private String nickname;

    @Column
    private Boolean hasImage;

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommendation> recommendations;
}
