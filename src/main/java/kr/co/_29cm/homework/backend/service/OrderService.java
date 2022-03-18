package kr.co._29cm.homework.backend.service;

import kr.co._29cm.homework.backend.exception.NoItemException;
import kr.co._29cm.homework.backend.model.dto.OrderRequestDto;
import kr.co._29cm.homework.backend.model.dto.OrderResponseDto;
import kr.co._29cm.homework.backend.model.entity.Item;
import kr.co._29cm.homework.backend.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
}