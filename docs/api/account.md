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
            "account": 0
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
        db -->> server: Account
    end

    server -->> client: 200 OK, AccountResponse

```

#### server

```mermaid
sequenceDiagram
    box Application
        participant AccountController
        participant AccountService
    end
    box Domain
        participant AccountRepository
        participant UserRepository
    end
#
    AccountController ->> AccountService: myAccount(userId)
    AccountService ->> AccountRepository: findOneByUserId(userId)
    alt Account NotFound
        AccountRepository -->> AccountService: null
        AccountService ->> UserRepository: findOneByUserId(userId)
        alt User NotFound
            UserRepository -->> AccountService: null
            AccountService -->> AccountController: 404 Not Found User
        else User Found
            UserRepository -->> AccountService: User
            Note over AccountService: Account.initialize()
            AccountService ->> AccountRepository: save(Account)
        end
    else Account Found
        AccountRepository -->> AccountService: Account
    end

#
    AccountService -->> AccountController: AccountResponse
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
            "account": 0
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
        db -->> server: Account
        server -->> db: 충전할 금액으로 USER_BALANCE 업데이트
    end
    server ->> db: USER_BALANCE_HISTORY 생성
    server -->> client: 200 OK, AccountResponse
```

#### Server

```mermaid
sequenceDiagram
    box Application
        participant AccountController
        participant AccountService
    end

    box Domain
        participant AccountApp
        participant AccountRepository
        participant UserRepository
        participant AccountHistoryRepository
    end

    AccountController ->> AccountService: charge(userId, amount)
    AccountService ->> AccountApp: chargeToUser(userId, amount)
#
    AccountApp ->> AccountRepository: findOneByUserId(userId)
    alt Account NotFound
        AccountRepository -->> AccountApp: null
        AccountApp ->> UserRepository: findOneByUserId(userId)
        alt User NotFound
            UserRepository -->> AccountApp: null
            AccountApp -->> AccountController: User Not Found Exception
        else User Found
            UserRepository -->> AccountApp: User
            Note over AccountApp: Account.initialize().amount(amount)
            AccountApp ->> AccountRepository: save(Account)
        end
    else Account Found
        AccountRepository -->> AccountApp: Account
        Note over AccountApp: Account.charge(amount)
        AccountApp ->> AccountRepository: save(Account)
    end
    AccountApp ->> AccountHistoryRepository: save(AccountHistory)
    AccountApp -->> AccountService: Account
    AccountService -->> AccountController: AccountResponse
```