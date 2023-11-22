package com.example.commonservice.controller;

import com.example.commonservice.dto.OrderRequest;
import com.example.commonservice.dto.OrderResponse;
import com.example.commonservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("common/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderResponse addOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.add(orderRequest);
    }

    @GetMapping
    public List<OrderResponse> getOrders() {
        return orderService.getAll();
    }

    @GetMapping("{orderId}")
    public OrderResponse getOrderById(@PathVariable Integer orderId) {
        return orderService.getById(orderId);
    }

    @PutMapping("{orderId}")
    public OrderResponse updateOrder(@PathVariable Integer orderId,
                                                 @RequestBody OrderRequest orderRequest) {
        return orderService.updateById(orderId, orderRequest);
    }
}
