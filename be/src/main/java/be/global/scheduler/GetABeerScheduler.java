package be.global.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import be.domain.beer.service.BeerService;
import be.global.statistics.entity.TotalStatistics;
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
	 * 매주 목요일 00시 5분에 인기 많은 맥주 갱신
	 */
	@Scheduled(cron = "0 5 0 * * THU")
	public void createWeeklyBeer() {
		beerService.createWeeklyBeer();
	}

	/*
	 * 매일 00시 10분에 직전 일간 통계자료 생성
	 */
	@Scheduled(cron = "0 10 0 * * *")
	public void createBeerStatistics() {
		statisticsService.createBeerStatistics();
		statisticsService.createBeerCategoryStatistics();
		statisticsService.createBeerTagStatistics();
		statisticsService.createPairingCategoryStatistics();
		TotalStatistics totalStatistics = TotalStatistics.builder().build();
	}

	/*
	 * 매일 00시 00분에 직전 일간 통계자료 객체 생성
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void createTotalStatisticsObject() {
	}

}
