package kr.co._29cm.homework.backend.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
public class Item {

    private String itemNo;          //상품번호
    private String name;            //상품명
    private int price;              //판매가격
    private int quantity;           //재고수량
}
