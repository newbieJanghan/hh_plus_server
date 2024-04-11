package my.ecommerce.application.products;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;

import my.ecommerce.application.api.products.ProductsService;
import my.ecommerce.application.api.products.dto.popular.GetPopularProductsPageRequestParamDto;
import my.ecommerce.domain.product.PopularProductApp;
import my.ecommerce.domain.product.ProductRepository;
import my.ecommerce.domain.product.dto.CursorPagedPopularProductsQueryDto;

public class ProductsServiceRequestQueryDtoTest {

	@Captor
	private ArgumentCaptor<CursorPagedPopularProductsQueryDto> getPopularProductsParamDtoCaptor;

	@Mock
	private ProductRepository productRepository;
	@Mock
	private PopularProductApp popularProductApp;

	private ProductsService productsService;

	@BeforeEach
	void setUp() {
		openMocks(this);
		productsService = new ProductsService(productRepository, popularProductApp);
	}

	@Test
	@DisplayName("인기 상품 조회 중 도메인으로 넘길 QueryDto 변환 테스트 - from & to 기본 값 하루 차이 확인")
	void success_convertToCursorPagedPopularProductsQueryDto() {
		// given
		when(popularProductApp.getPopularProductsWithPage(any(CursorPagedPopularProductsQueryDto.class))).thenReturn(
			new PageImpl<>(List.of()));

		// when
		GetPopularProductsPageRequestParamDto requestParamDto = GetPopularProductsPageRequestParamDto.empty();
		productsService.findPopularProductsWithPage(requestParamDto);

		// then
		verify(popularProductApp).getPopularProductsWithPage(getPopularProductsParamDtoCaptor.capture());
		CursorPagedPopularProductsQueryDto queryDto = getPopularProductsParamDtoCaptor.getValue();

		LocalDateTime from = queryDto.getFrom();
		LocalDateTime to = queryDto.getTo();

		assertEquals(from.toLocalDate(), to.toLocalDate());
		assertEquals(0, from.getHour());
		assertEquals(0, from.getMinute());
		assertEquals(0, from.getSecond());
		assertEquals(23, to.getHour());
		assertEquals(59, to.getMinute());
		assertEquals(59, to.getSecond());
	}
}