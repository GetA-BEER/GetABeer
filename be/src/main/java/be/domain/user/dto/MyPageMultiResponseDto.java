package be.domain.user.dto;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;

import be.global.dto.PageInfo;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyPageMultiResponseDto<T> {

	private String nickname;
	private List<T> data;
	private PageInfo pageInfo;

	public MyPageMultiResponseDto(String nickname, List<T> data, Page page) {
		this.nickname = nickname;
		this.data = data;
		this.pageInfo = new PageInfo(page.getNumber() + 1,
			page.getSize(), page.getTotalElements(), page.getTotalPages());
	}
}
