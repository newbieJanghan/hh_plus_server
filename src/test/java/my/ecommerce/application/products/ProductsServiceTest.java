package my.ecommerce.application.products;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.util.List;
import java.util.UUID;

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
import my.ecommerce.domain.product.PopularProductApp;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductRepository;
import my.ecommerce.domain.product.dto.CursorPagedPopularProductsQueryDto;
import my.ecommerce.domain.product.dto.CursorPagedProductsQueryDto;
import my.ecommerce.utils.UUIDGenerator;

public class ProductsServiceTest {
	@Mock
	private ProductRepository productRepository;
	@Mock
	private PopularProductApp popularProductApp;

	private ProductsService productsService;

	@BeforeEach
	void setProductsService() {
		openMocks(this);
		productsService = new ProductsService(productRepository, popularProductApp);
	}

	@Test
	@DisplayName("SearchParamDto 를 FindParamDto 로 변환한 뒤, 커서 Page 기반의 상품 목록 조회 성공")
	void success_findProducts_withCursorBasedPage() {
		// given
		UUID lastProductId = UUIDGenerator.generate();
		List<Product> productList = List.of(Product.builder().id(lastProductId).build());
		Page<Product> expect = new PageImpl<>(productList);

		when(productRepository.findAllWithPage(any(CursorPagedProductsQueryDto.class))).thenReturn(expect);

		// when
		GetProductsPageRequestParamDto requestParamDto = GetProductsPageRequestParamDto.empty();
		ProductsPageResponseDto result = productsService.findAllWithPage(requestParamDto);

		// then
		assertEquals(1, result.getPageInfo().getTotalPages());
		assertEquals(lastProductId.toString(), result.getPageInfo().getCursor());
		assertEquals(1, result.getData().size());
	}

	@Test
	@DisplayName("커서 다음의 목록이 없어 빈 페이지 반환")
	void success_findProducts_withCursorBasedPage_empty() {
		// given
		Page<Product> expect = new PageImpl<>(List.of());
		when(productRepository.findAllWithPage(any(CursorPagedProductsQueryDto.class))).thenReturn(expect);

		// when
		GetProductsPageRequestParamDto requestParamDto = GetProductsPageRequestParamDto.empty();
		ProductsPageResponseDto result = productsService.findAllWithPage(requestParamDto);

		// then
		assertEquals(0, result.getPageInfo().getTotalPages());
		assertNull(result.getPageInfo().getCursor());
		assertEquals(0, result.getData().size());
	}

	@Test
	@DisplayName("인기 상품 조회 성공")
	void success_findPopularProducts() {
		// given
		UUID lastProductId = UUIDGenerator.generate();
		Product product = Product.builder().id(lastProductId).build();
		int soldAmountInPeriod = 10;

		PopularProduct popularProduct = product.toPopularProduct(soldAmountInPeriod);
		Page<PopularProduct> expect = new PageImpl<>(List.of(popularProduct));
		when(popularProductApp.getPopularProductsWithPage(any(CursorPagedPopularProductsQueryDto.class))).thenReturn(
			expect);

		// when
		GetPopularProductsPageRequestParamDto requestParamDto = GetPopularProductsPageRequestParamDto.empty();
		PopularProductsPageResponseDto result = productsService.findPopularProductsWithPage(requestParamDto);

		// then
		assertEquals(1, result.getData().size());
		assertEquals(lastProductId.toString(), result.getPageInfo().getCursor());
		assertEquals(1, result.getData().size());
	}

	@Test
	@DisplayName("인기 상품 조회 - 커서 다음의 인기 상품이 없어 빈 페이지 반환")
	void success_findPopularProducts_empty() {
		// given
		Page<PopularProduct> expect = new PageImpl<>(List.of());
		when(popularProductApp.getPopularProductsWithPage(any(CursorPagedPopularProductsQueryDto.class))).thenReturn(
			expect);

		// when
		GetPopularProductsPageRequestParamDto requestParamDto = GetPopularProductsPageRequestParamDto.empty();
		PopularProductsPageResponseDto result = productsService.findPopularProductsWithPage(requestParamDto);

		// then
		assertEquals(0, result.getPageInfo().getTotalPages());
		assertNull(result.getPageInfo().getCursor());
		assertEquals(0, result.getData().size());
	}
}
