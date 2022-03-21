package kr.co._29cm.homework.backend.service;

import kr.co._29cm.homework.backend.model.dto.OrderResponseDto;
import kr.co._29cm.homework.backend.model.entity.Item;
import kr.co._29cm.homework.backend.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
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
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderService orderService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private int THREAD_CNT = 100;
    private ExecutorService executorService = Executors.newFixedThreadPool(THREAD_CNT);
    private CountDownLatch latch = new CountDownLatch(THREAD_CNT);

    @Test
    @DisplayName("주문 내역 받아오기")
    void getOrder() {
    }

    @Test
    @DisplayName("멀티 쓰레드 환경에서 SoldOutException 테스트")
    void getOrderResult() throws InterruptedException {
        //when
        Item item1 = Item.builder()
                .itemNo(123456)
                .name("테스트 상품1")
                .price(50000)
                .quantity(1000)
                .build();
        Item item2 = Item.builder()
                .itemNo(998877)
                .name("테스트 상품2")
                .price(15000)
                .quantity(1000)
                .build();

        Item saveItem1 = itemRepository.save(item1);
        Item saveItem2 = itemRepository.save(item2);

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        OrderResponseDto orderResponseDto1 = orderService.getOrder(item1.getItemNo(), 2);
        OrderResponseDto orderResponseDto2 = orderService.getOrder(item2.getItemNo(), 1);
        orderResponseDtoList.add(orderResponseDto1);
        orderResponseDtoList.add(orderResponseDto2);

        //given
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for(int i=0; i<5; i++){
            logger.info("orderResultResponseDto{} : {}", orderService.getOrderResult(orderResponseDtoList));
            logger.info("{}",i);
        }

        //then
        boolean wellDone = countDownLatch.await(10, TimeUnit.SECONDS);
        Assertions.assertEquals(saveItem1.getQuantity(), item1.getQuantity() - 2*5);
        Assertions.assertEquals(saveItem2.getQuantity(), item2.getQuantity() - 1*5);
        Assertions.assertEquals(wellDone, true);

    }
}