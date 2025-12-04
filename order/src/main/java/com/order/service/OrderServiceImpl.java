package com.order.service;

import com.order.client.CartClient;
import com.order.client.InventoryClient;
import com.order.dtos.*;

import com.order.entities.Order;
import com.order.entities.OrderItem;
import com.order.exception.UserIdNotFoundException;
import com.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final CartClient client;
    private final InventoryClient inventoryClient;


    @Override
    public List<OrderOutputDTO> getOrderOutput(Long userId) {
        List<Order> orderList=orderRepository.findByUserId(userId)
                .orElseThrow(()->new UserIdNotFoundException("No Order Yet!"));
        List<OrderOutputDTO> dto=orderList.stream()
                .map(ele->{
                    OrderOutputDTO outputDTO=new OrderOutputDTO();
                    outputDTO.setStatus("Completed");
                    List<OrderInputItemDTO> orderInputItem=ele.getItems()
                            .stream().map(fn->{
                                OrderInputItemDTO inputItemDTO=new OrderInputItemDTO();
                                inputItemDTO.setPrice(fn.getPrice());
                                inputItemDTO.setProductId(fn.getProductId());
                                inputItemDTO.setQuantity(fn.getQuantity());
                                return inputItemDTO;
                            }).toList();
                    outputDTO.setItems(orderInputItem);
                    return outputDTO;
                }).toList();
        return dto;
    }

    @Override
    public String addOrder(Long userId,OrderInputDTO orderInputDTO) {
        CartResponseDTO dto=client.getAll().getBody();
        Order order=new Order();
        order.setPayment(orderInputDTO.getPayment());
        order.setUserId(userId);
        order.setTotalAmount(dto.getTotalPrice());
        order.setDateTime(LocalDateTime.now());
        order.setItems(new ArrayList<>());


        List<OrderItem> orderItems=dto.getItems().stream()
                .map(ele->{
                    OrderItem orderItem=new OrderItem();
                    orderItem.setProductId(ele.getProductId());
                    orderItem.setPrice(ele.getPrice());
                    orderItem.setQuantity(ele.getQuantity());
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toList());
        order.setItems(orderItems);
        for(OrderItem item:orderItems){
            InventoryInputDTO inputDTO=new InventoryInputDTO();
            inputDTO.setProductid(item.getProductId());
            inputDTO.setQuantity(item.getQuantity());
            inventoryClient.orderAffect(inputDTO);
        }
        client.removeFromCart();
        orderRepository.save(order);
        return "Order Placed Successfully";
    }
}
