package org.example.orderservice.rest;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.config.exceptions.ProductIsNotAvaliableException;
import org.example.orderservice.model.dto.OrderRequest;
import org.example.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest httpServletRequest){
        String jwtToken = httpServletRequest.getHeader("Authorization").substring(7);
        return ResponseEntity.ok(orderService.addOrder(orderRequest,jwtToken));
    }

    public ResponseEntity<String> fallbackMethod(OrderRequest orderRequest,RuntimeException ex) {
        if(ex instanceof ProductIsNotAvaliableException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server error");
    }
}
