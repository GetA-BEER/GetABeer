package be.domain.beertag.service;

import org.springframework.stereotype.Service;

import be.domain.beertag.entity.BeerTag;
import be.domain.beertag.entity.BeerTagType;
import be.domain.beertag.repository.BeerTagRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeerTagService {

	private final BeerTagRepository beerTagRepository;

	public BeerTag findVerifiedBeerTagByBeerTagType(BeerTagType beerTagType) {
		return beerTagRepository.findBeerTagByBeerTagType(beerTagType)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BEER_CATEGORY_NOT_FOUND));
	}

	public BeerTag findVerifiedBeerTag(Long beerTagId) {
		return beerTagRepository.findById(beerTagId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BEER_CATEGORY_NOT_FOUND));
	}
}
