package my.ecommerce.application.common;

import lombok.Getter;

import java.util.List;

@Getter
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
