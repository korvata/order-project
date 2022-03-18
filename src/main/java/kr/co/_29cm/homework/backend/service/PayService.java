package kr.co._29cm.homework.backend.service;

import kr.co._29cm.homework.backend.exception.SoldOutException;
import kr.co._29cm.homework.backend.model.dto.OrderRequestDto;
import kr.co._29cm.homework.backend.model.dto.PayResponseDto;
import kr.co._29cm.homework.backend.model.entity.Item;
import kr.co._29cm.homework.backend.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PayService {

    private final ItemRepository itemRepository;

    public PayService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public PayResponseDto pay(List<OrderRequestDto> orderRequestDtos) {

        int orderPrice = 0;
        int shippingFee = 0;

        for (OrderRequestDto orderRequestDto : orderRequestDtos) {
            Item item = itemRepository.findByItemNo(orderRequestDto.getItemNo()).get();

            if (item.getQuantity() - orderRequestDto.getQuantity() >= 0) {//재고량이 충분할시
                item.setQuantity(item.getQuantity() - orderRequestDto.getQuantity());
                orderPrice += item.getPrice();
            } else {//재고량이 부족할시
                throw new SoldOutException("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
            }
        }

        if (orderPrice < 50000) shippingFee = 2500;

        return PayResponseDto.builder()
                .orderPrice(orderPrice)
                .shippingFee(shippingFee)
                .payPrice(orderPrice+shippingFee)       //지불금액 = 주문금액 + 배송비
                .build();
    }
}
