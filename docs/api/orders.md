# 주문 API

## 주문

### API 명세

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
                { "productId": "uuid", "quantity": 1 },
                { "productId": "uuid", "quantity": 2 }
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
                "id": "uuid",
                "totalPrice": 0,
                "items": [
                    {
                        "id": "uuid",
                        "orderId": "uuid",
                        "product": {
                            "id": "uuid",
                            "name": "상품1",
                            "price": 1000,
                            "stock": 10
                        },
                        "quantity": 1,
                        "status": "ORDERED"
                    },
                    {
                        "id": "uuid",
                        "orderId": "uuid",
                        "product": {
                            "id": "uuid",
                            "name": "상품2",
                            "price": 2000,
                            "stock": 10
                        },
                        "quantity": 2,
                        "status": "ORDERED"
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

### 플로우 차트

#### Rest API

```mermaid
sequenceDiagram
    participant client
    participant server
    participant db

#
    client ->> server: POST /api/v1/order
    server ->> db: PRODUCT에 재고 조회
    opt 재고 부족
        db -->> server: PRODUCT
        server -->> client: 422 INSUFFICIENT_PRODUCT_STOCK
    end
    note over server: Product
    server ->> db: USER_BALANCE에 잔액 조회
    opt 잔액 부족
        db -->> server: USER_BALANCE
        server -->> client: 422 INSUFFICIENT_BALANCE
    end
    note over server: Product
    server ->> db: USER_BALANCE에 잔액 차감
    server ->> db: USER_BALANCE_HISTORY에 잔액 이력 저장
    server ->> db: PRODUCT에 재고 차감
    server ->> db: ORDER에 주문 저장
    note over server: Product, Order
    server ->> db: ORDER_ITEM에 주문 상품 저장
    server ->> dataLake: 주문 데이터 저장
    note over server: Order, List<OrderItem>
    server -->> client: 200 OK, OrderResponse
```

#### server

- Product Domain 으로부터 재고 검증

```mermaid
---
title: OrderApp collaborate sequence with Product Domain
---
sequenceDiagram
    participant OrderApp
    create actor Order
    OrderApp -->> Order: new Order()
    note over Order: Order.userId(userId)

    loop for each CreateOrderItemRequestDto
        create actor OrderItem
        OrderApp -->> OrderItem: new OrderItem()
        Note over ProductRepository: findOneById(productId)
        create actor Product
        ProductRepository -->> Product: find
        Note over Product: validateStock(quantity)
        Note over Product: sell(quantity)
    #
        Product -->> OrderItem: join
        note over OrderItem: setProduct(Product)
        OrderItem -->> Order: join
        note over Order: addOrderItem(OrderItem)
    end
#

```

- Balance Domain 으로부터 잔액 검증

```mermaid
---
title: OrderApp collaborate sequence with UserDomain 
---
sequenceDiagram
    actor Order
    note over Order: calculateTotalPrice()
#
    note over UserBalanceRepository: findOneByUserId(userId)
    create actor UserBalance
    UserBalanceRepository -->> UserBalance: find
#
    Order -->> UserBalance: totalPrice
    note over UserBalance: checkUseAvailable(totalPrice)
    note over UserBalance: use(totalPrice)
    UserBalance -->> UserBalanceRepository: persisted
    note over UserBalanceRepository: save(UserBalance)
```

- Persistence

```mermaid
---
title: OrderApp save sequence
---
sequenceDiagram
    participant UserBalanceRepository
    actor UserBalance
    UserBalance ->> UserBalanceRepository: persisted
    note over UserBalanceRepository: save(UserBalance)
    participant OrderRepository
    actor Order
    Order ->> OrderRepository: persisted
    note over OrderRepository: save(Order)

    loop for each OrderItemList
        actor OrderItem
        Order -->> OrderItem: join
        note over OrderItem: setOrder(Order)
    #
        actor Product
        Product -->> ProductRepository: persisted
        note over ProductRepository: save(Product)
        Product -->> OrderItem: join
    #
        OrderItem -->> OrderItemRepository: persisted
        note over OrderItemRepository: save(OrderItem)
    end

```
