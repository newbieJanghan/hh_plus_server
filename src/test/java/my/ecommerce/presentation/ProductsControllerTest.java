package my.ecommerce.presentation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import my.ecommerce.domain.product.ProductPageService;
import my.ecommerce.presentation.controller.ProductsController;
import my.ecommerce.presentation.request.page.PopularProductsPageRequestParams;
import my.ecommerce.presentation.request.page.ProductsPageRequestParams;
import my.ecommerce.presentation.utils.MockAuthentication;

@WebMvcTest(ProductsController.class)
public class ProductsControllerTest {

	@Captor
	ArgumentCaptor<ProductsPageRequestParams> paramDtoCaptor;

	private MockMvc mockMvc;
	@MockBean
	private ProductPageService productsService;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(new ProductsController(productsService)).build();
		MockAuthentication.setAuthenticatedContext();
	}

	@Test
	@DisplayName("상품 목록 조회 성공 - 기본 limit 값 적용")
	public void findProducts_success_whenLimitParamDefaultValue() throws Exception {
		// when
		when(productsService.findAllWithPage(any(ProductsPageRequestParams.class))).thenReturn(
			new PageImpl<>(List.of()));
		// then
		mockMvc.perform(get("/api/v1/products")).andExpect(status().isOk());

		verify(productsService).findAllWithPage(paramDtoCaptor.capture());
		assertEquals(10, paramDtoCaptor.getValue().getSize());
	}

	@Test
	@DisplayName("상폼 목록 조회 성공 - 입력한 파라미터 검증")
	public void findProducts_fail_whenQueryError() throws Exception {
		// when
		when(productsService.findAllWithPage(any(ProductsPageRequestParams.class))).thenReturn(
			new PageImpl<>(List.of()));

		// then
		mockMvc.perform(get("/api/v1/products?size=20&category=sports"))
			.andExpect(status().isOk());

		verify(productsService).findAllWithPage(paramDtoCaptor.capture());
		assertEquals(20, paramDtoCaptor.getValue().getSize());
		assertEquals("sports", paramDtoCaptor.getValue().getCategory());
	}

	@Test
	@DisplayName("인기 상품 목룍 조회 성공")
	public void findAllPopularWithPage_success() throws Exception {
		// when
		when(productsService.findAllPopularWithPage(any(PopularProductsPageRequestParams.class))).thenReturn(
			new PageImpl<>(List.of()));

		// then
		mockMvc.perform(get("/api/v1/products/popular"))
			.andExpect(status().isOk());
	}
}