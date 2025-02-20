package com.moutsti.order.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.moutsti.order.entities.Order;
import com.moutsti.order.entities.Product;

@ExtendWith(MockitoExtension.class)
public class OrderProcessorTest {

	private OrderProcessor orderProcessor = new OrderProcessor();

	@Test
	void testProcessOrder() {
		Order order = new Order();
		List<Product> products = List.of(new Product(1L, "Product 1", BigDecimal.TEN),
				new Product(2L, "Product 2", new BigDecimal("20")));
		order.setProducts(products);

		orderProcessor.process(order);

		assertEquals("PROCESSED", order.getStatus());
		assertEquals(new BigDecimal("30"), order.getTotalAmount());
	}

	@Test
	void testProcessOrderWithNoProducts() {
		Order order = new Order();
		order.setProducts(new ArrayList<>());
		orderProcessor.process(order);

		assertEquals("PROCESSED", order.getStatus());
		assertEquals(BigDecimal.ZERO, order.getTotalAmount());
	}

}
