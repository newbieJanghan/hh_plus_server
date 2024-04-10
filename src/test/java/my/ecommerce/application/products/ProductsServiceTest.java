package my.ecommerce.application.products;

import my.ecommerce.application.api.products.ProductsService;
import my.ecommerce.application.api.products.dto.GetProductsPageRequestParamDto;
import my.ecommerce.application.api.products.dto.ProductsPageResponseDto;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductRepository;
import my.ecommerce.domain.product.dto.CursorPagedProductsQueryDto;
import my.ecommerce.utils.UUIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class ProductsServiceTest {
    @Mock
    private ProductRepository productRepository;

    private ProductsService productsService;

    @BeforeEach
    void setProductsService() {
        openMocks(this);
        productsService = new ProductsService(productRepository);
    }

    @Test
    @DisplayName("SearchParamDto 를 FindParamDto 로 변환한 뒤, 커서 Page 기반의 상품 목록 조회 성공")
    void success_findProducts_withCursorBasedPage() {
        // given
        UUID lastProductId = UUIDGenerator.generate();
        List<Product> productList = List.of(Product.builder().id(lastProductId).build());
        Page<Product> resultPage = new PageImpl<>(productList);

        GetProductsPageRequestParamDto requestParamDto = GetProductsPageRequestParamDto.empty();

        when(productRepository.findAllWithPage(any(CursorPagedProductsQueryDto.class))).thenReturn(resultPage);

        // when
        ProductsPageResponseDto responseDto = productsService.findAllWithPage(requestParamDto);

        // then
        assertEquals(1, responseDto.getPageInfo().getTotalPages());
        assertEquals(lastProductId.toString(), responseDto.getPageInfo().getCursor());
        assertEquals(1, responseDto.getData().size());
    }

    @Test
    @DisplayName("커서 다음의 목록이 없어 빈 페이지 반환")
    void success_findProducts_withCursorBasedPage_empty() {
        // given
        Page<Product> resultPage = new PageImpl<>(List.of());

        GetProductsPageRequestParamDto requestParamDto = GetProductsPageRequestParamDto.empty();

        when(productRepository.findAllWithPage(any(CursorPagedProductsQueryDto.class))).thenReturn(resultPage);

        // when
        ProductsPageResponseDto responseDto = productsService.findAllWithPage(requestParamDto);

        // then
        assertEquals(0, responseDto.getPageInfo().getTotalPages());
        assertNull(responseDto.getPageInfo().getCursor());
        assertEquals(0, responseDto.getData().size());
    }
}
