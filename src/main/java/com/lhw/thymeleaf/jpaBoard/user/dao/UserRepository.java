package com.lhw.thymeleaf.jpaBoard.user.dao;


import com.lhw.thymeleaf.jpaBoard.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
