package com.kravchenko.springeshop.service;

import com.kravchenko.springeshop.config.OrderIntegrationConfig;
import com.kravchenko.springeshop.dao.OrderRepository;
import com.kravchenko.springeshop.domain.Order;
import com.kravchenko.springeshop.dto.OrderIntegrationDTO;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderIntegrationConfig integrationConfig;

    public OrderServiceImpl(OrderRepository orderRepository, OrderIntegrationConfig integrationConfig) {
        this.orderRepository = orderRepository;
        this.integrationConfig = integrationConfig;
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        sendIntegrationNotify(savedOrder);
    }

    private void sendIntegrationNotify(Order order){
        OrderIntegrationDTO dto = new OrderIntegrationDTO();
        dto.setUsername(order.getUser().getName());
        dto.setAddress(order.getAddress());
        dto.setOrderId(order.getId());
        List<OrderIntegrationDTO.OrderDetailsDTO> details = order.getDetails().stream()
                .map(OrderIntegrationDTO.OrderDetailsDTO::new).collect(Collectors.toList());
        dto.setDetails(details);

        Message<OrderIntegrationDTO> message = MessageBuilder.withPayload(dto)
                .setHeader("Content-type", "application/json")
                .build();

        integrationConfig.getOrdersChannel().send(message);
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
