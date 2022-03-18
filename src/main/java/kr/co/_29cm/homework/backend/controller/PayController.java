package kr.co._29cm.homework.backend.controller;

import kr.co._29cm.homework.backend.model.dto.OrderRequestDto;
import kr.co._29cm.homework.backend.model.dto.PayResponseDto;
import kr.co._29cm.homework.backend.service.PayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class PayController {

    private final PayService payService;

    public PayController(PayService payService) {
        this.payService = payService;
    }

    @PostMapping("/pay")
    public ResponseEntity<PayResponseDto> pay(List<OrderRequestDto> orderRequestDtos){
        return ResponseEntity.ok(payService.pay(orderRequestDtos));
    }
}
