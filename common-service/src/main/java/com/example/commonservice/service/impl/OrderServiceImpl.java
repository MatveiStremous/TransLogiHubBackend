package com.example.commonservice.service.impl;

import com.example.commonservice.dto.OrderRequest;
import com.example.commonservice.dto.OrderResponse;
import com.example.commonservice.exception.BusinessException;
import com.example.commonservice.model.Order;
import com.example.commonservice.repository.OrderRepository;
import com.example.commonservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final String ORDER_IS_NOT_EXIST = "Order with this id is not exist.";

    @Override
    public OrderResponse add(OrderRequest orderRequest) {
        Order newOrder = modelMapper.map(orderRequest, Order.class);
        newOrder = orderRepository.save(newOrder);
        return modelMapper.map(newOrder, OrderResponse.class);
    }

    @Override
    public List<OrderResponse> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .toList();
    }

    @Override
    public OrderResponse getById(Integer orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new BusinessException(HttpStatus.CONFLICT, ORDER_IS_NOT_EXIST);
        }
        return modelMapper.map(optionalOrder.get(), OrderResponse.class);
    }

    @Override
    public OrderResponse updateById(Integer orderId, OrderRequest orderRequest) {
        OrderResponse orderFromDb = getById(orderId);
        Order orderForUpdate = modelMapper.map(orderRequest, Order.class);
        orderForUpdate.setId(orderId);
        Order updatedOrder = orderRepository.save(orderForUpdate);
        return modelMapper.map(updatedOrder, OrderResponse.class);
    }
}
