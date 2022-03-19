package kr.co._29cm.homework.backend.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private String name;            //상품명
    private int quantity;        //수량
}
