package com.tourist_spot_management.repository;

import com.tourist_spot_management.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
