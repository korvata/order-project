package kr.co._29cm.homework.backend.service;

import kr.co._29cm.homework.backend.exception.SoldOutException;
import kr.co._29cm.homework.backend.model.dto.OrderResponseDto;
import kr.co._29cm.homework.backend.model.entity.Item;
import kr.co._29cm.homework.backend.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MultiThreadTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderService orderService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    @DisplayName("멀티 쓰레드 환경에서 SoldOutException 테스트")
    void getOrderResult() throws InterruptedException {
        //when
        Item item1 = Item.builder()
                .itemNo(123456)
                .name("테스트 상품1")
                .price(50000)
                .quantity(8)
                .build();
        Item item2 = Item.builder()
                .itemNo(998877)
                .name("테스트 상품2")
                .price(15000)
                .quantity(8)
                .build();

        itemRepository.save(item1);
        itemRepository.save(item2);

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        OrderResponseDto orderResponseDto1 = orderService.getOrder(item1.getItemNo(), 3);
        OrderResponseDto orderResponseDto2 = orderService.getOrder(item2.getItemNo(), 2);
        orderResponseDtoList.add(orderResponseDto1);
        orderResponseDtoList.add(orderResponseDto2);

        //when
        int threadNumbers = 5;//5번의 주문 발생
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadNumbers);
        for (int i = 0; i < threadNumbers; i++) {
            service.submit(() -> {
                try {
                    orderService.getOrderResult(orderResponseDtoList);
                } catch (SoldOutException e) {
                    logger.error(e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await();

        //then
        //두번의 주문이 성공하고 나머지 세번의 주문은 SoldOutException이 발생하여 실패하게 되므로 item1의 남은 수량은 8 - 2*3 = 6이 된다.
        assertEquals(itemRepository.findByItemNo(orderResponseDto1.getItemNo()).get().getQuantity(),
                item1.getQuantity()-2*orderResponseDto1.getQuantity());
    }
}
