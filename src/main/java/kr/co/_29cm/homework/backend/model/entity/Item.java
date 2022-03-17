package kr.co._29cm.homework.backend.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ITEM")
public class Item {

    @Id
    @Column(name = "ITEM_NO")
    private String itemNo;          //상품번호

    @Column(name = "NAME")
    private String name;            //상품명

    @Column(name = "PRICE")
    private int price;              //판매가격

    @Column(name = "QUANTITY")
    private int quantity;           //재고수량
}
