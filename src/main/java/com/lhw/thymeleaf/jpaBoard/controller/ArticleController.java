package com.lhw.thymeleaf.jpaBoard.controller;

import com.lhw.thymeleaf.jpaBoard.dao.ArticleRepository;
import com.lhw.thymeleaf.jpaBoard.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping("")
    @ResponseBody
    public List<Article> returnArticle() {
        return articleRepository.findAll();

    }

    @RequestMapping("/doModify")
    @ResponseBody
    public Article doModify(long id, String title, String body) {
        Article article = articleRepository.findById(id).get();

        if (title != null) {
            article.setTitle(title);
        }

        if (body != null) {
            article.setBody(body);
        }
        articleRepository.save(article);


        return article;
    }

    @RequestMapping("/doDelete")
    @ResponseBody
    public String doDelete(long id) {
        articleRepository.deleteById(id);
//        Article article = articleRepository.findById(id).get();

//        articleRepository.delete(article);


        return "삭제완료";
    }

}
