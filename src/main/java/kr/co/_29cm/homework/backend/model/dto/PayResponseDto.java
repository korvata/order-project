package kr.co._29cm.homework.backend.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class PayResponseDto {

    private int orderPrice;             //주문금액
    private int shippingFee;            //배송비
    private int payPrice;               //지불금액
}
