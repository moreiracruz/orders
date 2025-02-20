package com.moutsti.order.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moutsti.order.entities.Order;
import com.moutsti.order.services.OrderService;

public class OrderControllerUnitTest {

	private MockMvc mockMvc;

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderController orderController;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
	}

	@Test
	public void testCreateOrder() throws Exception {
		Order order = new Order();
		order.setId(1L);
		order.setCustomerId("123");

		when(orderService.createOrder(any(Order.class))).thenReturn(order);

		mockMvc.perform(
				post("/orders").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(order)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.customerId").value("123"));

		verify(orderService, times(1)).createOrder(any(Order.class));
	}

	@Test
	public void testGetOrderFound() throws Exception {
		Order order = new Order();
		order.setId(1L);
		order.setCustomerId("123");

		when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));

		mockMvc.perform(get("/orders/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.customerId").value("123"));

		verify(orderService, times(1)).getOrderById(1L);
	}

	@Test
	public void testGetOrderNotFound() throws Exception {
		when(orderService.getOrderById(1L)).thenReturn(Optional.empty());

		mockMvc.perform(get("/orders/1")).andExpect(status().isNotFound());

		verify(orderService, times(1)).getOrderById(1L);
	}

}
