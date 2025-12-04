package com.order.service;

import com.order.dtos.OrderInputDTO;
import com.order.dtos.OrderOutputDTO;

import java.util.List;


public interface OrderService {
    public List<OrderOutputDTO> getOrderOutput(Long userId);
    public String addOrder(Long userId,OrderInputDTO orderInputDTO);
}
