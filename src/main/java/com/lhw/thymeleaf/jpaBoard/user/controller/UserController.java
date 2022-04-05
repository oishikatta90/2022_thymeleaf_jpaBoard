package com.lhw.thymeleaf.jpaBoard.user.controller;

import com.lhw.thymeleaf.jpaBoard.user.dao.UserRepository;
import com.lhw.thymeleaf.jpaBoard.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

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

    @RequestMapping("doLogin")
    @ResponseBody
    public String doLogin(String email, String password) {

        if (email == null || email.trim().length() == 0) {
            return "이메일을 입력해주세요";
        }
        email = email.trim();
        User user = userRepository.findByEmail(email).get();

        if (user == null) {
            return "존재하지 않는 이메일입니다.";
        }

        if (password == null || password.trim().length() == 0) {
            return "비밀번호를 입력해주세요";
        }
        password = password.trim();
        if (user.getPassword().equals(password) == false) {
            return "비밀번호가 일치하지 않습니다.";
        }

        return "%s님 로그인 되셨습니다.".formatted(user.getName());


    }
}
