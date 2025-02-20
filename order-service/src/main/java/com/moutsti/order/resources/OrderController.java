package com.moutsti.order.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moutsti.order.entities.Order;
import com.moutsti.order.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order Controller", description = "Endpoints para gerenciamento de pedidos")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	@Operation(summary = "Criar um pedido", description = "Recebe um pedido e o processa")
	@ApiResponse(responseCode = "200", description = "Pedido criado com sucesso")
	@ApiResponse(responseCode = "400", description = "Pedido inválido")
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {
		Order savedOrder = orderService.createOrder(order);
		return ResponseEntity.ok(savedOrder);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obter um pedido por ID", description = "Retorna os detalhes de um pedido específico")
	@ApiResponse(responseCode = "200", description = "Pedido encontrado")
	@ApiResponse(responseCode = "404", description = "Pedido não encontrado")
	public ResponseEntity<Order> getOrder(@PathVariable Long id) {
		return orderService.getOrderById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

}
