package com.example.commonservice.service.impl;

import com.example.commonservice.dto.OrderRequest;
import com.example.commonservice.dto.OrderResponse;
import com.example.commonservice.exception.BusinessException;
import com.example.commonservice.model.Order;
import com.example.commonservice.model.enums.OrderStatus;
import com.example.commonservice.repository.OrderRepository;
import com.example.commonservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final String ORDER_IS_NOT_EXIST = "Order with this id is not exist.";

    @Override
    public OrderResponse add(OrderRequest orderRequest) {
        Order newOrder = modelMapper.map(orderRequest, Order.class);
        newOrder.setStatus(OrderStatus.FORMED);
        newOrder = orderRepository.save(newOrder);
        return modelMapper.map(newOrder, OrderResponse.class);
    }

    @Override
    public List<OrderResponse> getAll() {
        return orderRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .toList();
    }

    @Override
    public OrderResponse getById(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, ORDER_IS_NOT_EXIST));
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse updateById(Integer orderId, OrderRequest orderRequest) {
        OrderResponse orderFromDb = getById(orderId);
        Order orderForUpdate = modelMapper.map(orderRequest, Order.class);
        orderForUpdate.setId(orderId);
        orderForUpdate.setStatus(orderFromDb.getStatus());
        Order updatedOrder = orderRepository.save(orderForUpdate);
        return modelMapper.map(updatedOrder, OrderResponse.class);
    }
}
