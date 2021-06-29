package com.kravchenko.springeshop.service;

import com.kravchenko.springeshop.domain.Order;

public interface OrderService {
    void saveOrder(Order order);

    Order getOrder(Long id);
}
