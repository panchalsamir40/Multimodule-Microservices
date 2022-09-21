package com.orderservice.service;

import com.orderservice.dto.InventoryResponse;
import com.orderservice.dto.OrderLineItemsDto;
import com.orderservice.dto.OrderRequest;
import com.orderservice.dto.OrderResponse;
import com.orderservice.model.Order;
import com.orderservice.model.OrderLineItems;
import com.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
//@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;

    @Autowired
    public OrderService(OrderRepository orderRepository, WebClient.Builder webClient) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
    }

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtosList().stream().map(this::mapToDto).toList();
        order.setOrderLineItems(orderLineItems);

        List<String> skuCodes = order.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).toList();
        InventoryResponse[] inventoryResponses = webClient.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
        if (allProductInStock) {
            orderRepository.save(order);
            return "Order Placed successfully. ";
        } else {
            throw new IllegalStateException("Product is not in stock. ");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        OrderLineItems build = OrderLineItems.builder().price(orderLineItemsDto.getPrice()).quantity(orderLineItemsDto.getQuantity()).skuCode(orderLineItemsDto.getSkuCode()).build();
        return build;
    }

    public List<OrderResponse> getOrderList() {
        List<Order> findOrders = orderRepository.findAll();
        List<OrderResponse> orderResponses = findOrders.stream().map(this::mapToOrder).toList();
        return orderResponses;
    }

    private OrderResponse mapToOrder(Order order) {
        return OrderResponse.builder().id(order.getId()).orderNumber(order.getOrderNumber()).orderLineItems(order.getOrderLineItems()).build();
    }
}
