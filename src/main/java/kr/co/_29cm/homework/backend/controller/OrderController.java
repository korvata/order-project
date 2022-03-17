package kr.co._29cm.homework.backend.controller;

import kr.co._29cm.homework.backend.model.dto.OrderRequest;
import kr.co._29cm.homework.backend.model.dto.OrderResponse;
import kr.co._29cm.homework.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> order(OrderRequest orderRequest) {

        return ResponseEntity.ok(orderService.order(orderRequest));
    }
}
