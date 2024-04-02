package my.ecommerce.domain.product.dto;

public interface FindManyProductsParam {
    long getLimit();

    long getCursor();

    String getSort();

    String getCategory();
}
