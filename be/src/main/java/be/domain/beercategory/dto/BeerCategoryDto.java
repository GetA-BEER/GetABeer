package be.domain.beercategory.dto;

import javax.validation.constraints.NotBlank;

import be.domain.beercategory.entity.BeerCategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BeerCategoryDto {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {

		private Long beerCategoryId;
		@NotBlank
		private BeerCategoryType beerCategoryType;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class BeerResponse {
		@NotBlank
		private BeerCategoryType beerCategoryType;
	}
}
