package be.domain.beercategory.service;

import org.springframework.stereotype.Service;

import be.domain.beercategory.entity.BeerCategory;
import be.domain.beercategory.entity.BeerCategoryType;
import be.domain.beercategory.repository.BeerCategoryRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeerCategoryService {
	private final BeerCategoryRepository beerCategoryRepository;

	public BeerCategory findVerifiedBeerCategory(BeerCategoryType beerCategoryType) {

		return beerCategoryRepository.findBeerCategoryByBeerCategoryType(beerCategoryType)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BEER_CATEGORY_NOT_FOUND));
	}
}
