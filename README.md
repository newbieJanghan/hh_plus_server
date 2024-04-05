# 프로젝트 마일스톤

```mermaid
gantt
    title E-Commerce Scenario Application
    dateFormat YYYY-MM-DD
    section API 명세
        요구사항 분석: Done, 2024-03-30, 2024-04-01
        API 명세 작성: Done, 2024-04-01, 2024-04-02
    section DB 설계
        DB 설계: Done, 2024-04-02, 2024-04-03
    section API Application 세팅
        MOCK API 개발: active, 2024-04-03, 2024-04-04
        서버 프로젝트 셋업: future, 2024-04-04, 2024-04-05
    section API 기본 기능 구현
        잔액 충전 / 조회 API: future, 2024-04-05, 2024-04-06
        상품 목록 조회 API: future, 2024-04-06, 2024-04-07
        주문 / 결제 API: future, 2024-04-07, 2024-04-10
        인기 판매 상품 조회 API: future, 2024-04-10, 2024-04-13
    section API 추가 기능 구현
        장바구니 기능: future, 2024-04-13, 2024-04-16
```

# 시나리오 요구사항 분석

| 번호 | 요구사항        | 설명                                 | 유형  | 구현 여부 | 비고 |
|----|-------------|------------------------------------|-----|-------|----|
| 1  | 잔액 충전       | 사용자는 잔액을 충전할 수 있다.                 | 기능  |       |    |
| 2  | 잔액 조회       | 사용자는 잔액을 조회할 수 있다.                 | 기능  |       |    |
| 3  | 상품 목록 조회    | 사용자는 상품 목록을 조회할 수 있다.              | 기능  |       |    |
| 4  | 주문          | 사용자는 상품을 주문/결제할 수 있다.              | 기능  |       |    |
| 5  | 인기 판매 상품 조회 | 사용자는 인기 판매 상품을 조회할 수 있다.           | 기능  |       |    |
| 6  | 주문          | 마지막 재고에 여러 주문이 들어오는 경우 첫 주문만 허용한다. | 비기능 |       |    |
| 7  | 주문          | 잔액이 부족한 경우 주문을 거부한다.               | 비기능 |       |    |

# API 명세

**Authorization**: Bearer token 대신 userId 값으로 사용자를 식별한다.
Session 로그인 기능이 들어온 이후 jwt 로 대체한다.

### 잔액 충전

- Request
    - Method: POST
    - URL: /api/v1/balance/charge
    - Header:
        - Content-Type: application/json
        - Authorization: Bearer {token}
    - Body:
        ```json
        {
            "amount": 0
        }
        ```
- Response
    - 200 OK: 성공적으로 충전
        ```json
        {
            "code": "OK",
            "data": {
                "balance": 0
            }
        }
        ```
    - 400 Bad Request: 충전 금액이 적절하지 않은 경우
        ```json
        {
            "code": "BAD_REQUEST",
            "message": "requested amount is not valid"
        }
        ```
    - 401 Unauthorized: 유저 토큰이 유효하지 않은 경우
        ```json
        {
            "code": "UNAUTHORIZED",
            "message": "user token is not valid"
        }
        ```
    - 404 Not Found User: 유저 정보가 없는 경우
        ```json
        {
            "code": "NOT_FOUND",
            "message": "no user information was found"
        }
        ```

### 잔액 조회

- Request
    - Method: GET
    - URL: /api/v1/balance
    - Header:
        - Content-Type: application/json
        - Authorization: Bearer {token}
- Response
    - 200 OK: 성공적으로 조회
        ```json
        {
            "code": "OK",
            "data": {
                "userId": 1,
                "balance": 0
            }
        }
        ```
    - 401 Unauthorized: 유저 토큰이 유효하지 않은 경우
        ```json
        {
            "code": "UNAUTHORIZED",
            "message": "user token is not valid"
        }
        ```
    - 404 Not Found User: 유저 정보가 없는 경우
        ```json
        {
            "code": "NOT_FOUND",
            "message": "no user information was found"
        }
        ```

### 상품 목록 조회

- Request
    - Method: GET
    - URL: /api/v1/products
    - Header:
        - Content-Type: application/json
        - Authorization: Bearer {token}
    - Query:
        - cursor: 1
        - size?: 10
        - sort?: createdAt
        - direction?: desc
        - category?: ${category}
