package my.ecommerce.application.products;

import my.ecommerce.application.common.pagination.CursorPageInfo;
import my.ecommerce.application.products.dto.GetProductsRequestParamDto;
import my.ecommerce.application.products.dto.PaginatedProductsResponseDto;
import my.ecommerce.application.products.dto.ProductsResponseDto;
import my.ecommerce.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
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
    }

    @Test
    @DisplayName("상품 목록 조회 성공 - 기본 limit 값 적용")
    @WithMockUser
    public void findProducts_success_whenLimitParamDefaultValue() throws Exception {
        long userId = 1;
        // when
        when(productsService.findMany(new GetProductsRequestParamDto())).thenReturn(mockPaginatedResponse());
        // then
        mockMvc.perform(get("/api/v1/products").header("Authorization", userId))
                .andExpect(status().isOk());

        verify(productsService).findMany(paramDtoCaptor.capture());
        assertEquals(10, paramDtoCaptor.getValue().getLimit());
    }

    @Test
    @DisplayName("인기 상품 목룍 조회 성공")
    @WithMockUser
    public void findPopularProducts_success() throws Exception {
        long userId = 1;

        // when
        when(productsService.findPopularProducts()).thenReturn(mockResponse());

        // then
        mockMvc.perform(get("/api/v1/products/popular").header("Authorization", userId))
                .andExpect(status().isOk());
    }


    private PaginatedProductsResponseDto mockPaginatedResponse() {
        CursorPageInfo cursorPageInfo = new CursorPageInfo();
        return new PaginatedProductsResponseDto(List.of(new Product()), cursorPageInfo);
    }

    private ProductsResponseDto mockResponse() {
        return new ProductsResponseDto(List.of(new Product()));
    }
}
