package my.ecommerce.application.page;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class PageResponse<T, P extends PageInfo> {
    List<T> data;
    P pageInfo;

    public PageResponse(List<T> data, P pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
