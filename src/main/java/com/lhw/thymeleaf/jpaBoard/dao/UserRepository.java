package com.lhw.thymeleaf.jpaBoard.dao;


import com.lhw.thymeleaf.jpaBoard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
