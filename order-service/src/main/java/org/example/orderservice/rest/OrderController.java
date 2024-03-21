package org.example.orderservice.rest;


import lombok.RequiredArgsConstructor;
import org.example.orderservice.model.dto.OrderRequest;
import org.example.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest){
        return ResponseEntity.ok(orderService.addOrder(orderRequest));
    }
}
