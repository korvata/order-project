package kr.co._29cm.homework.backend.service;

import kr.co._29cm.homework.backend.exception.NoItemException;
import kr.co._29cm.homework.backend.exception.SoldOutException;
import kr.co._29cm.homework.backend.model.dto.OrderRequestDto;
import kr.co._29cm.homework.backend.model.dto.OrderResponseDto;
import kr.co._29cm.homework.backend.model.dto.OrderResultResponseDto;
import kr.co._29cm.homework.backend.model.entity.Item;
import kr.co._29cm.homework.backend.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final ItemRepository itemRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public OrderService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public OrderResponseDto order(OrderRequestDto orderRequestDto) {

        long orderItemNo = orderRequestDto.getItemNo();
        int orderQuantity = orderRequestDto.getQuantity();

        Optional<Item> optionalItem = itemRepository.findByItemNo(orderItemNo);

        if (optionalItem.isPresent()) { //상품이 DB에 존재하면
            Item item = optionalItem.get();

            OrderResponseDto orderResponse = OrderResponseDto.builder()
                    .name(item.getName())
                    .quantity(orderQuantity)
                    .build();

            logger.info("orderResponse : {}", orderResponse);
            return orderResponse;
        } else {    //상품이 DB에 존재하지 않으면
            throw new NoItemException(orderItemNo + " is not exist in DB!");
        }
    }

    public OrderResultResponseDto result(List<OrderRequestDto> orderRequestDtoList) {

        int orderPrice = 0;
        int shippingFee = 0;

        for (OrderRequestDto orderRequestDto : orderRequestDtoList) {
            Item item = itemRepository.findByItemNo(orderRequestDto.getItemNo()).get();

            if (item.getQuantity() - orderRequestDto.getQuantity() >= 0) {//재고량이 충분할시
                item.setQuantity(item.getQuantity() - orderRequestDto.getQuantity());
                orderPrice += item.getPrice();
            } else {//재고량이 부족할시
                throw new SoldOutException("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
            }
        }

        if (orderPrice < 50000) shippingFee = 2500;

        return OrderResultResponseDto.builder()
                .orderPrice(orderPrice)
                .shippingFee(shippingFee)
                .payPrice(orderPrice+shippingFee)       //지불금액 = 주문금액 + 배송비
                .build();
    }
}