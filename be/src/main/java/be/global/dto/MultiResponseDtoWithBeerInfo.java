package be.global.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;

import be.domain.beer.entity.Beer;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultiResponseDtoWithBeerInfo<T> {
	private List<T> data;
	private PageInfoWithBeerInfo pageInfo;

	public MultiResponseDtoWithBeerInfo(List<T> data, Page page, Beer beer) {
		this.data = data;
		this.pageInfo = new PageInfoWithBeerInfo(page.getNumber() + 1,
			page.getSize(), page.getTotalElements(), page.getTotalPages(),
			beer.getId(), beer.getBeerDetailsBasic().getKorName(), beer.getBeerDetailsBasic().getEngName());
	}
}
