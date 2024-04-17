package my.ecommerce.domain.product.page;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;

import my.ecommerce.domain.product.popular.PeriodQuery;
import my.ecommerce.presentation.request.page.PopularProductsPageRequest;
import my.ecommerce.presentation.request.page.ProductsPageRequest;
import my.ecommerce.utils.UUIDGenerator;

public class ProductPageServiceTest {
	@Captor
	private ArgumentCaptor<ProductPageCursorQuery> cursorQueryCaptor;
	@Captor
	private ArgumentCaptor<PeriodQuery> periodQueryCaptor;

	@Mock
	private ProductPageRepository productPageRepository;

	private ProductPageService productsService;

	@BeforeEach
	void setProductsService() {
		openMocks(this);
		productsService = new ProductPageService(productPageRepository);
	}

	@Test
	@DisplayName("CursorPageQuery Dto로 맵핑되어 String 커서가 UUID 로 변환되어야 함")
	void success_mappingRequestParam_toCursorQueryDto() {
		// given
		prepareRepositoryFindAllProduct();

		// when
		ProductsPageRequest requestParams = new ProductsPageRequest(10, null,
			UUIDGenerator.generate().toString(), null);
		productsService.findAllWithPage(requestParams);

		// then
		verify(productPageRepository).findAllWithPage(cursorQueryCaptor.capture());
		ProductPageCursorQuery queryDto = cursorQueryCaptor.getValue();

		assertInstanceOf(ProductPageCursorQuery.class, queryDto);
		assertInstanceOf(UUID.class, queryDto.getCursor());
	}

	@Test
	@DisplayName("인기 상품 조회 중 도메인으로 넘길 QueryDto 변환 테스트 - from & to 기본 값 하루 차이 확인")
	void success_convertToCursorPagedPopularProductsQueryDto() {
		// given
		prepareRepositoryFindAllPopularProduct();

		// when
		PopularProductsPageRequest requestParams = new PopularProductsPageRequest(10, null,
			UUIDGenerator.generate().toString(), null, null, null);
		productsService.findAllPopularWithPage(requestParams);

		// then
		verify(productPageRepository).findAllPopularWithPage(cursorQueryCaptor.capture(),
			periodQueryCaptor.capture());
		PeriodQuery queryDto = periodQueryCaptor.getValue();

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

	private void prepareRepositoryFindAllProduct() {
		when(productPageRepository.findAllWithPage(any(ProductPageCursorQuery.class))).thenReturn(
			new PageImpl<>(List.of()));
	}

	private void prepareRepositoryFindAllPopularProduct() {
		when(productPageRepository.findAllPopularWithPage(any(ProductPageCursorQuery.class),
			any(PeriodQuery.class)))
			.thenReturn(new PageImpl<>(List.of()));
	}
}
