package kr.co._29cm.homework.backend.controller;

import kr.co._29cm.homework.backend.model.dto.OrderRequestDto;
import kr.co._29cm.homework.backend.model.dto.OrderResponseDto;
import kr.co._29cm.homework.backend.model.dto.OrderResultResponseDto;
import kr.co._29cm.homework.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/order")
    public ResponseEntity<OrderResponseDto> order(OrderRequestDto orderRequestDto) {

        return ResponseEntity.ok(orderService.order(orderRequestDto));
    }

    @PostMapping("/order/result")
    public ResponseEntity<OrderResultResponseDto> result(List<OrderRequestDto> orderRequestDtoList) {

        return ResponseEntity.ok(orderService.result(orderRequestDtoList));
    }
}
