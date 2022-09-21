package com.orderservice.controller;

import com.orderservice.dto.OrderRequest;
import com.orderservice.dto.OrderResponse;
import com.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory",fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory",fallbackMethod = "fallbackMethod")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
    }

    @GetMapping
    public List<OrderResponse> getOrderList(){
        return orderService.getOrderList();
    }

    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(() ->  "Something went wrong, Please order after some time!");
    }
}
