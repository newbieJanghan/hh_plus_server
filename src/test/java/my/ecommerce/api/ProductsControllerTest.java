package my.ecommerce.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

import my.ecommerce.api.controller.ProductsController;
import my.ecommerce.api.utils.MockAuthentication;
import my.ecommerce.domain.product.ProductService;
import my.ecommerce.domain.product.dto.PeriodQuery;
import my.ecommerce.domain.product.dto.ProductPageCursorQuery;
import my.ecommerce.utils.UUIDGenerator;

@WebMvcTest(ProductsController.class)
public class ProductsControllerTest {

	@Captor
	ArgumentCaptor<ProductPageCursorQuery> queryCaptor;
	@Captor
	private ArgumentCaptor<PeriodQuery> periodQueryCaptor;

	private MockMvc mockMvc;
	@MockBean
	private ProductService productService;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(new ProductsController(productService))
			.build();
		MockAuthentication.setAuthenticatedContext();
	}

	@Test
	@DisplayName("상품 목록 조회 성공 - 기본 limit 값 적용")
	public void findProducts_success_whenLimitParamDefaultValue() throws Exception {
		// when
		when(productService.getProductPage(any(ProductPageCursorQuery.class))).thenReturn(
			new PageImpl<>(List.of()));
		// then
		mockMvc.perform(get("/api/v1/products")).andExpect(status().isOk());

		verify(productService).getProductPage(queryCaptor.capture());
		assertEquals(10, queryCaptor.getValue().getLimit());
	}

	@Test
	@DisplayName("상폼 목록 조회 성공 - 입력한 파라미터 검증")
	public void findProducts_fail_whenQueryError() throws Exception {
		// when
		when(productService.getProductPage(any(ProductPageCursorQuery.class))).thenReturn(
			new PageImpl<>(List.of()));
		UUID uuid = UUIDGenerator.generate();

		// then
		mockMvc.perform(get("/api/v1/products?size=20&category=sports&cursor=" + uuid.toString()))
			.andExpect(status().isOk());

		verify(productService).getProductPage(queryCaptor.capture());
		ProductPageCursorQuery query = queryCaptor.getValue();

		assertEquals(20, queryCaptor.getValue().getLimit());
		assertEquals(uuid, queryCaptor.getValue().getCursor());
		assertEquals("sports", queryCaptor.getValue().getCategory());
	}

	@Test
	@DisplayName("인기 상품 목룍 조회 성공 - 기본 Period Query 변환 성공")
	public void findAllPopularWithPage_success() throws Exception {
		// when
		when(
			productService.getPopularProductPage(any(ProductPageCursorQuery.class), any(PeriodQuery.class))).thenReturn(
			new PageImpl<>(List.of()));

		// then
		mockMvc.perform(get("/api/v1/products/popular"))
			.andExpect(status().isOk());

		verify(productService).getPopularProductPage(queryCaptor.capture(), periodQueryCaptor.capture());

		PeriodQuery periodQuery = periodQueryCaptor.getValue();
		LocalDateTime from = periodQuery.getFrom();
		LocalDateTime to = periodQuery.getTo();

		assertEquals(from.toLocalDate(), to.toLocalDate());
		assertEquals(0, from.getHour());
		assertEquals(0, from.getMinute());
		assertEquals(0, from.getSecond());
		assertEquals(23, to.getHour());
		assertEquals(59, to.getMinute());
		assertEquals(59, to.getSecond());
	}
}
