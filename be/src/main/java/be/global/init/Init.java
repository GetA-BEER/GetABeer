package be.global.init;

import static org.apache.commons.io.FileUtils.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
import be.domain.beer.entity.MonthlyBeer;
import be.domain.beer.entity.WeeklyBeer;
import be.domain.beer.entity.WeeklyBeerCategory;
import be.domain.beer.repository.BeerRepository;
import be.domain.beer.repository.MonthlyBeerRepository;
import be.domain.beer.repository.WeeklyBeerRepository;
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
import be.domain.user.repository.UserRepository;
import be.global.statistics.entity.TotalStatistics;
import be.global.statistics.repository.TotalStatisticsRepository;

@Configuration
public class Init {

	private static final Logger log = LoggerFactory.getLogger(Init.class);

	private final PasswordEncoder passwordEncoder;

	public Init(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	private final List<String> pairingCategoryList =
		List.of("FRIED", "GRILL", "STIR", "FRESH", "DRY", "SNACK", "SOUP", "ETC");

	@Bean
	@Transactional
	CommandLineRunner stubInit(BeerController beerController, BeerService beerService,
		BeerRepository beerRepository, BeerTagService beerTagService,
		BeerCategoryRepository beerCategoryRepository, UserRepository userRepository,
		BeerTagRepository beerTagRepository, TotalStatisticsRepository totalStatisticsRepository,
		BeerCategoryService beerCategoryService, MonthlyBeerRepository monthlyBeerRepository,
		WeeklyBeerRepository weeklyBeerRepository) throws IOException {

		for (int i = 0; i < 8; i++) {
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

		for (int i = 0; i < 1; i++) {
			TotalStatistics totalStatistics =
				TotalStatistics.builder()
					.totalBeerViewCount(0)
					.totalPairingCount(0)
					.totalRatingCount(0)
					.totalVisitorCount(0)
					.date(LocalDate.now())
					.build();

			totalStatisticsRepository.save(totalStatistics);
		}

		/*
		 * BEER STUB DATA
		 */

		ClassLoader classLoader = getClass().getClassLoader();

		String FILE_PATH = "src/main/java/be/global/init/Get_A_Beer_Products.csv";

		/* 자르 파일 용 ^_^ */
		// String FILE_PATH = "Get_A_Beer_Products.csv";

		List<List<String>> csvList = new ArrayList<List<String>>();

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Get_A_Beer_Products.csv");

		File csv = convertInputStreamToFile(inputStream);
		// File csv = new File(FILE_PATH);
		BufferedReader br = null;
		String line = "";

		try {
			br = new BufferedReader(new FileReader(csv));
			while ((line = br.readLine()) != null) { // readLine()은 파일에서 개행된 한 줄의 데이터를 읽어온다.
				List<String> aLine = new ArrayList<String>();
				String[] lineArr = line.split(","); // 파일의 한 줄을 ,로 나누어 배열에 저장 후 리스트로 변환한다.
				aLine = Arrays.asList(lineArr);
				csvList.add(aLine);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close(); // 사용 후 BufferedReader를 닫아준다.
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/* 자르 테스트 용은 짧게 가져가기 */
		for (int i = 1; i < 180; i++) {

			BeerDetailsStars beerDetailsStars =
				BeerDetailsStars.builder()
					.totalAverageStars((double)(int)((Math.random() * 5) * 100) / 100)
					.femaleAverageStars((double)(int)((Math.random() * 5) * 100) / 100)
					.maleAverageStars((double)(int)((Math.random() * 5) * 100) / 100)
					.build();

			List<String> list = csvList.get(i);

			BeerDto.Post.PostBuilder postBuilder = BeerDto.Post.builder();

			postBuilder.korName(list.get(0));
			postBuilder.engName(list.get(1));
			postBuilder.country(list.get(2));
			if (!list.get(4).contains("")) {
				postBuilder.beerCategories(List.of(
					BeerCategoryDto.Response.builder()
						// .beerCategoryId((long)BeerCategoryType.valueOf(list.get(3)).ordinal() + 1)
						.beerCategoryType(BeerCategoryType.valueOf(list.get(3)))
						.build(),
					BeerCategoryDto.Response.builder()
						// .beerCategoryId((long)BeerCategoryType.valueOf(list.get(4)).ordinal() + 1)
						.beerCategoryType(BeerCategoryType.valueOf(list.get(4)))
						.build()
				));
			} else {
				postBuilder.beerCategories(List.of(
					BeerCategoryDto.Response.builder()
						// .beerCategoryId((long)BeerCategoryType.valueOf(list.get(3)).ordinal() + 1)
						.beerCategoryType(BeerCategoryType.valueOf(list.get(3)))
						.build()));
			}
			if (list.get(5).isEmpty()) {
				postBuilder.abv(0.0);
			} else {
				postBuilder.abv(Double.valueOf(list.get(5)));
			}
			if (!list.get(6).isBlank()) {
				postBuilder.ibu(Integer.valueOf(list.get(6)));
			}
			postBuilder.thumbnail(list.get(7));

			beerController.postBeer(postBuilder.build());

			Beer findBeer = beerService.findVerifiedBeer((long)i);

			findBeer.addBeerDetailsStars(beerDetailsStars);

			findBeer.getBeerDetailsStatistics()
				.updateBestPairingCategory(pairingCategoryList.get((int)((Math.random() * 7))));

			beerRepository.save(findBeer);
		}

		/*
		 * MONTHLY BEER STUB DATA
		 */
		for (int i = 0; i < 5; i++) {
			Long rand = (long)(Math.random() * 9 + 1);

			Beer findBeer = beerService.findVerifiedBeer(rand);

			MonthlyBeer monthlyBeer = MonthlyBeer.builder().build();

			monthlyBeer.create(findBeer);

			monthlyBeerRepository.save(monthlyBeer);
		}

		/*
		 * WEEKLY BEER STUB DATA
		 */
		for (int i = 0; i < 5; i++) {
			Long rand = (long)(Math.random() * 179 + 1);

			Beer findBeer = beerService.findVerifiedBeer(rand);

			WeeklyBeerCategory weeklyBeerCategory = new WeeklyBeerCategory("ALE", "DUNKEL");

			WeeklyBeer.WeeklyBeerBuilder weeklyBeer = WeeklyBeer.builder();

			weeklyBeer.beerId(findBeer.getId());
			weeklyBeer.korName(findBeer.getBeerDetailsBasic().getKorName());
			weeklyBeer.country(findBeer.getBeerDetailsBasic().getCountry());
			weeklyBeer.thumbnail(findBeer.getBeerDetailsBasic().getThumbnail());
			weeklyBeer.weeklyBeerCategory(weeklyBeerCategory);
			weeklyBeer.abv(findBeer.getBeerDetailsBasic().getAbv());
			weeklyBeer.ibu(findBeer.getBeerDetailsBasic().getIbu());
			weeklyBeer.averageStar(findBeer.getBeerDetailsStars().getTotalAverageStars());

			weeklyBeerRepository.save(weeklyBeer.build());
		}

		/*
		 * BEER STUB DATA
		 */
		// for (int i = 1; i <= 30; i++) {
		//
		// 	int rand7 = (int)(Math.random() * 7);
		//
		// 	BeerCategoryDto.Response beerCategoryDto =
		// 		BeerCategoryDto.Response.builder()
		// 			.beerCategoryId((long)rand7 + 1)
		// 			.beerCategoryType(BeerCategoryType.values()[rand7])
		// 			.build();
		//
		// 	BeerDto.Post postBeer =
		// 		BeerDto.Post.builder()
		// 			.korName("한글 이름" + i)
		// 			.engName("EngName" + i)
		// 			.country("Germany")
		// 			.beerCategories(List.of(beerCategoryDto))
		// 			.thumbnail("썸네일 이미지 경로" + i)
		// 			.abv(4.5)
		// 			.ibu(20)
		// 			.build();
		//
		// 	BeerDetailsStars beerDetailsStars =
		// 		BeerDetailsStars.builder()
		// 			.totalAverageStars((double)(int)((Math.random() * 5) * 100) / 100)
		// 			.femaleAverageStars((double)(int)((Math.random() * 5) * 100) / 100)
		// 			.maleAverageStars((double)(int)((Math.random() * 5) * 100) / 100)
		// 			.build();
		//
		// 	beerController.postBeer(postBeer);
		//
		// 	Beer findBeer = beerService.findVerifiedBeer((long)i);
		// 	findBeer.addBeerDetailsCounts(BEER_DETAILS_COUNTS);
		// 	findBeer.addBeerDetailsStars(beerDetailsStars);
		//
		// 	beerRepository.save(findBeer);
		// }

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

			user.putUserInfo(Age.values()[(int)(Math.random() * 6)], Gender.values()[(int)(Math.random() * 3)]);
			user.putUserBeerTags(new ArrayList<>());

			for (int j = 0; j < 4; j++) {
				UserBeerTag userBeerTag =
					UserBeerTag.builder()
						.beerTag(beerTagService.findVerifiedBeerTag((long)(Math.random() * 16) + 1))
						.user(user)
						.build();
				user.addUserBeerTags(userBeerTag);
			}

			user.putUserBeerCategories(new ArrayList<>());

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

	public static File convertInputStreamToFile(InputStream inputStream) throws IOException {

		File tempFile = File.createTempFile(String.valueOf(inputStream.hashCode()), ".csv");
		tempFile.deleteOnExit();

		copyInputStreamToFile(inputStream, tempFile);

		return tempFile;
	}

}
