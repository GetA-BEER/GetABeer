package be.global.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultiResponseDto<T> {
	private List<T> data;
	private PageInfo pageInfo;

	public MultiResponseDto(List<T> data, Page page) {
		this.data = data;
		this.pageInfo = new PageInfo(page.getNumber() + 1,
			page.getSize(), page.getTotalElements(), page.getTotalPages());
	}
}
