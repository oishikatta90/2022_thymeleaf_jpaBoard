package com.lhw.thymeleaf.jpaBoard.dao;


import com.lhw.thymeleaf.jpaBoard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
