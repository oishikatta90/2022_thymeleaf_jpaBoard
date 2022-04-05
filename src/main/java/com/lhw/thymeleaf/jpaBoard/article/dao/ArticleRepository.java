package com.lhw.thymeleaf.jpaBoard.article.dao;


import com.lhw.thymeleaf.jpaBoard.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
