package my.ecommerce.application.common.dto;

import lombok.Getter;

@Getter
public abstract class Response<T> {
    String code;
    T data;

    public Response(String code, T data) {
        this.code = code;
        this.data = data;
    }
}
