package be.global.init;

import static be.global.init.InitConstant.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import be.domain.beercategory.service.BeerCategoryService;
import be.domain.beertag.entity.BeerTag;
import be.domain.beertag.entity.BeerTagType;
import be.domain.beertag.repository.BeerTagRepository;
import be.domain.beertag.service.BeerTagService;
import be.domain.user.entity.User;
import be.domain.user.entity.UserBeerCategory;
import be.domain.user.entity.UserBeerTag;
import be.domain.user.entity.enums.Age;
import be.domain.user.entity.enums.Gender;
import be.domain.user.entity.enums.RandomProfile;
import be.domain.user.entity.enums.Role;
import be.domain.user.entity.enums.UserStatus;
import be.domain.user.repository.UserBeerTagRepository;
import be.domain.user.repository.UserRepository;

@Configuration
public class Init {

	private static final Logger log = LoggerFactory.getLogger(Init.class);

	private final PasswordEncoder passwordEncoder;

	public Init(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Bean
	@Transactional
	CommandLineRunner stubInit(BeerController beerController, BeerMapper beerMapper, BeerService beerService,
							   BeerRepository beerRepository, BeerTagService beerTagService, UserBeerTagRepository userBeerTagRepository,
							   BeerCategoryRepository beerCategoryRepository, UserRepository userRepository,
							   BeerTagRepository beerTagRepository, BeerBeerTagRepository beerBeerTagRepository,
							   BeerCategoryService beerCategoryService) {

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

		for (int i = 1; i <= 30; i++) {

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
		 * RATING STUB DATA
		 */
		// for (int i = 1; i <= 100; i++) {
		//
		// 	RatingRequestDto.Post ratingPost = RatingRequestDto.Post
		// 		.builder()
		// 		.beerId((long)(Math.random() * 30) + 1)
		// 		.
		//
		// 	private Long beerId;
		// 	private String nickname;
		// 	private String content;
		// 	private Double star;
		// 	private String color;
		// 	private String taste;
		// 	private String flavor;
		// 	private String carbonation;
		// }

		/*
		 * USER STUB DATA
		 */
		for (int i = 1; i <= 20; i++) {

			User user = User.builder()
				.email("e" + i + "@mail.com")
				.provider("LOCAL")
				.nickname("닉네임" + i)
				.roles(List.of(Role.ROLE_USER.toString()))
				.password(passwordEncoder.encode("password" + i + "!"))
				.status(UserStatus.ACTIVE_USER.getStatus())
				.imageUrl(RandomProfile.values()[(int)(Math.random() * 4)].getValue())
				.build();

			user.setUserInfo(Age.values()[(int)(Math.random() * 6)], Gender.values()[(int)(Math.random() * 3)]);
			user.setUserBeerTags(new ArrayList<>());

			for (int j = 0; j < 4; j++) {
				UserBeerTag userBeerTag =
					UserBeerTag.builder()
						.beerTag(beerTagService.findVerifiedBeerTag((long)(Math.random() * 16) + 1))
						.user(user)
						.build();
				user.addUserBeerTags(userBeerTag);
			}

			user.setUserBeerCategories(new ArrayList<>());

			for (int j = 0; j < 2; j++) {
				UserBeerCategory userBeerCategory =
					UserBeerCategory.builder()
						.beerCategory(beerCategoryService.findVerifiedBeerCategoryById((long)(Math.random() * 7) + 1))
						.user(user)
						.build();

				user.addUserBeerCategories(userBeerCategory);
			}
			userRepository.save(user);

		}

		return null;
	}

}
