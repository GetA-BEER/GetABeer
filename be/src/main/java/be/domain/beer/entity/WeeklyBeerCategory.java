package be.domain.beer.entity;

import java.util.List;

import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Embeddable
@RequiredArgsConstructor
public class WeeklyBeerCategory {

	private String category1;
	private String category2;

	public void addCategory(String category) {
		this.category1 = category;
	}

	public void addCategories(List<String> categoryList) {
		this.category1 = categoryList.get(0);
		this.category2 = categoryList.get(1);
	}

	public WeeklyBeerCategory(String category1, String category2) {
		this.category1 = category1;
		this.category2 = category2;
	}

	public List<String> createList() {
		if (this.category2.isEmpty()) {
			return List.of(this.category1);
		} else {
			return List.of(this.category1, this.category2);
		}
	}
}
