package com.kravchenko.springeshop.dao;

import com.kravchenko.springeshop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
