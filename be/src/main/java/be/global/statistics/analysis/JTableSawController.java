package be.global.statistics.analysis;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.global.statistics.repository.BeerCategoryStatisticsQueryRepository;
import be.global.statistics.repository.BeerStatisticsQueryRepository;
import be.global.statistics.repository.BeerTagStatisticsQueryRepository;
import be.global.statistics.repository.BeerTagStatisticsRepository;
import be.global.statistics.repository.PairingCategoryStatisticsQueryRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/jtable")
@RequiredArgsConstructor
public class JTableSawController {
	private final JTableSawService jTableSawService;
	private final BeerTagStatisticsQueryRepository beerTagStatisticsQueryRepository;
	private final BeerStatisticsQueryRepository beerStatisticsQueryRepository;
	private final PairingCategoryStatisticsQueryRepository pairingCategoryStatisticsQueryRepository;
	private final BeerCategoryStatisticsQueryRepository beerCategoryStatisticsQueryRepository;

	@GetMapping("/test")
	public void test() {
		beerStatisticsQueryRepository.createAndSaveBeerStatistics();
	}

	@GetMapping("/statistics")
	public void jTableSawTest() throws IOException {
		jTableSawService.statistics();
	}

	@GetMapping("/visualization")
	public void visualization() throws IOException {
		jTableSawService.visualization();
	}

	@GetMapping("/scatterplot")
	public void scatterPlot() throws IOException {
		jTableSawService.scatterPlot();
	}
}
