package be.domain.crawler;

import static be.domain.crawler.SeleniumConstant.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
//@Component
@RequiredArgsConstructor
public class SeleniumSetup {
    private WebDriver webDriver;
    //    private final SeleniumCrawler seleniumCrawler;
//    private final WebDriverWait webDriverWait;
    private final CrawledInfoRepository crawledInfoRepository;
    @Value("${webdriver.chrome.driverid}")
    private String driverId;
    @Value("${webdriver.chrome.location}")
    private String driverPath;
//    public static final String targetUrl = "https://www.ratebeer.com/beer/stella-artois/1478";
//    public static final String targetUrl = "https://www.ratebeer.com/beer/jeju-pellong-ale/784135";
    public static final String companyCategoryTargetSelector = "//*[@id=\"styleLink\"]";
//    public static final String infoTargetSelector = "//*[@id=\"root\"]/div[2]/div[2]/div/div/div/div[2]/div[1]/div[1]";
//    public static final String targetSelector = "//*[@id=\"root\"]/div[2]/div[2]/div/div/div/div[2]/div[1]/div/div[2]/div[1]/div/div[2]/div[3]/div[2]/div[2]/text()";


    @Bean
    public void crawl() {

        System.setProperty(driverId, driverPath);

        webDriver = initWebDriver();

        try {
            getDataList();
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
//        chromeOptions.addArguments("headless"); // 브라우저 실행 안 함
        chromeOptions.addArguments("--no-sandbox"); // Linux에서 headless 사용시 필요함
        chromeOptions.addArguments("--disable-gpu"); // Linux에서 headless 사용시 필요함
        chromeOptions.addArguments("--start-maximized"); // 최대 크기로 시작
        chromeOptions.addArguments("--window-size=1920,1080"); // 해상도
        chromeOptions.addArguments("--disable-extensions"); // 확장 프로그램 사용 안 함
        chromeOptions.addArguments("--disable-popup-blocking"); // 팝업 비활성화
        chromeOptions.addArguments("--disable-dev-shm-usage"); // CI가 구현되었거나 Docker를 사용하는 경우
        chromeOptions.addArguments("--ignore-certificate-errors"); // '안전하지 않은 페이지' 스킵
//        chromeOptions.addArguments("lang=en_US");
        chromeOptions.addArguments("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation")); // 상단 바 제거
//        chromeOptions.setExperimentalOption("useAutomationExtension", false); // 추가 확장 프로그램 설치 금지

        return new ChromeDriver(chromeOptions);
    }

    @Bean
    public WebDriverWait setWebDriverWait() {
        return new WebDriverWait(initWebDriver(), Duration.ofSeconds(3));
    }

    private List<String> getDataList() throws InterruptedException {

        List<String> list = new ArrayList<>();

        webDriver.get(START_URL);
//        webDriver.get("https://www.ratebeer.com/search?q=Kirin+ichiban&tab=beer");

//        List<WebElement> elements = webDriver.findElements(By.xpath(BEER_INFO_XPATH));
//
//        List<String> stringList = new ArrayList<>();
//
//        System.out.println(elements.get(0).getText());

//        for (WebElement webElement : elements) {
//            String[] arr = webElement.getText().split("\n");
//            stringList = List.of(arr[2], arr[7], arr[8], arr[10], arr[12]);
//        }
//
//        System.out.println("===============================================");
//        stringList.forEach(System.out::println);
//        System.out.println("===============================================");
//
//        CrawledInfo crawledInfo = CrawledInfo.builder()
//                .engName(stringList.get(0))
//                .country(stringList.get(1))
//                .beerCategory(stringList.get(2))
//                .abv(stringList.get(3))
//                .ibu(stringList.get(4))
//                .build();




//        findElementByXPath("//*[@id=\"root\"]/div[2]/div[2]/div/div/div/div[2]/div[2]/div[1]", webDriver).click();


        WebElement webElement = findElementByXPath(SEARCH_BOX, webDriver);
        Thread.sleep(1000);
        webElement.sendKeys("Kirin ichiban");
        Thread.sleep(1000);
        webElement.sendKeys(Keys.TAB, Keys.TAB, Keys.ENTER);
        Thread.sleep(1000);
//        findElementByXPath("//*[@id=\"root\"]/div[2]/div[2]/div/div/div/div[2]/div[2]/div[1]/a/div[2]/div[1]", webDriver).click();
//        findElementsByXPath("//*[@id=\"root\"]/div[2]/div[2]/div/div/div/div[2]/div[2]/div[1]/a", webDriver);


        Thread.sleep(5000);
//        findElementByXPath(FIRST_RESULT, webDriver).click();
//        List<WebElement> beerInfoElement = findElementsByXPathWithWait(BEER_INFO_XPATH, webDriver, setWebDriverWait());
        List<WebElement> beerInfoElement = webDriver.findElements(By.xpath(BEER_INFO_XPATH));
        CrawledInfo crawledInfo = createBeerInfo(beerInfoElement);

//        CrawledInfo crawledInfo = createBeerInfo(elements);

        crawledInfoRepository.save(crawledInfo);








        return list;
    }

//    @Bean
//    public void run() {
//        seleniumCrawler.crawl();
//    }

//    @Bean
//    public void getDataList() throws InterruptedException {
//
//        log.info("Crawling Init");
//
//        webDriver.get(START_URL);

//        WebElement webElement = findElementByXPath(SEARCH_BOX, webDriver);
//        webElement.sendKeys("Kirin ichiban");
//        webElement.sendKeys(Keys.TAB);
//        webElement.sendKeys(Keys.TAB);
//        webElement.sendKeys(Keys.ENTER);
//        findElementByXPath(FIRST_RESULT, webDriver).click();
//        List<WebElement> beerInfoElement = findElementsByXPathWithWait(BEER_INFO_XPATH, webDriver, setWebDriverWait());
//        List<WebElement> beerInfoElement = webDriver.findElements(By.xpath(BEER_INFO_XPATH));
//        CrawledInfo crawledInfo = createBeerInfo(beerInfoElement);
//
//        crawledInfoRepository.save(crawledInfo);
//    }

    public CrawledInfo createBeerInfo(List<WebElement> beerInfoElement) {

        List<String> list = new ArrayList<>();

        for (WebElement webElement : beerInfoElement) {
            String[] arr = webElement.getText().split("\n");
            list = List.of(arr[2], arr[7], arr[8], arr[10], arr[12]);
        }

//        String[] arr = beerInfoElement.get(0).getText().split("\n");

        return CrawledInfo.builder()
                .engName(list.get(0))
                .country(list.get(1))
                .beerCategory(list.get(2))
                .abv(list.get(3))
                .ibu(list.get(4))
                .build();

//        return CrawledInfo.builder()
//                .engName(arr[2])
//                .country(arr[7])
//                .beerCategory(arr[8])
//                .abv(arr[10])
//                .ibu(arr[12])
//                .build();
    }

    public List<WebElement> findElementsByXPathWithWait(String location, WebDriver webDriver,
                                                        WebDriverWait webDriverWait) {

        waitDuration(location, webDriverWait);
        return findElementsByXPath(location, webDriver);
    }

    public void waitDuration(String location, WebDriverWait webDriverWait) {

        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(location)));
    }

    public WebElement findElementByXPath(String location, WebDriver webDriver) {

        return webDriver.findElement(By.xpath(location));
    }

    public List<WebElement> findElementsByXPath(String location, WebDriver webDriver) {

        return webDriver.findElements(By.xpath(location));
    }

}