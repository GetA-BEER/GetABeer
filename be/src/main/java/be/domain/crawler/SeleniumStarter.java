package be.domain.crawler;

import static be.domain.crawler.SeleniumConstant.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Component
@RequiredArgsConstructor
public class SeleniumStarter {
	private WebDriver webDriver;
	private WebDriverWait webDriverWait;
	private final CrawledInfoRepository crawledInfoRepository;
	@Value("${webdriver.chrome.driverid}")
	private String driverId;
	@Value("${webdriver.chrome.location}")
	private String driverPath;

	@Bean
	public void run() {

		System.setProperty(driverId, driverPath);

		webDriver = initWebDriver();

		try {
			crawl();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		webDriver.close();
		webDriver.quit();
	}

	@Bean
	public WebDriver initWebDriver() {

		System.setProperty(driverId, driverPath);

		ChromeOptions chromeOptions = new ChromeOptions();

		/*
		 * 속도 향상을 위한 크롬 옵션
		 */
		chromeOptions.addArguments("headless"); // 브라우저 실행 안 함
		chromeOptions.addArguments("--no-sandbox"); // Linux에서 headless 사용시 필요함
		chromeOptions.addArguments("--disable-gpu"); // Linux에서 headless 사용시 필요함
		chromeOptions.addArguments("--start-maximized"); // 최대 크기로 시작
		chromeOptions.addArguments("--window-size=1920,1080"); // 해상도
		chromeOptions.addArguments("--disable-extensions"); // 확장 프로그램 사용 안 함
		chromeOptions.addArguments("--disable-popup-blocking"); // 팝업 비활성화
		chromeOptions.addArguments("--disable-dev-shm-usage"); // CI가 구현되었거나 Docker를 사용하는 경우
		chromeOptions.addArguments("--ignore-certificate-errors"); // '안전하지 않은 페이지' 스킵
		chromeOptions.addArguments("lang=en_US");
		chromeOptions.addArguments(
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) "
				+ "Chrome/109.0.0.0 Safari/537.36");
		chromeOptions.setExperimentalOption("excludeSwitches",
			Collections.singletonList("enable-automation")); // 상단 바 제거
		//        chromeOptions.setExperimentalOption("useAutomationExtension", false); // 추가 확장 프로그램 설치 금지

		return new ChromeDriver(chromeOptions);
	}

	@Bean
	public WebDriverWait setWebDriverWait() {
		return new WebDriverWait(initWebDriver(), Duration.ofSeconds(3));
	}

	private void crawl() throws InterruptedException {

		log.info("Crawling Begins");

		List<CrawledInfo> crawledInfos = createCrawledInfo();

		crawledInfoRepository.saveAll(crawledInfos);

	}

	public List<CrawledInfo> createCrawledInfo() throws InterruptedException {

		List<CrawledInfo> crawledInfos = new ArrayList<>();

		webDriver.get(START_URL);

		Thread.sleep(5000);

		for (String engName : TEST_LIST) {

			WebElement webElement;

			try {
				webElement = webDriver.findElement(By.xpath(SEARCH_BOX));
			} catch (NoSuchElementException e) {
				break;
			}

			String selectAll = Keys.chord(Keys.COMMAND, "Test");
			webElement.sendKeys(selectAll);
			Thread.sleep(1000);
			webElement.sendKeys(engName);
			Thread.sleep(2000);
			webElement.sendKeys(Keys.TAB, Keys.TAB, Keys.ENTER);
			Thread.sleep(5000);

			List<WebElement> beerInfoElement = webDriver.findElements(By.xpath(BEER_INFO_XPATH));

			if (beerInfoElement.size() < 12) {
				continue;
			}

			CrawledInfo crawledInfo = createBeerInfo(beerInfoElement);
			crawledInfos.add(crawledInfo);
		}

		return crawledInfos;
	}

	public CrawledInfo createBeerInfo(List<WebElement> beerInfoElement) {

		List<String> list = new ArrayList<>();

		for (WebElement webElement : beerInfoElement) {
			list = List.of(webElement.getText().split("\n"));
		}

		return CrawledInfo.builder()
			.engName(list.get(2))
			.country(list.get(7))
			.beerCategory(list.get(8))
			.abv(list.get(10))
			.ibu(list.get(12))
			.build();
	}
}
