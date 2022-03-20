package kr.co._29cm.homework.backend.controller;

import kr.co._29cm.homework.backend.model.dto.OrderResponseDto;
import kr.co._29cm.homework.backend.model.dto.OrderResultResponseDto;
import kr.co._29cm.homework.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public ResponseEntity<OrderResponseDto> order(@RequestParam long reqItemNo, @RequestParam long reqQuantity) {

        return ResponseEntity.ok(orderService.order(reqItemNo, reqQuantity));
    }

    @PostMapping("/order/result")
    public ResponseEntity<OrderResultResponseDto> result(@RequestBody List<OrderResponseDto> orderResponseDtoList) {

        return ResponseEntity.ok(orderService.result(orderResponseDtoList));
    }
}
