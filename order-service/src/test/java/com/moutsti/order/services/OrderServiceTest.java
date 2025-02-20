package com.moutsti.order.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.moutsti.order.entities.Order;
import com.moutsti.order.entities.Product;
import com.moutsti.order.repositories.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private OrderProcessor orderProcessor;

	@Mock
	private OrderNotifier orderNotifier;

	@InjectMocks
	private OrderService orderService;

	@Test
	void testCreateOrder() {
		Order order = new Order();
		List<Product> products = List.of(new Product(1L, "Product 1", BigDecimal.TEN));
		order.setProducts(products);

		Order processedOrder = new Order();
		processedOrder.setTotalAmount(BigDecimal.TEN);
		processedOrder.setStatus("PROCESSED");

		when(orderRepository.save(order)).thenReturn(processedOrder);

		Order createdOrder = orderService.createOrder(order);

		assertEquals("PROCESSED", createdOrder.getStatus());
		assertEquals(BigDecimal.TEN, createdOrder.getTotalAmount());
		verify(orderRepository).save(order);
		verify(orderNotifier).notify(processedOrder);
		verify(orderProcessor).process(order);
	}

	@Test
	void testGetOrderById() {
		Order order = new Order();
		when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

		Optional<Order> foundOrder = orderService.getOrderById(1L);

		assertTrue(foundOrder.isPresent());
		assertEquals(order, foundOrder.get());
	}

}
