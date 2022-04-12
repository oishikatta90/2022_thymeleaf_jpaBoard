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

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    @RequestMapping("/write")
    public String showWrite() {
        return "jpaBoard/article/write";
    }

    @RequestMapping("/detail")
    public String showDetail(Model model, long id) {
        Optional<Article> opArticle = articleRepository.findById(id);
        Article article = opArticle.get();

        model.addAttribute("article", article);

        return "jpaBoard/article/detail";
    }

    @RequestMapping("/modify")
    public String showModify(Model model, long id) {
        Optional<Article> opArticle = articleRepository.findById(id);
        Article article = opArticle.get();

        model.addAttribute("article", article);

        return "jpaBoard/article/modify";
    }

    @RequestMapping("/doWrite")
    @ResponseBody
    public String doWrite(String title, String body, HttpSession session) {
        boolean isLogined = false;
        long loginedUserId = 0;
        if (session.getAttribute("loginedUserId") != null) {
            isLogined = true;
            loginedUserId = (long)session.getAttribute("loginedUserId");

        } else {
            return """
                <script>
                alert('로그인을 해주세요!');
                history.back();
                </script>
                """;
        }

        if (title == null || title.trim().length() == 0) {
            return "제목을 넣어주세요";
        }
        title = title.trim();

        if (body == null || body.trim().length() == 0) {
            return "내용을 넣어주세요";
        }
        body = body.trim();

        Article article = new Article();
        article.setReg_date(LocalDateTime.now());
        article.setUpdate_date(LocalDateTime.now());
        article.setTitle(title);
        article.setBody(body);
        User user = userRepository.findById(loginedUserId).get();
        article.setUser(user);
        articleRepository.save(article);


        return """
                <script>
                alert('%d번 게시물이 생성되었습니다.');
                location.replace('list');
                </script>
                """.formatted(article.getId());
    }

    @RequestMapping("/doModify")
    @ResponseBody
    public String doModify(long id, String title, String body) {
        Article article = articleRepository.findById(id).get();

        if (title != null) {
            article.setTitle(title);
        }

        if (body != null) {
            article.setBody(body);
        }

        article.setUpdate_date(LocalDateTime.now());
        articleRepository.save(article);


        return """
                <script>
                alert('%d번 게시물이 수정되었습니다.');
                location.replace('detail?id=%d');
                </script>
                """.formatted(article.getId(), article.getId());
    }

    @RequestMapping("/doDelete")
    @ResponseBody
    public String doDelete(long id) {
        Article article = articleRepository.findById(id).get();

        if (!articleRepository.existsById(id)) {
            return """
                    <script>
                    alert('%d번 게시물은 이미 삭제되었거나 존재하지 않습니다.');
                    history.back();
                    </script>
                    """.formatted(id);
        }
        articleRepository.deleteById(id);
        return """
                <script>
                alert('%d번 게시물이 삭제 되었습니다.');
                location.replace('list');
                </script>
                """.formatted(article.getId());
    }

}
