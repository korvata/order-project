package kr.co._29cm.homework.backend.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ITEM")
public class Item {

    private String itemNo;          //상품번호
    private String name;            //상품명
    private int price;              //판매가격
    private int quantity;           //재고수량
}
