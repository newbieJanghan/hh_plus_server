package my.ecommerce.application.products;

import my.ecommerce.Mocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductsController.class)
public class ProductsControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private ProductsService productsService;


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductsController(productsService)).build();
    }

    @Test
    @DisplayName("상품 목록 조회 성공")
    @WithMockUser
    public void findProducts_success() throws Exception {
        long userId = 1;
        // when
        when(productsService.findMany()).thenReturn(Mocks.mockProductsResponseDto());
        // then
        mockMvc.perform(get("/api/v1/products").header("Authorization", userId))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.pageInfo.total").exists());
    }

}
