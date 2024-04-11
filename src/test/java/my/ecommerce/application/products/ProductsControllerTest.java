package my.ecommerce.application.products;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import my.ecommerce.application.api.products.ProductsController;
import my.ecommerce.application.api.products.ProductsService;
import my.ecommerce.application.api.products.dto.GetProductsPageRequestParamDto;
import my.ecommerce.application.api.products.dto.ProductsPageResponseDto;
import my.ecommerce.application.api.products.dto.popular.GetPopularProductsPageRequestParamDto;
import my.ecommerce.application.api.products.dto.popular.PopularProductsPageResponseDto;
import my.ecommerce.application.utils.MockAuthentication;

@WebMvcTest(ProductsController.class)
public class ProductsControllerTest {

	@Captor
	ArgumentCaptor<GetProductsPageRequestParamDto> paramDtoCaptor;

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
		when(productsService.findAllWithPage(any(GetProductsPageRequestParamDto.class))).thenReturn(
			ProductsPageResponseDto.empty());
		// then
		mockMvc.perform(get("/api/v1/products")).andExpect(status().isOk());

		verify(productsService).findAllWithPage(paramDtoCaptor.capture());
		assertEquals(10, paramDtoCaptor.getValue().getSize());
	}

	@Test
	@DisplayName("상폼 목록 조회 성공 - 입력한 파라미터 검증")
	public void findProducts_fail_whenQueryError() throws Exception {
		// when
		when(productsService.findAllWithPage(any(GetProductsPageRequestParamDto.class))).thenReturn(
			ProductsPageResponseDto.empty());

		// then
		mockMvc.perform(get("/api/v1/products?size=20&category=sports"))
			.andExpect(status().isOk());

		verify(productsService).findAllWithPage(paramDtoCaptor.capture());
		assertEquals(20, paramDtoCaptor.getValue().getSize());
		assertEquals("sports", paramDtoCaptor.getValue().getCategory());
	}

	@Test
	@DisplayName("인기 상품 목룍 조회 성공")
	public void findPopularProductsWithPage_success() throws Exception {
		// when
		when(productsService.findPopularProductsWithPage(any(GetPopularProductsPageRequestParamDto.class))).thenReturn(
			PopularProductsPageResponseDto.empty());

		// then
		mockMvc.perform(get("/api/v1/products/popular"))
			.andExpect(status().isOk());
	}
}
