package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.model.dto.OrderItemRequest;
import org.example.orderservice.model.dto.OrderRequest;
import org.example.orderservice.model.entity.Order;
import org.example.orderservice.model.entity.OrderItem;
import org.example.orderservice.repo.OrderItemRepository;
import org.example.orderservice.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;


    @Transactional
    public String addOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderItems(orderRequest
                .getOrderItemList()
                .stream()
                .map(item -> toOrderItem(item, order))
                .toList());

        orderRepository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());



        return order.getOrderNumber();
    }


    private OrderItem toOrderItem(OrderItemRequest orderItemRequest, Order order){
        return new OrderItem(orderItemRequest.getProductCode(), order, orderItemRequest.getQuantity());
    }
}
