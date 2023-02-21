package be.domain.beercategory.entity;

public enum BeerCategoryType {
	ALE(),
	LAGER(),
	WEIZEN(),
	DUNKEL(),
	PILSNER(),
	FRUIT(),
	NON_ALCOHOLIC(),
	ETC();

	@Override
	public String toString() {
		return super.toString();
	}

	public Integer toInteger(BeerCategoryType beerCategoryType) {
		return beerCategoryType.ordinal();
	}
}
