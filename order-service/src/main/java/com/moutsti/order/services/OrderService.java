package com.moutsti.order.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moutsti.order.entities.Order;
import com.moutsti.order.repositories.OrderRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderProcessor orderProcessor;
	private final OrderNotifier orderNotifier;

	@Autowired
	public OrderService(OrderRepository orderRepository, OrderProcessor orderProcessor, OrderNotifier orderNotifier) {
		this.orderRepository = orderRepository;
		this.orderProcessor = orderProcessor;
		this.orderNotifier = orderNotifier;
	}

	public Order createOrder(Order order) {
		orderProcessor.process(order);
		Order savedOrder = orderRepository.save(order);
		orderNotifier.notify(savedOrder);
		return savedOrder;
	}

	public Optional<Order> getOrderById(Long id) {
		return orderRepository.findById(id);
	}

}
