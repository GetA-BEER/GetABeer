package be.global.statistics.service;

import org.springframework.stereotype.Service;

import be.global.statistics.repository.BeerCategoryTypeStatisticsQueryRepository;
import be.global.statistics.repository.BeerStatisticsQueryRepository;
import be.global.statistics.repository.BeerTagTypeStatisticsQueryRepository;
import be.global.statistics.repository.PairingCategoryStatisticsQueryRepository;
import be.global.statistics.repository.TotalStatisticsQueryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticsService {
	private final TotalStatisticsQueryRepository totalStatisticsQueryRepository;
	private final BeerStatisticsQueryRepository beerStatisticsQueryRepository;
	private final BeerCategoryTypeStatisticsQueryRepository beerCategoryTypeStatisticsQueryRepository;
	private final BeerTagTypeStatisticsQueryRepository beerTagTypeStatisticsQueryRepository;
	private final PairingCategoryStatisticsQueryRepository pairingCategoryStatisticsQueryRepository;

	public void createTotalStatistics() {
		beerStatisticsQueryRepository.createAndSaveDailyBeerStatistics();

	}

	public void createBeerStatistics() {

	}

	public void createBeerCategoryTypeStatistics() {

	}

	public void createBeerTagTypeStatistics() {

	}

	public void createPairingCategoryStatistics() {

	}
}
