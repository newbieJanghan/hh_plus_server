# 프로젝트 마일스톤

```mermaid
gantt
    title E-Commerce Scenario Application
    dateFormat YYYY-MM-DD
    section API 명세
        요구사항 분석: done, 2024-03-30, 2024-04-01
        API 명세 작성: done, 2024-04-01, 2024-04-02
    section DB 설계
        DB 설계: done, 2024-04-02, 2024-04-03
    section API Application 세팅
        MOCK API 개발: done, 2024-04-03, 2024-04-04
        서버 프로젝트 셋업: done, 2024-04-04, 2024-04-05
    section API 기본 기능 구현
        잔액 충전 / 조회 API: active, 2024-04-05, 2024-04-06
        상품 목록 조회 API: active, 2024-04-06, 2024-04-07
        주문 / 결제 API: active, 2024-04-07, 2024-04-10
        인기 판매 상품 조회 API: active, 2024-04-10, 2024-04-13
    section API 추가 기능 구현
        장바구니 기능: future, 2024-04-13, 2024-04-16
```

# 요구사항

| 번호 | 요구사항        | 설명                                 | 유형  | 구현 여부 | 비고 |
|----|-------------|------------------------------------|-----|-------|----|
| 1  | 잔액 충전       | 사용자는 잔액을 충전할 수 있다.                 | 기능  |       |    |
| 2  | 잔액 조회       | 사용자는 잔액을 조회할 수 있다.                 | 기능  |       |    |
| 3  | 상품 목록 조회    | 사용자는 상품 목록을 조회할 수 있다.              | 기능  |       |    |
| 4  | 주문          | 사용자는 상품을 주문/결제할 수 있다.              | 기능  |       |    |
| 5  | 인기 판매 상품 조회 | 사용자는 인기 판매 상품을 조회할 수 있다.           | 기능  |       |    |
| 6  | 주문          | 마지막 재고에 여러 주문이 들어오는 경우 첫 주문만 허용한다. | 비기능 |       |    |
| 7  | 주문          | 잔액이 부족한 경우 주문을 거부한다.               | 비기능 |       |    |
| 8  | 주문          | 재고가 부족한 경우 주문을 거부한다.               | 비기능 |       |    |
| 9  | 주문          | 주문 완료시 데이터 레이크로 주문 정보를 송신한다.       | 비기능 |       |    |
| 10 | 잔액 충전       | 충전 금액이 적절하지 않은 경우 충전을 거부한다.        | 비기능 |       |    |
| 11 | 잔액 충전       | 충전 금액이 적절하지 않은 경우 충전을 거부한다.        | 비기능 |       |    |

# API 명세 및 플로우 차트

## Swagger-UI

![capture](./docs/.asset/swagger-ui.png)

## Authorization

Bearer token 대신 userId 값으로 사용자를 식별한다.
이후 jwt 로 대체한다.

## [잔액 API](./docs/api/balance.md)

- 잔액 조회
- 잔액 충전

## [상품 API](./docs/api/products.md)

- 상품 목록 조회
- 인기 상품 조회

## [주문 API](./docs/api/orders.md)

- 주문

# ERD

```mermaid
erDiagram
    USER {
        uuid id PK
    }
    USER_BALANCE {
        uuid id PK
        long user_id FK
        long amount
    }
    USER_BALANCE_HISTORY {
        uuid id PK
        uuid user_balance_id FK
        long amount
        string type
    }
    PRODUCT {
        uuid id PK
        string name
        long price
        long stock
    }
    STOCK_HISTORY {
        uuid product_id FK
        long quantity
        string type
    }
    ORDER {
        uuid id PK
        uuid user_id FK
        long total_price
    }
    ORDER_ITEM {
        uuid id PK
        uuid order_id FK
        uuid product_id FK
        string status
        long quantity
        long paid_price
    }
    PAYMENT {
        uuid id PK
        uuid order_id FK
        uuid paymet_method_id
        string type
        string status
    }
    USER ||--|| USER_BALANCE: "포인트"
    USER_BALANCE ||--o{ USER_BALANCE_HISTORY: "잔액 충전/사용 로그"
    USER ||--o{ ORDER: "주문"
    ORDER ||--o{ ORDER_ITEM: "주문 상품"
    PRODUCT ||--o{ ORDER_ITEM: "주문 상품"
    PRODUCT ||--o| STOCK_HISTORY: "재고 이력"
    ORDER ||--o| PAYMENT: "결제"
```