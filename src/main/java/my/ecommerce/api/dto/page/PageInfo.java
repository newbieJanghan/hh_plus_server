package my.ecommerce.api.dto.page;

public interface PageInfo {
	int getSize();

	int getTotalCounts();

	int getCurrentPage();

	int getTotalPages();
}
