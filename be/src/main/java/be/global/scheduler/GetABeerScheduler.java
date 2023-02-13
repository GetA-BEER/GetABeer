package be.global.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import be.domain.beer.service.BeerService;
import be.global.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetABeerScheduler {

	private final BeerService beerService;
	private final StatisticsService statisticsService;

	/*
	 * 매달 1일 00시 5분에 이달의 맥주 갱신
	 */
	@Scheduled(cron = "0 5 0 1 * *")
	public void createMonthlyBeer() {
		beerService.createMonthlyBeer();
	}

	/*
	 * 매주 월요일 00시 10분에 직전 주간 통계자료 생성
	 */
	@Scheduled(cron = "0 10 0 * * MON")
	public void createBeerStatistics() {
		statisticsService.createBeerStatistics();
		statisticsService.createBeerCategoryStatistics();
		statisticsService.createBeerTagStatistics();
		statisticsService.createPairingCategoryStatistics();
	}
}
