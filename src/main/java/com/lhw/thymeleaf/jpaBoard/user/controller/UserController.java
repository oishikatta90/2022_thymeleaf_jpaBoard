package com.lhw.thymeleaf.jpaBoard.user.controller;

import com.lhw.thymeleaf.jpaBoard.user.dao.UserRepository;
import com.lhw.thymeleaf.jpaBoard.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/jpaBoard")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("")
    @ResponseBody
    public List<User> users() {
        return userRepository.findAll();
    }
}
