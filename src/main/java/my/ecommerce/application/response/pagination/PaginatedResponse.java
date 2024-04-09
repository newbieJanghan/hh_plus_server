package my.ecommerce.application.response.pagination;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class PaginatedResponse<T, P> {
    List<T> data;
    P pageInfo;

    public PaginatedResponse(List<T> data, P pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
