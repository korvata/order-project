package kr.co._29cm.homework.backend.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class OrderResponseDto {

    private String name;            //상품명
    private int quantity;        //수량
}
