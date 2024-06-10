package com.tourist_spot_management.service.Impl;

import com.tourist_spot_management.entity.OrderEntity;
import com.tourist_spot_management.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderEntity saveOrder(OrderEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }
}
