package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.example.orderservice.config.exceptions.ProductIsNotAvaliableException;
import org.example.orderservice.event.OrderPlacedEvent;
import org.example.orderservice.model.dto.OrderItemRequest;
import org.example.orderservice.model.dto.OrderRequest;
import org.example.orderservice.model.entity.Order;
import org.example.orderservice.model.entity.OrderItem;
import org.example.orderservice.model.entity.User;
import org.example.orderservice.repo.OrderItemRepository;
import org.example.orderservice.repo.OrderRepository;
import org.example.orderservice.repo.UserRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final WebClient.Builder webClient;
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    @Transactional
    public String addOrder(OrderRequest orderRequest,String jwtToken) {
        Order order = new Order();
        order.setOrderItems(orderRequest
                .getOrderItemList()
                .stream()
                .map(item -> toOrderItem(item, order))
                .toList());

        List<String> codes = order.getOrderItems().stream().map(OrderItem::getProductCode).toList();
        List<String> quantities = order.getOrderItems().stream().map(item -> String.valueOf(item.getQuantity())).toList();

        Boolean isInInventory = webClient.build().get()
                        .uri("http://inventory-service/api/inventory",
                                uriBuilder -> uriBuilder.queryParam("codes", codes).queryParam("quantities", quantities).build())
                                .retrieve()
                                .bodyToMono(Boolean.class)
                                .block();

        if(isInInventory){
            orderRepository.save(order);
            orderItemRepository.saveAll(order.getOrderItems());
            webClient.build().put()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("codes", codes).queryParam("quantities", quantities).build())
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber(), extractUser(jwtToken).getEmail()));
        }else{
            throw new ProductIsNotAvaliableException();
        }
        return order.getOrderNumber();
    }

    private OrderItem toOrderItem(OrderItemRequest orderItemRequest, Order order){
        return new OrderItem(orderItemRequest.getProductCode(), order, orderItemRequest.getQuantity());
    }

    private User extractUser(String jwtToken){
        String username = jwtService.extractUsername(jwtToken);
        return userRepository.findUserByUsername(username);
    }

}
