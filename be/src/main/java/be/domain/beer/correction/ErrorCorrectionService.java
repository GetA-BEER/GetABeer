package be.domain.beer.correction;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.beer.entity.Beer;
import be.domain.beer.repository.BeerQueryRepository;
import be.domain.beer.repository.BeerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ErrorCorrectionService {

	private final BeerRepository beerRepository;
	private final BeerQueryRepository beerQueryRepository;

	@Transactional
	public String correctRatings() {

		List<Beer> beerList = beerRepository.findAll();

		beerList.stream()
			// .filter(beer -> beer.getRatingList().size() != 0)
			.forEach(beer -> {

				if (beer.getRatingList().size() == 0) {
					beer.correct(0, 0.0, 0,
						0.0, 0, 0.0);
				}

				List<Long> values = beerQueryRepository.findRatingsCounts(beer);

				Integer totalRatingCount = Integer.parseInt(String.valueOf(values.get(0)));
				Integer totalFemaleRatingCount = Integer.parseInt(String.valueOf(values.get(1)));
				Integer totalMaleRatingCount = Integer.parseInt(String.valueOf(values.get(2)));

				beer.correct(totalRatingCount, totalFemaleRatingCount, totalMaleRatingCount);

				beerRepository.save(beer);
			});

		return "Corrected";
	}

	@Transactional
	public String correctStars() {

		List<Beer> beerList = beerRepository.findAll();

		beerList.stream()
			.filter(beer -> beer.getRatingList().size() != 0)
			.forEach(beer -> {
				List values = beerQueryRepository.findStarsAndCounts(beer);

				Double totalStar = (Double)values.get(0);
				Integer totalStarCount = Integer.parseInt(String.valueOf(values.get(1)));
				Double totalAverageStars = (double)(Math.round(totalStar * 100 / totalStarCount)) / 100;

				Double totalFemaleStar = (Double)values.get(2);
				Integer totalFemaleStarCount = Integer.parseInt(String.valueOf(values.get(3)));
				Double totalFemaleAverageStars =
					(double)(Math.round(totalFemaleStar * 100 / totalFemaleStarCount)) / 100;

				Double totalMaleStar = (Double)values.get(4);
				Integer totalMaleStarCount = Integer.parseInt(String.valueOf(values.get(5)));
				Double totalMaleAverageStars = (double)(Math.round(totalMaleStar * 100 / totalMaleStarCount)) / 100;

				beer.correct(totalStarCount, totalAverageStars,
					totalFemaleStarCount, totalFemaleAverageStars,
					totalMaleStarCount, totalMaleAverageStars);

				beerRepository.save(beer);
			});
		return "Corrected";
	}
}
