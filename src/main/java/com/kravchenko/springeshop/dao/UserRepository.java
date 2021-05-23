package com.kravchenko.springeshop.dao;

import com.kravchenko.springeshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByName(String name);
}
