package be.global.init;

import static be.global.init.InitConstant.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import be.domain.beer.controller.BeerController;
import be.domain.beer.dto.BeerDto;
import be.domain.beer.entity.Beer;
import be.domain.beer.entity.BeerDetailsStars;
import be.domain.beer.mapper.BeerMapper;
import be.domain.beer.repository.BeerBeerTagRepository;
import be.domain.beer.repository.BeerRepository;
import be.domain.beer.service.BeerService;
import be.domain.beercategory.dto.BeerCategoryDto;
import be.domain.beercategory.entity.BeerCategory;
import be.domain.beercategory.entity.BeerCategoryType;
import be.domain.beercategory.repository.BeerCategoryRepository;
import be.domain.beertag.entity.BeerTag;
import be.domain.beertag.entity.BeerTagType;
import be.domain.beertag.repository.BeerTagRepository;
import be.domain.beertag.service.BeerTagService;
import be.domain.user.entity.User;
import be.domain.user.entity.enums.Age;
import be.domain.user.entity.enums.Gender;
import be.domain.user.entity.enums.Role;
import be.domain.user.repository.UserBeerTagRepository;
import be.domain.user.repository.UserRepository;

@Configuration
public class Init {

	private static final Logger log = LoggerFactory.getLogger(Init.class);

	@Bean
	@Transactional
	CommandLineRunner stubInit(BeerController beerController, BeerMapper beerMapper, BeerService beerService,
		BeerRepository beerRepository, BeerTagService beerTagService, UserBeerTagRepository userBeerTagRepository,
		BeerCategoryRepository beerCategoryRepository, UserRepository userRepository,
		BeerTagRepository beerTagRepository, BeerBeerTagRepository beerBeerTagRepository) {

		for (int i = 0; i < 7; i++) {
			BeerCategory beerCategory = BeerCategory.builder()
				.beerCategoryType(BeerCategoryType.values()[i])
				.build();
			beerCategoryRepository.save(beerCategory);
		}

		for (int i = 0; i < 16; i++) {
			BeerTag beerTag = BeerTag.builder()
				.beerTagType(BeerTagType.values()[i])
				.build();
			beerTagRepository.save(beerTag);
		}

		/*
		 * BEER STUB DATA
		 */

		for (int i = 1; i <= 50; i++) {

			int rand7 = (int)(Math.random() * 7);

			BeerCategoryDto.Response beerCategoryDto =
				BeerCategoryDto.Response.builder()
					.beerCategoryId((long)rand7 + 1)
					.beerCategoryType(BeerCategoryType.values()[rand7])
					.build();

			BeerDto.Post postBeer =
				BeerDto.Post.builder()
					.korName("한글 이름" + i)
					.engName("EngName" + i)
					.country("Germany")
					.beerCategories(List.of(beerCategoryDto))
					.thumbnail("썸네일 이미지 경로" + i)
					.abv(4.5)
					.ibu(20)
					.build();

			BeerDetailsStars beerDetailsStars =
				BeerDetailsStars.builder()
					.totalAverageStars((double)(int)((Math.random() * 5) * 10) / 10)
					.femaleAverageStars((double)(int)((Math.random() * 5) * 10) / 10)
					.maleAverageStars((double)(int)((Math.random() * 5) * 10) / 10)
					.build();

			beerController.postBeer(postBeer);

			Beer findBeer = beerService.findVerifiedBeer((long)i);
			findBeer.addBeerDetailsCounts(BEER_DETAILS_COUNTS);
			findBeer.addBeerDetailsStars(beerDetailsStars);

			beerRepository.save(findBeer);
		}

		/*
		 * USER STUB DATA
		 */
		for (int i = 1; i <= 20; i++) {

			User user = User.builder()
				.email("e" + i + "@mail.com")
				.nickname("닉네임" + i)
				.password("1234")
				.gender(Gender.values()[(int)(Math.random() * 3)])
				.age(Age.values()[(int)(Math.random() * 6)])
				.roles(List.of(Role.ROLE_USER.toString()))
				.build();

			// UserBeerTag userBeerTag =
			// 	UserBeerTag.builder()
			// 		.beerTag(beerTagService.findVerifiedBeerTag((long)(Math.random() * 16) + 1))
			// 		.user(user)
			// 		.build();
			//
			// userBeerTagRepository.save(userBeerTag);

			userRepository.save(user);

		}

		return null;
	}

}
