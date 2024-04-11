package my.ecommerce.application.products;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import my.ecommerce.application.api.products.ProductsService;
import my.ecommerce.application.api.products.dto.GetProductsPageRequestParamDto;
import my.ecommerce.application.api.products.dto.ProductsPageResponseDto;
import my.ecommerce.application.api.products.dto.popular.GetPopularProductsPageRequestParamDto;
import my.ecommerce.application.api.products.dto.popular.PopularProductsPageResponseDto;
import my.ecommerce.domain.product.PopularProduct;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductReader;
import my.ecommerce.domain.product.dto.CursorPagedProductsQueryDto;
import my.ecommerce.domain.product.dto.PopularProductsPeriodQueryDto;
import my.ecommerce.utils.UUIDGenerator;

public class ProductsServiceTest {
	@Mock
	private ProductReader productReader;

	private ProductsService productsService;

	@BeforeEach
	void setProductsService() {
		openMocks(this);
		productsService = new ProductsService(productReader);
	}

	@Test
	@DisplayName("SearchParamDto 를 FindParamDto 로 변환한 뒤, 커서 Page 기반의 상품 목록 조회 성공")
	void success_findProducts_withCursorBasedPage() {
		// given
		Page<Product> expect = productsPage();
		prepareReaderFindAll(expect);

		// when
		ProductsPageResponseDto result = productsService.findAllWithPage(GetProductsPageRequestParamDto.empty());

		// then
		assertEquals(expect.getTotalPages(), result.getPageInfo().getTotalPages());
		assertEquals(expect.getContent().getLast().getId().toString(), result.getPageInfo().getCursor());
		assertEquals(expect.getContent().size(), result.getData().size());
	}

	@Test
	@DisplayName("커서 다음의 목록이 없어 빈 페이지 반환")
	void success_findProducts_withCursorBasedPage_empty() {
		// given
		Page<Product> expect = new PageImpl<>(List.of());
		prepareReaderFindAll(expect);

		// when
		ProductsPageResponseDto result = productsService.findAllWithPage(GetProductsPageRequestParamDto.empty());

		// then
		assertEquals(expect.getTotalPages(), result.getPageInfo().getTotalPages());
		assertNull(result.getPageInfo().getCursor());
		assertEquals(expect.getContent().size(), result.getData().size());
	}

	@Test
	@DisplayName("인기 상품 조회 성공 - 마지막 아이템의 id를 커서 값으로 반환")
	void success_findPopularProducts() {
		// given
		Page<PopularProduct> expect = popularProductsPage();
		prepareReaderFindPopular(expect);

		// when
		PopularProductsPageResponseDto result = productsService.findPopularProductsWithPage(
			GetPopularProductsPageRequestParamDto.empty());

		// then
		assertEquals(expect.getSize(), result.getData().size());
		assertEquals(expect.getContent().getLast().getId().toString(), result.getPageInfo().getCursor());
	}

	@Test
	@DisplayName("인기 상품 조회 성공 - 빈 페이지 반환")
	void success_findPopularProducts_empty() {
		// given
		Page<PopularProduct> expect = new PageImpl<>(List.of());
		prepareReaderFindPopular(expect);

		// when
		PopularProductsPageResponseDto result = productsService.findPopularProductsWithPage(
			GetPopularProductsPageRequestParamDto.empty());

		// then
		assertEquals(expect.getTotalPages(), result.getPageInfo().getTotalPages());
		assertNull(result.getPageInfo().getCursor());
		assertEquals(expect.getContent().size(), result.getData().size());
	}

	private Page<Product> productsPage() {
		return new PageImpl<>(List.of(Product.builder().id(UUIDGenerator.generate()).build()));
	}

	private void prepareReaderFindAll(Page<Product> expect) {
		when(productReader.findAllWithPage(any(CursorPagedProductsQueryDto.class))).thenReturn(expect);
	}

	private Page<PopularProduct> popularProductsPage() {
		return new PageImpl<>(List.of(Product.builder().id(UUIDGenerator.generate()).build().toPopularProduct(0)));
	}

	private void prepareReaderFindPopular(Page<PopularProduct> expect) {
		when(productReader.getPopularProductsWithPage(
			any(CursorPagedProductsQueryDto.class), any(PopularProductsPeriodQueryDto.class)))
			.thenReturn(expect);
	}
}
