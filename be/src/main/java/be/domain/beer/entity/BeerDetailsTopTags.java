package be.domain.beer.entity;

import java.util.List;

import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Embeddable
@RequiredArgsConstructor
public class BeerDetailsTopTags {

	private String tag1;
	private String tag2;
	private String tag3;
	private String tag4;

	@Builder
	public BeerDetailsTopTags(String tag1, String tag2, String tag3, String tag4) {
		this.tag1 = tag1;
		this.tag2 = tag2;
		this.tag3 = tag3;
		this.tag4 = tag4;
	}

	public List<String> createList() {
		return List.of(tag1, tag2, tag3, tag4);
	}

	public void createTags(List<String> beerTagTypes) {

		BeerDetailsTopTags beerDetailsTopTags =
			BeerDetailsTopTags.builder()
				.tag1(beerTagTypes.get(0))
				.tag2(beerTagTypes.get(1))
				.tag3(beerTagTypes.get(2))
				.tag4(beerTagTypes.get(3))
				.build();
	}

	public void changeTags(List<String> beerTagTypes) {
		this.tag1 = beerTagTypes.get(0);
		this.tag2 = beerTagTypes.get(1);
		this.tag3 = beerTagTypes.get(2);
		this.tag4 = beerTagTypes.get(3);
	}
}
