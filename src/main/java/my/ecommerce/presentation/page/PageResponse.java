package my.ecommerce.presentation.page;

import java.util.List;

import lombok.Getter;

@Getter
public abstract class PageResponse<T, P extends PageInfo> {
	List<T> data;
	P pageInfo;

	public PageResponse(List<T> data, P pageInfo) {
		this.data = data;
		this.pageInfo = pageInfo;
	}
}
