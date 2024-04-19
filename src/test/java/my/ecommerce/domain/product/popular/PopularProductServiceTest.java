package my.ecommerce.domain.product.popular;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;

import my.ecommerce.domain.product.page.ProductPageCursorQuery;
import my.ecommerce.api.dto.request.page.PopularProductPageRequest;
import my.ecommerce.utils.UUIDGenerator;

public class PopularProductServiceTest {
	@Captor
	private ArgumentCaptor<ProductPageCursorQuery> cursorQueryCaptor;
	@Captor
	private ArgumentCaptor<PeriodQuery> periodQueryCaptor;

	@Mock
	private PopularProductRepository repository;

	private PopularProductService service;

	@Test
	@DisplayName("인기 상품 조회 중 도메인으로 넘길 QueryDto 변환 테스트 - from & to 기본 값 하루 차이 확인")
	void success_convertToCursorPagedPopularProductsQueryDto() {
		// given
		when(repository.findPopularWithPage(any(ProductPageCursorQuery.class),
			any(PeriodQuery.class)))
			.thenReturn(new PageImpl<>(List.of()));

		// when
		PopularProductPageRequest requestParams = new PopularProductPageRequest(10, null,
			UUIDGenerator.generate().toString(), null, null, null);
		service.findPopularWithPage(requestParams);

		// then
		verify(repository).findPopularWithPage(cursorQueryCaptor.capture(),
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
}