- Response
    - 200 OK: 성공적으로 조회
        ```json
        {
            "code": "OK",
            "data": [
                {
                    "id": 1,
                    "name": "상품1",
                    "price": 1000,
                    "stock": 10
                }
            ],
            "pageInfo": {
                "cursor": 1,
                "size": 10,
                "total": 1,
                "totalPages": 1
            }
        }
        ```
    - 401 Unauthorized: 유저 토큰이 유효하지 않은 경우
        ```json
        {
            "code": "UNAUTHORIZED",
            "message": "user token is not valid"
        }
        ```

### 주문

- Request
    - Method: POST
    - URL: /api/v1/order
    - Header:
        - Content-Type: application/json
        - Authorization: Bearer {token}
    - Body:
        ```json
        { 
           "items": [ 
                { "productId": 1, "quantity": 1 },
                { "productId": 2, "quantity": 2 }
            ],
           "totalPrice": 0
        }
        ```
- Response
    - 200 OK: 성공적으로 주문
        ```json
        {
            "code": "OK",
            "data": {
                "id": 1,
                "totalPrice": 0,
                "balance": 0,
                "status": "ORDERED",
                "items": [
                    {
                        "product": {
                            "id": 1,
                            "name": "상품1",
                            "price": 1000,
                            "stock": 10
                        },
                        "quantity": 1
                    },
                    {
                        "product": {
                            "id": 2,
                            "name": "상품2",
                            "price": 2000,
                            "stock": 10
                        },
                        "quantity": 2
                    }
                ]
            }
        }
        ```
    - 400 Bad Request: 주문 상품이 적절하지 않은 경우
        ```json
        {
            "code": "BAD_REQUEST",
            "message": "order item is not valid"
        }
        ```
    - 401 Unauthorized: 유저 토큰이 유효하지 않은 경우
        ```json
        {
            "code": "UNAUTHORIZED",
            "message": "user token is not valid"
        }
        ```
    - 404 Not Found User: 유저 정보가 없는 경우
        ```json
        {
            "code": "NOT_FOUND",
            "message": "no user information was found"
        }
        ```
    - 404 Not Found Product: 상품 정보가 없는 경우
        ```json
        {
            "code": "NOT_FOUND",
            "message": "no product information was found"
        }
        ```
    - 422 Insufficient Product Stock: 재고가 부족한 경우
        ```json
        {
            "code": "INSUFFICIENT_PRODUCT_STOCK",
            "message": "product stock is insufficient"
        }
        ```
    - 422 Insufficient Balance: 잔액이 부족한 경우
        ```json
        {
            "code": "INSUFFICIENT_BALANCE",
            "message": "balance is insufficient"
        }
        ```

### 인기 판매 상품 조회

- Request
    - Method: GET
    - URL: /api/v1/products/popular
    - Header:
        - Content-Type: application/json
        - Authorization: Bearer {token}
    - Query:
        - cursor: 1
        - size?: 10
        - sort?: createdAt
        - direction?: desc
        - category?: ${category}
- Response
    - 200 OK: 성공적으로 조회
        ```json
        {
            "code": "OK",
            "data": [
                {
                    "id": 1,
                    "name": "상품1",
                    "price": 1000,
                    "stock": 10
                }
            ],
            "pageInfo": {
                "cursor": 1,
                "size": 10,
                "total": 1,
                "totalPages": 1
            }
        }
        ```
    - 401 Unauthorized: 유저 토큰이 유효하지 않은 경우
        ```json
        {
            "code": "UNAUTHORIZED",
            "message": "user token is not valid"
        }
        ```

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
        string status
    }
    ORDER_ITEM {
        uuid id PK
        uuid order_id FK
        uuid product_id FK
        long quantity
    }
    DAILY_PRODUCT_RANK {
        uuid id PK
        uuid product_id FK
        date ranked_date
        long rank
    }
    USER ||--|| USER_BALANCE: "포인트"
    USER_BALANCE ||--o{ USER_BALANCE_HISTORY: "잔액 충전/사용 로그"
    USER ||--o{ ORDER: "주문"
    ORDER ||--o{ ORDER_ITEM: "주문 상품"
    PRODUCT ||--o{ DAILY_PRODUCT_RANK: "인기 상품"
    PRODUCT ||--o{ ORDER_ITEM: "주문 상품"
    PRODUCT ||--o| STOCK_HISTORY: "재고"
```