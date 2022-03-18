package kr.co._29cm.homework.backend.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class OrderRequestDto {

    private long itemNo;              //상품번호
    private int quantity;            //수량
}
