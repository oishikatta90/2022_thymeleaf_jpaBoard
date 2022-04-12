package com.lhw.thymeleaf.jpaBoard.article.controller;

import com.lhw.thymeleaf.jpaBoard.article.dao.ArticleRepository;
import com.lhw.thymeleaf.jpaBoard.article.domain.Article;
import com.lhw.thymeleaf.jpaBoard.user.dao.UserRepository;
import com.lhw.thymeleaf.jpaBoard.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/jpaBoard/article")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("list")
    public String showList(Model model) {

        List<Article> articles = articleRepository.findAll();

        model.addAttribute("articles", articles);

        return "jpaBoard/article/list";
    }
    @RequestMapping("list2")
    @ResponseBody
    public List<Article> showList2() {
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
        User user = userRepository.findById(1L).get();
        article.setUser(user);
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
