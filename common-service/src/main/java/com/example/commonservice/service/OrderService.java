package com.example.commonservice.service;


import com.example.commonservice.dto.OrderDivideRequest;
import com.example.commonservice.dto.OrderDivideResponse;
import com.example.commonservice.dto.OrderRequest;
import com.example.commonservice.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse add(OrderRequest orderRequest);

    List<OrderResponse> getAll();

    OrderResponse getById(Integer orderId);

    OrderResponse updateById(Integer orderId, OrderRequest orderRequest);

    OrderDivideResponse divideIntoTransportation(Integer orderId, OrderDivideRequest orderDivideRequest);

    OrderResponse deleteById(Integer orderId);
}

