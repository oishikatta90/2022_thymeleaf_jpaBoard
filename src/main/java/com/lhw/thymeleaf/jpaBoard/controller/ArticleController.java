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

    @RequestMapping("1")
    @ResponseBody
    public String hello() {
        return "Hello!1321321dsadsaadad31";
    }

}
