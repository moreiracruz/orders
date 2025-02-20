package com.moutsti.order.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moutsti.order.entities.Order;

@Service
public class RabbitOrderNotifier implements OrderNotifier {

	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public RabbitOrderNotifier(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void notify(Order order) {
		rabbitTemplate.convertAndSend("orderQueue", order);
	}

}
