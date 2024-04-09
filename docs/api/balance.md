# 잔액 API

## 잔액 조회

### API 명세

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
            "id": "uuid",
            "userId": "uuid",
            "balance": 0
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

### 플로우 차트

```mermaid
sequenceDiagram
    participant client
    participant server
    participant db
    client ->> server: GET /api/v1/balance
    server ->> db: USER_BALANCE에 userId로 조회
    opt not found
        db -->> server: null
        server -->> client: 404 Not Found User
    end
    db -->> server: UserBalance
    server -->> client: 200 OK, UserBalanceResponse

```

#### server

```mermaid
sequenceDiagram
    box Application
        participant BalanceController
        participant BalanceService
    end
    box Domain
        participant UserBalanceRepository
    end
#
    BalanceController ->> BalanceService: myBalance(userId)
    BalanceService ->> UserBalanceRepository: findOneByUserId(userId)
    opt UserNotFoundException
        UserBalanceRepository -->> BalanceService: foreign key insert exception
        BalanceService -->> BalanceController: 404 Not Found User
    end
    UserBalanceRepository -->> BalanceService: UserBalance
#
    BalanceService -->> BalanceController: UserBalanceResponse
```

## 잔액 충전

### API 명세

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
            "id": "uuid",
            "userId": "uuid",
            "balance": 0
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

### 플로우 차트

```mermaid
sequenceDiagram
    participant client
    participant server
    participant db
    client ->> server: POST /api/v1/balance/charge
    server ->> db: USER_BALANCE에 userId로 조회
    opt not found
        db -->> server: foreign key insert exception
        server -->> client: 404 Not Found User
    end
    db -->> server: UserBalance
    server ->> db: update(UserBalance)
    db -->> server: UserBalance
    server -->> client: 200 OK, UserBalanceResponse
```

#### Server

```mermaid
sequenceDiagram
    box Application
        participant BalanceController
        participant BalanceService
    end

    box Domain
        participant UserBalanceApp
        participant UserBalanceRepository
        participant UserBalanceHistoryRepository
    end

    BalanceController ->> BalanceService: charge(userId, amount)
    BalanceService ->> UserBalanceApp: chargeToUser(userId, amount)
#
    UserBalanceApp ->> UserBalanceRepository: findOneByUserId(userId)
    opt UserNotFoundException
        UserBalanceRepository -->> UserBalanceApp: foreign key insert exception
        UserBalanceApp -->> BalanceController: 404 Not Found User
    end
    UserBalanceRepository -->> UserBalanceApp: UserBalance
    Note over UserBalanceApp: UserBalance.charge(amount)
    UserBalanceApp ->> UserBalanceRepository: save(UserBalance)
    UserBalanceRepository -->> UserBalanceApp: UserBalance
    UserBalanceApp ->> UserBalanceHistoryRepository: save(UserBalanceHistory)

#
    UserBalanceApp -->> BalanceService: UserBalance
    BalanceService -->> BalanceController: UserBalanceResponse
```