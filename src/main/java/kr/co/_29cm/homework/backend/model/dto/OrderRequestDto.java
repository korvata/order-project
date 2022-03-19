package kr.co._29cm.homework.backend.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    private long itemNo;              //상품번호
    private int quantity;            //수량
}
