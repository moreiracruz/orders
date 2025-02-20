package com.moutsti.order.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.moutsti.order.entities.Order;
import com.moutsti.order.entities.Product;

@Service
public class OrderProcessor {

	private BigDecimal calculateTotal(List<Product> products) {
		return products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public void process(Order order) {
		order.setTotalAmount(calculateTotal(order.getProducts()));
		order.setStatus("PROCESSED");
	}

}
