# 상품 API

## 상품 목록 조회

### API 명세

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
            "data": {
                "items": [
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
                    "totalPages": 1,
                    "order": {
                        "createdAt": "desc"
                    }
                }   
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

### 플로우 차트

```mermaid
sequenceDiagram
    participant client
    box Application
        participant ProductsController
        participant ProductsService
    end
    box Domain
        participant ProductReader
        participant ProductRepository
    end
#
    client ->> ProductsController: GET /api/v1/products
    ProductsController ->> ProductsService: search(SearchRequestParamDto)
    ProductsService ->> ProductReader: searchAndCount(searchParamDto)
#
    ProductReader ->> ProductRepository: findAllByAndCount(cursor, size, sort, direction, category)
    ProductRepository -->> ProductReader: { List<Product>, count }
#
    ProductReader -->> ProductsService: { List<Product>, count }
    ProductsService -->> ProductsController: PaginatedProductsResponse
    ProductsController -->> client: 200 OK, PaginatedProductsResponse
```

## 인기 상품 조회

### API 명세

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
            "data": {
                "items": [
                    {
                        "id": 1,
                        "name": "상품1",
                        "price": 1000,
                        "stock": 10,
                        "sellCountByDaily": 1
                    }
                ],
                "metadata": {
                    "rankedAt": "2024-04-04T00:00:00Z"
                },
                "pageInfo": {
                    "cursor": 1,
                    "size": 10,
                    "total": 1,
                    "totalPages": 1,
                    "order": {
                        "sellCountByDaily": "desc"
                    }
                }
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
    - 422 Unprocessable Entity: 아직 집계가 완료되지 않은 경우
        ```json
        {
            "code": "UNPROCESSABLE_ENTITY",
            "message": "batching is not completed yet"
        }
        ```  

### 플로우 차트

```mermaid
sequenceDiagram
    participant client
    box Application
        participant ProductsController
        participant ProductsService
    end
#
    box PopularProductDomain
        participant PopularProductReader
        participant DailyProductRankRepository
    end

#
    client ->> ProductsController: GET /api/v1/products/popular
    ProductsController ->> ProductsService: getPopularProducts(RequestParamDto)
    ProductsService ->> PopularProductReader: getWithCount(ParamDto)
#
    PopularProductReader ->> DailyProductRankRepository: findAllAndCount(date, limit)
    DailyProductRankRepository -->> PopularProductReader: { List<DailyProductRank>, count }
    PopularProductReader --> PopularProductReader: makePopularProductList(List<DailyProductRank>)
    PopularProductReader -->> ProductsService: { List<PopularProduct>, count }
    ProductsService -->> ProductsController: PaginatedPopularProductsResponse
    ProductsController -->> client: 200 OK, PaginatedPopularProductsResponse
```