package com.lhw.thymeleaf.jpaBoard.user.controller;

import com.lhw.thymeleaf.jpaBoard.user.dao.UserRepository;
import com.lhw.thymeleaf.jpaBoard.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/jpaBoard/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("list")
    @ResponseBody
    public List<User> users() {
        return userRepository.findAll();
    }

    @RequestMapping("doJoin")
    @ResponseBody
    public String doJoin(String name, String email, String password) {

        if (name == null || name.trim().length() == 0) {
            return "이름을 입력해주세요";
        }
        name = name.trim();

        if (password == null || password.trim().length() == 0) {
            return "비밀번호를 입력해주세요";
        }
        password = password.trim();

        if (email == null || email.trim().length() == 0) {
            return "이메일을 입력해주세요";
        }
        email = email.trim();
        boolean existsByEmail = userRepository.existsByEmail(email);
        if (existsByEmail) {
            return "입력하신 이메일(%s)은 이미 사용중입니다.".formatted(email);
        }


        User user = new User();

        user.setReg_date(LocalDateTime.now());
        user.setUpdate_date(LocalDateTime.now());
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        userRepository.save(user);


        return "%d번 회원이 생성되었습니다.".formatted(user.getId());
    }

    @RequestMapping("login")
    public String showLogin(HttpSession session, Model model) {
        boolean isLogined = false;
        long loginedUserId = 0;

        if (session.getAttribute("loginedUserId") != null) {
            isLogined = true;
            loginedUserId = (long) session.getAttribute("loginedUserId");
        }

        if (isLogined) {
            model.addAttribute("msg", "이미 로그인 되었습니다.");
            model.addAttribute("historyBack", true);
        }

        return "jpaBoard/user/login";
    }

    @RequestMapping("doLogin")
    @ResponseBody
    public String doLogin(String email, String password, HttpServletRequest req, HttpServletResponse resp) {

        if (email == null || email.trim().length() == 0) {
            return "이메일을 입력해주세요";
        }
        email = email.trim();
//        User user = userRepository.findByEmail(email).orElse(null);   # 방법 1번
        Optional<User> user = userRepository.findByEmail(email);  // 방법 2번

        if (user.isEmpty()) {
            return "존재하지 않는 이메일입니다.";
        }

        if (password == null || password.trim().length() == 0) {
            return "비밀번호를 입력해주세요";
        }
        password = password.trim();
        if (!user.get().getPassword().equals(password)) {
            return "비밀번호가 일치하지 않습니다.";
        }

        HttpSession session = req.getSession();
        session.setAttribute("loginedUserId", user.get().getId());
//        Cookie cookie = new Cookie("loginedUserId", user.get().getId()+"");
//        resp.addCookie(cookie);

        return """
                <script>
                alert('%로그인 되셨습니다.');
                location.replace('article/list');
                </script>
                """;


    }
    @RequestMapping("doLogout")
    @ResponseBody
    public String doLogout(HttpSession session) {
        boolean isLogined = false;

        if (session.getAttribute("loginedUserId") != null) {
            isLogined = true;
        }

        if (!isLogined) {
            return "이미 로그아웃 되었습니다.";
        }

        session.removeAttribute("loginedUserId");

        return "로그아웃 되었습니다.";

    }
    @RequestMapping("/me")
    @ResponseBody
    public User showMe(HttpSession session) {
        boolean isLogined = false;
        long loginedUserId = 0;

        if (session.getAttribute("loginedUserId") != null){
            isLogined = true;
            loginedUserId = (long) session.getAttribute("loginedUserId");
        };


        if (!isLogined) {
            return null;
        }
        Optional<User>user = userRepository.findById(loginedUserId);

        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }
}
