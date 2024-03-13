package org.example.orderservice.rest;


import lombok.RequiredArgsConstructor;
import org.example.orderservice.model.dto.OrderRequest;
import org.example.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        return orderService.addOrder(orderRequest);
    }
}
