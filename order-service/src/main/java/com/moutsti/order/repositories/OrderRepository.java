package com.moutsti.order.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moutsti.order.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	Optional<Order> findByCustomerIdAndTotalAmount(String customerId, BigDecimal totalAmount);

	List<Order> findByStatus(String status);

}
