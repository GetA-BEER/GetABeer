package be.global.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import be.domain.beer.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerScheduler {

	private final BeerService beerService;

	/*
	 * 매달 1일 00시 10분에 이달의 맥주 갱신
	 */
	@Scheduled(cron = "0 10 0 1 * *")
	public void createMonthlyBeer() {
		beerService.createMonthlyBeer();
	}
}
