package com.lhw.thymeleaf.jpaBoard.controller;

import com.lhw.thymeleaf.jpaBoard.dao.ArticleRepository;
import com.lhw.thymeleaf.jpaBoard.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
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
    @RequestMapping("/doWrite")
    @ResponseBody
    public String doWrite(String title, String body) {
        Article article = new Article();

        if (title == null || title.trim().length() == 0) {
            return "제목을 넣어주세요";
        }
        title = title.trim();

        if (body == null || body.trim().length() == 0) {
            return "내용을 넣어주세요";
        }
        body = body.trim();

        article.setTitle(title);
        article.setBody(body);
        article.setReg_date(LocalDateTime.now());
        article.setUpdate_date(LocalDateTime.now());
        article.setUserId(1);
        articleRepository.save(article);


        return "게시글이 작성 되었습니다.".formatted(article);
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

        article.setUpdate_date(LocalDateTime.now());
        articleRepository.save(article);


        return article;
    }

    @RequestMapping("/doDelete")
    @ResponseBody
    public String doDelete(long id) {
        if (articleRepository.existsById(id) == false) {
            return id + "번 게시물은 이미 삭제되었거나 존재하지 않는 게시물입니다.";
        }
        articleRepository.deleteById(id);
//        Article article = articleRepository.findById(id).get();

//        articleRepository.delete(article);


        return "삭제완료";
    }

}
