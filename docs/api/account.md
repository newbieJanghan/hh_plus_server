# 잔액 API

## 잔액 조회

### API 명세

- Request
    - Method: GET
    - URL: /api/v1/account
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
    client ->> server: GET /api/v1/account
    server ->> db: USER_BALANCE에 userId로 조회
    alt USER_BALANCE 없음
        db -->> server: null
        server ->> db: USER 존재하는지 확인
        alt USER 없음
            db -->> server: null
            server -->> client: 404 Not Found User
        else USER 있음
            db -->> server: User
            server ->> db: USER_BALANCE 생성
        end
    else USER_BALANCE 있음
        db -->> server: UserBalance
    end

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
        participant UserRepository
    end
#
    BalanceController ->> BalanceService: myBalance(userId)
    BalanceService ->> UserBalanceRepository: findOneByUserId(userId)
    alt UserBalance NotFound
        UserBalanceRepository -->> BalanceService: null
        BalanceService ->> UserRepository: findOneByUserId(userId)
        alt User NotFound
            UserRepository -->> BalanceService: null
            BalanceService -->> BalanceController: 404 Not Found User
        else User Found
            UserRepository -->> BalanceService: User
            Note over BalanceService: UserBalance.initialize()
            BalanceService ->> UserBalanceRepository: save(UserBalance)
        end
    else UserBalance Found
        UserBalanceRepository -->> BalanceService: UserBalance
    end

#
    BalanceService -->> BalanceController: UserBalanceResponse
```

## 잔액 충전

### API 명세

- Request
    - Method: POST
    - URL: /api/v1/account/charge
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
    client ->> server: POST /api/v1/account/charge
    server ->> db: USER_BALANCE 에 userId로 조회
    alt USER_BALANCE 없음
        db -->> server: null
        server ->> db: USER 존재하는지 확인
        alt USER 없음
            db -->> server: null
            server -->> client: 404 Not Found User
        else USER 있음
            db -->> server: User
            server ->> db: 충전할 금액으로 새로운 USER_BALANCE 생성
        end
    else USER_BALANCE 있음
        db -->> server: UserBalance
        server -->> db: 충전할 금액으로 USER_BALANCE 업데이트
    end
    server ->> db: USER_BALANCE_HISTORY 생성
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
        participant UserRepository
        participant UserBalanceHistoryRepository
    end

    BalanceController ->> BalanceService: charge(userId, amount)
    BalanceService ->> UserBalanceApp: chargeToUser(userId, amount)
#
    UserBalanceApp ->> UserBalanceRepository: findOneByUserId(userId)
    alt UserBalance NotFound
        UserBalanceRepository -->> UserBalanceApp: null
        UserBalanceApp ->> UserRepository: findOneByUserId(userId)
        alt User NotFound
            UserRepository -->> UserBalanceApp: null
            UserBalanceApp -->> BalanceController: User Not Found Exception
        else User Found
            UserRepository -->> UserBalanceApp: User
            Note over UserBalanceApp: UserBalance.initialize().amount(amount)
            UserBalanceApp ->> UserBalanceRepository: save(UserBalance)
        end
    else UserBalance Found
        UserBalanceRepository -->> UserBalanceApp: UserBalance
        Note over UserBalanceApp: UserBalance.charge(amount)
        UserBalanceApp ->> UserBalanceRepository: save(UserBalance)
    end
    UserBalanceApp ->> UserBalanceHistoryRepository: save(UserBalanceHistory)
    UserBalanceApp -->> BalanceService: UserBalance
    BalanceService -->> BalanceController: UserBalanceResponse
```