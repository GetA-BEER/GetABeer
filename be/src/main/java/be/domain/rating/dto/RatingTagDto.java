package be.domain.rating.dto;

import be.domain.beertag.entity.BeerTagType;
import lombok.Builder;
import lombok.Getter;

public class RatingTagDto {
	@Getter
	public static class Response {
		private BeerTagType color;
		private BeerTagType taste;
		private BeerTagType flavor;
		private BeerTagType carbonation;

		@Builder
		public Response(BeerTagType color, BeerTagType taste, BeerTagType flavor, BeerTagType carbonation) {
			this.color = color;
			this.taste = taste;
			this.flavor = flavor;
			this.carbonation = carbonation;
		}
	}
}
