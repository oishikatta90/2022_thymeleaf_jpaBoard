package com.lhw.thymeleaf.jpaBoard.article.domain;

import com.lhw.thymeleaf.jpaBoard.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime reg_date;
    private LocalDateTime update_date;
    private String title;
    private String body;

    @ManyToOne
    private User user;
}
