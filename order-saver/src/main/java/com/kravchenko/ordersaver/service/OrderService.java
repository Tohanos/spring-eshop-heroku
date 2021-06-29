package com.kravchenko.ordersaver.service;

import com.kravchenko.ordersaver.domain.Order;

public interface OrderService {
    void save(Order order);
}
