package my.ecommerce.application;

import lombok.Getter;

@Getter
public class Response<T> {
    private String code;
    private T data;

    public Response(String code, T data) {
        this.code = code;
        this.data = data;
    }
}
