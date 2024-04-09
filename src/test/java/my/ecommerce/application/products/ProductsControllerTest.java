package my.ecommerce.application.products;

import my.ecommerce.application.api.products.ProductsController;
import my.ecommerce.application.api.products.ProductsService;
import my.ecommerce.application.api.products.dto.GetProductsRequestParamDto;
import my.ecommerce.application.api.products.dto.PaginatedProductsResponseDto;
import my.ecommerce.application.api.products.dto.ProductResponseDto;
import my.ecommerce.application.response.pagination.CursorPageInfo;
import my.ecommerce.application.utils.MockAuthentication;
import my.ecommerce.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductsController.class)
public class ProductsControllerTest {

    @Captor
    ArgumentCaptor<GetProductsRequestParamDto> paramDtoCaptor;

    private MockMvc mockMvc;
    @MockBean
    private ProductsService productsService;


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductsController(productsService)).build();
        MockAuthentication.setAuthenticatedContext();
    }

    @Test
    @DisplayName("상품 목록 조회 성공 - 기본 limit 값 적용")
    public void findProducts_success_whenLimitParamDefaultValue() throws Exception {
        // when
        when(productsService.findMany(new GetProductsRequestParamDto())).thenReturn(emptyResponseDto());
        // then
        mockMvc.perform(get("/api/v1/products")).andExpect(status().isOk());

        verify(productsService).findMany(paramDtoCaptor.capture());
        assertEquals(10, paramDtoCaptor.getValue().getLimit());
    }

    @Test
    @DisplayName("상폼 목록 조회 실패 - Query 오류")
    public void findProducts_fail_whenQueryError() throws Exception {
        // then
        mockMvc.perform(get("/api/v1/products?limit=0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("인기 상품 목룍 조회 성공")
    public void findPopularProducts_success() throws Exception {
        // when
        when(productsService.findPopularProducts()).thenReturn(emptyResponseDto());

        // then
        mockMvc.perform(get("/api/v1/products/popular"))
                .andExpect(status().isOk());
    }


    private PaginatedProductsResponseDto emptyResponseDto() {
        ProductResponseDto productResponseDto = new ProductResponseDto(new Product());
        CursorPageInfo cursorPageInfo = new CursorPageInfo();
        return new PaginatedProductsResponseDto(List.of(productResponseDto), cursorPageInfo);
    }
}
