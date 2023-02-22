package be.domain.beer.entity;

import java.util.List;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Embeddable
@RequiredArgsConstructor
public class MonthlyBeerCategory {

	private String category1;
	private String category2;

	public void addCategory(String category) {
		this.category1 = category;
	}

	public void addCategories(List<String> categoryList) {
		this.category1 = categoryList.get(0);
		this.category2 = categoryList.get(1);
	}
}
