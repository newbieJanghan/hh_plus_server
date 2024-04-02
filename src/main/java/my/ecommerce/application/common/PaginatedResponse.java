package my.ecommerce.application.common;

import java.util.List;

public abstract class PaginatedResponse<T, P> {
    String code;
    List<T> data;
    P pageInfo;

    public PaginatedResponse(String code, List<T> data, P pageInfo) {
        this.code = code;
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
