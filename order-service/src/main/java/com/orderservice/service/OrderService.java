package com.orderservice.service;

import com.orderservice.dto.OrderLineItemsDto;
import com.orderservice.dto.OrderRequest;
import com.orderservice.dto.OrderResponse;
import com.orderservice.model.Order;
import com.orderservice.model.OrderLineItems;
import com.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtosList().stream().map(this::mapToDto).toList();
        order.setOrderLineItems(orderLineItems);
        orderRepository.save(order);
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
