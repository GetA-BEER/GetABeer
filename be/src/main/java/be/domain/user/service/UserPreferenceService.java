package be.domain.user.service;

import org.springframework.stereotype.Service;

import be.domain.beercategory.entity.BeerCategory;
import be.domain.beercategory.service.BeerCategoryService;
import be.domain.beertag.entity.BeerTag;
import be.domain.beertag.service.BeerTagService;
import be.domain.user.entity.User;
import be.domain.user.entity.UserBeerCategory;
import be.domain.user.entity.UserBeerTag;
import be.domain.user.repository.UserBeerCategoryQRepository;
import be.domain.user.repository.UserBeerCategoryRepository;
import be.domain.user.repository.UserBeerTagQRepository;
import be.domain.user.repository.UserBeerTagRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPreferenceService {

	private final BeerTagService beerTagService;
	private final BeerCategoryService beerCategoryService;
	private final UserBeerTagRepository userBeerTagRepository;
	private final UserBeerTagQRepository userBeerTagQRepository;
	private final UserBeerCategoryRepository userBeerCategoryRepository;
	private final UserBeerCategoryQRepository userBeerCategoryQRepository;

	/* set UserBeerTags */
	protected void setUserBeerTags(User post, User user) {
		if (user.getUserBeerTags() != null) {
			userBeerTagQRepository.delete(user.getId());
		}

		post.getUserBeerTags().forEach(userBeerTag -> {
			BeerTag beerTag =
				beerTagService.findVerifiedBeerTagByBeerTagType(userBeerTag.getBeerTag().getBeerTagType());
			UserBeerTag saved = UserBeerTag.builder()
				.user(user)
				.beerTag(beerTag)
				.build();
			userBeerTagRepository.save(saved);
		});
	}

	/* set BeerCategories */
	protected void setUserBeerCategories(User post, User user) {
		if (user.getUserBeerCategories() != null) {
			userBeerCategoryQRepository.delete(user.getId());
		}

		post.getUserBeerCategories().forEach(userBeerCategory -> {
			BeerCategory beerCategory =
				beerCategoryService.findVerifiedBeerCategory(userBeerCategory.getBeerCategory().getBeerCategoryType());
			UserBeerCategory saved = UserBeerCategory.builder()
				.user(user)
				.beerCategory(beerCategory)
				.build();
			userBeerCategoryRepository.save(saved);
		});
	}
}
