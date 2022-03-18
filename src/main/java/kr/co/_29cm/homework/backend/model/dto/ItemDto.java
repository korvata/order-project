package kr.co._29cm.homework.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private Long itemNo;          //상품번호
    private String name;            //상품명
    private int price;              //판매가격
    private int quantity;           //재고수량
}
