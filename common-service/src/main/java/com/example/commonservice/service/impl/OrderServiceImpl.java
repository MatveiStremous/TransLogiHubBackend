package com.example.commonservice.service.impl;

import com.example.commonservice.dto.OrderDivideRequest;
import com.example.commonservice.dto.OrderDivideResponse;
import com.example.commonservice.dto.OrderRequest;
import com.example.commonservice.dto.OrderResponse;
import com.example.commonservice.dto.TransportationRequest;
import com.example.commonservice.exception.BusinessException;
import com.example.commonservice.model.Order;
import com.example.commonservice.model.enums.HistoryType;
import com.example.commonservice.model.enums.OrderStatus;
import com.example.commonservice.repository.OrderRepository;
import com.example.commonservice.service.HistoryService;
import com.example.commonservice.service.OrderService;
import com.example.commonservice.service.TransportationService;
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
    private final TransportationService transportationService;
    private final HistoryService historyService;
    private final ModelMapper modelMapper;

    @Override
    public OrderResponse add(OrderRequest orderRequest) {
        Order newOrder = modelMapper.map(orderRequest, Order.class);
        newOrder.setStatus(OrderStatus.FORMED);
        newOrder = orderRepository.save(newOrder);
        addToHistory(newOrder.getId(), "Заказ создан");
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
                .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, "ORDER-1"));
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

    @Override
    public OrderDivideResponse divideIntoTransportation(Integer orderId, OrderDivideRequest orderDivideRequest) {
        orderDivideRequest.getTransportations().stream()
                .map(t -> TransportationRequest.builder()
                        .weight(t.getWeight())
                        .note(t.getNote())
                        .orderId(orderId)
                        .build())
                .forEach(transportationService::add);
        return OrderDivideResponse.builder()
                .transportations(transportationService.getAllByOrderId(orderId))
                .build();
    }

    public Order getEntityById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, "ORDER-1"));
    }

    private void addToHistory(Integer entityId, String message) {
        historyService.add(entityId, HistoryType.ORDER, message);
    }
}
