package my.ecommerce.presentation.dto.page;

public interface PageInfo {
	int getSize();

	int getTotalCounts();

	int getCurrentPage();

	int getTotalPages();
}
