package kr.co._29cm.homework.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.bind.v2.schemagen.xmlschema.Any;
import kr.co._29cm.homework.backend.model.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.*;

@Component
public class Display {

    private final BufferedReader br;
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Display() {
        this.br = new BufferedReader(new InputStreamReader(System.in));;
        this.restTemplate = new RestTemplate();
    }

    public void start() {
        while (true) {
            String command = getCommand();                                                                              //명령 입력
            if ("o".equals(command)) {//주문 명령일 경우
                getAllItems();        //상품 정보 조회
                List<OrderRequestDto> orderRequestDtoList = getOrderRequestDtoList();                                   // 주문 입력받기
                List<OrderResponseDto> orderResponseDtoList = getOrder(orderRequestDtoList);            // 주문 하기
                ResponseEntity<OrderResultResponseDto> OrderResultResponseDto = getOrderResult(orderResponseDtoList);//주문 내역받기
                printOrderResult(OrderResultResponseDto);//주문 내역 조회
            } else if ("q".equals(command)) {//종료 명령일 경우
                quit();
                break;
            }
        }
    }

    /**
     * 상품 노출
     */
    private void getAllItems() {
        List<ItemResponseDto> itemResponseDtoList = getItemResponseDto();
        StringBuilder sb = new StringBuilder();

        sb.append("상품번호\t상품명\t\t\t\t\t판매가격\t재고수\n");
        for (ItemResponseDto item : itemResponseDtoList) {
            sb.append(item.getItemNo() + "\t" + item.getName() + "\t\t"
                    + item.getPrice() + "\t" + item.getQuantity() + "\n");
        }
        sb.append("\n");
        System.out.println(sb.toString());
    }

    /**
     * 상품리스트 조회
     *
     * @return
     */
    private List<ItemResponseDto> getItemResponseDto() {
        String url = "http://localhost:8080/v1/items";
        ResponseEntity<ItemResponseDto[]> response = restTemplate.getForEntity(url, ItemResponseDto[].class);
        ObjectMapper mapper = new ObjectMapper();
        List<ItemResponseDto> ItemResponseDto = mapper.convertValue(response.getBody(), new TypeReference<List<ItemResponseDto>>() {
        });
        return ItemResponseDto;
    }

    /**
     * 명령 입력받기
     *
     * @return
     */
    private String getCommand() {
        System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
        String command = getInput();

        return command;
    }

    /**
     * 주문 입력받기
     */
    private List<OrderRequestDto> getOrderRequestDtoList() {
        List<OrderRequestDto> orderRequestDtoList = new LinkedList<>();

        while (true) {
            System.out.print("상품번호 : ");
            String itemNo = getInput();

            if (" ".equals(itemNo)) {
                break;
            }

            System.out.print("수량 : ");
            String quantity = getInput();

            if (" ".equals(quantity)) {
                break;
            }

            try {
                OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                        .itemNo(Integer.parseInt(itemNo))
                        .quantity(Integer.parseInt(quantity))
                        .build();
                orderRequestDtoList.add(orderRequestDto);
            } catch (Exception e) {
                System.out.println("상품번호와 수량을 정확히 입력해주세요.");
            }
        }
        logger.info("orderRequestDtoList : {}", orderRequestDtoList.toString());
        return orderRequestDtoList;
    }

    /**
     * 주문하기
     */
    private List<OrderResponseDto> getOrder(List<OrderRequestDto> orderRequestDtoList) {
        String url = "http://localhost:8080/v1/order/reqItemNo/{reqItemNo}/reqQuantity/{reqQuantity}";
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        try {
            for(OrderRequestDto orderRequestDto : orderRequestDtoList) {

                UriComponentsBuilder uriComponents = UriComponentsBuilder.fromHttpUrl(url)
                        .queryParam("reqItemNo", orderRequestDto.getItemNo())
                        .queryParam("reqQuantity", Long.valueOf(orderRequestDto.getQuantity()));

                ResponseEntity<OrderResponseDto> orderResponseDto = restTemplate.getForEntity(uriComponents.toUriString(), OrderResponseDto.class);
                orderResponseDtoList.add(orderResponseDto.getBody());
            }
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
        }

        logger.info("orderResponseDtoList : {}", orderResponseDtoList.toString());
        return orderResponseDtoList;
    }

    /**
     * 주문내역받기
     */
    private ResponseEntity<OrderResultResponseDto> getOrderResult(List<OrderResponseDto> orderResponseDtoList) {
        String url = "http://localhost:8080/v1/order/result";
        ResponseEntity<OrderResultResponseDto> response = null;

        System.out.println("주문내역:");
        System.out.println("----------------------------------------");

        try {
            for (OrderResponseDto orderResponseDto : orderResponseDtoList) {
                System.out.println(String.format("%s", orderResponseDto.getName()) + " - " + String.format("%d", orderResponseDto.getQuantity()) + "개");
            }
            response = restTemplate.postForEntity(url, orderResponseDtoList, OrderResultResponseDto.class);
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 주문결과 노출
     *
     * @param
     */
    private void printOrderResult(ResponseEntity<OrderResultResponseDto> orderResultResponse) {
        if (orderResultResponse == null) {
            return;
        }

        OrderResultResponseDto orderResultResponseDto = orderResultResponse.getBody();

        System.out.println("----------------------------------------");
        System.out.println("주문금액 : " + String.format("%,d", orderResultResponseDto.getOrderPrice()) + "원");

        if (orderResultResponseDto.getShippingFee() != 0) {
            System.out.println("배송비 : " + String.format("%,d", orderResultResponseDto.getShippingFee()) + "원");
        }

        int payPrice = orderResultResponseDto.getPayPrice();
        System.out.println("----------------------------------------");
        System.out.println("지불금액 : " + String.format("%,d", payPrice) + "원");
        System.out.println("----------------------------------------");
    }

    /**
     * 값 입력
     *
     * @return
     * @throws IOException
     */
    private String getInput() {
        String input = null;
        try {
            input = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    /**
     * 종료
     */
    private void quit() {
        System.out.println("고객님의 주문 감사합니다.");
    }

}
