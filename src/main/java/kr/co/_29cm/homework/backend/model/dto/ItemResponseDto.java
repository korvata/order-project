package kr.co._29cm.homework.backend.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto {

    private long itemNo;          //상품번호
    private String name;            //상품명
    private int price;              //판매가격
    private int quantity;           //재고수량
}
