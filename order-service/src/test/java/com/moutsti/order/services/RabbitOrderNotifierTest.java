package com.moutsti.order.services;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.moutsti.order.entities.Order;

@ExtendWith(MockitoExtension.class)
public class RabbitOrderNotifierTest {

	@Mock
	private RabbitTemplate rabbitTemplate;

	@InjectMocks
	private RabbitOrderNotifier notifier;

	@Test
	void testNotify() {
		Order order = new Order();

		notifier.notify(order);

		verify(rabbitTemplate).convertAndSend("orderQueue", order);
	}

}
