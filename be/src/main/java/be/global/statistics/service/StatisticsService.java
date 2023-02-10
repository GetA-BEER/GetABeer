package be.global.statistics.service;

import org.springframework.stereotype.Service;

import be.global.statistics.repository.BeerCategoryStatisticsQueryRepository;
import be.global.statistics.repository.BeerStatisticsQueryRepository;
import be.global.statistics.repository.BeerTagStatisticsQueryRepository;
import be.global.statistics.repository.PairingCategoryStatisticsQueryRepository;
import be.global.statistics.repository.TotalStatisticsQueryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticsService {
	private final TotalStatisticsQueryRepository totalStatisticsQueryRepository;
	private final BeerStatisticsQueryRepository beerStatisticsQueryRepository;
	private final BeerCategoryStatisticsQueryRepository beerCategoryStatisticsQueryRepository;
	private final BeerTagStatisticsQueryRepository beerTagStatisticsQueryRepository;
	private final PairingCategoryStatisticsQueryRepository pairingCategoryStatisticsQueryRepository;

	public void createTotalStatistics() {

	}

	public void createBeerStatistics() {
		beerStatisticsQueryRepository.createAndSaveBeerStatistics();
	}

	public void createBeerCategoryStatistics() {
		beerCategoryStatisticsQueryRepository.createAndSaveBeerCategoryStatistics();
	}

	public void createBeerTagStatistics() {
		beerTagStatisticsQueryRepository.createAndSaveBeerTagStatistics();
	}

	public void createPairingCategoryStatistics() {
		pairingCategoryStatisticsQueryRepository.createAndSaveBeerTagStatistics();
	}
}
