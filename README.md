# 상품주문 프로젝트
## 목차
  - [프로젝트 구조](#프로젝트구조) 
  - [구현방향](#구현방향)
  - [구현결과](#구현결과)
  - [검증결과](#검증결과)
  
## 프로젝트구조
  - DB
    - 
    ![image](https://user-images.githubusercontent.com/45089402/159536843-ab540cf9-fd13-47e2-945e-fc9c22950ee2.png)
    
  - 패키지 및 클래스
    -
    ![image](https://user-images.githubusercontent.com/45089402/159618935-412a4fa5-84e0-4913-be71-0da01bb60839.png)
      - Main
        - controller : API 매핑
        - exception : SoldOutException과 같은 에러 처리
        - model : Entity와 DTO
        - repository : JPA 처리
        - service : 상품 조회, 주문과 같은 비즈니스 로직 처리
        - Display : 콘솔 입력 및 화면 처리
        - resource : application 설정 및 DB 데이터 입력을 위한 sql 파일
      - Test
        - service : 멀티쓰레드 환경에서 SoldOutException 테스트
        - resource : application 설정(main과 app.about 값을 구분하여 display 로직을 안타게 설정)


## 구현방향
  - 기술 스펙
    - Java, SpringBoot, JPA, H2 DB, RestTemplate, Junit5

  - 비즈니스 로직
    -  GET /v1/items를 통해 모든 상품의 정보를 조회한다.
    -  GET /v1/orders를 통해 콘솔로 입력받은 주문 정보를 받아온다.
        - DB에 없는 상품정보로 입력한 주문 정보가 있을시 해당 상품이 존재하지 않는다는 메세지와 함께 에러가 발생한다.
    -  POST /v1/orders/result를 통해 입력 받은 주문 정보를 토대로 주문을 진행한다.
        -  주문시 수량이 부족하면 SoldOutException이 발생하여 주문이 실패한다.
        -  수량이 충분하다면 주문이 성공하고 주문 내역을 표시한다.
        -  지불금액 = 주문금액 + 배송비(배송비는 주문금액이 50000원 이상일시 2500원으로 측정한다)
  - Dev 환경과 Test 환경
    - Test시 콘솔 Display 표시 로직을 타지 않기 위해 app.about의 값을 dev와 test로 구분하였다.(dev만 콘솔 디스플레이 표시)

## 구현결과
  - 구동 후 o 입력시
    - 
    ![image](https://user-images.githubusercontent.com/45089402/159620457-c46192dc-52ad-4165-8803-bf944f42d244.png)
  - 단건 주문
    - 
    ![image](https://user-images.githubusercontent.com/45089402/159620735-e775608c-f7e4-4671-9b8a-23a71ab4565e.png)
    ![image](https://user-images.githubusercontent.com/45089402/159620821-3c0bc0f2-6a45-4ba2-a592-3e9aabd4e595.png)
      - 주문 성공후 213341 품목의 수량이 2개만큼 줄어듦

  - 다건 주문
    -
    ![image](https://user-images.githubusercontent.com/45089402/159621199-34f132f0-a50e-4e35-af80-488b4a2bc053.png)
  - SoldOutException
    -
    ![image](https://user-images.githubusercontent.com/45089402/159621280-a15deda5-97c4-40ae-8e13-1b1bc2f4aa6e.png)
    

## 검증결과
  - 멀티 쓰레드 환경에서 SoldOutException 테스트
  ![image](https://user-images.githubusercontent.com/45089402/159622078-e7627796-10ba-451f-b441-ccf1ea4ddbd4.png)
