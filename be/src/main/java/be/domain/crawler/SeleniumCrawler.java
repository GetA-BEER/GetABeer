//package be.domain.crawler;
//
//import static be.domain.crawler.SeleniumConstant.*;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//public class SeleniumCrawler {
//
//    private final WebDriver webDriver;
//    private final WebDriverWait webDriverWait;
//    private final CrawledInfoRepository crawledInfoRepository;
//
//    public void crawl() {
//
//        log.info("Crawling Init");
//
//        webDriver.get(START_URL);
//
//        findElementByXPath(SEARCH_BOX, webDriver).sendKeys("Kirin ichiban");
//        findElementByXPath(FIRST_RESULT, webDriver).click();
//        List<WebElement> beerInfoElement = findElementsByXPathWithWait(BEER_INFO_XPATH, webDriver, webDriverWait);
//        CrawledInfo crawledInfo = createBeerInfo(beerInfoElement);
//
//        crawledInfoRepository.save(crawledInfo);
//    }
//
//    public CrawledInfo createBeerInfo(List<WebElement> beerInfoElement) {
//
//        String[] arr = beerInfoElement.get(0).getText().split("\n");
//
//        return CrawledInfo.builder()
//                .engName(arr[2])
//                .country(arr[7])
//                .beerCategory(arr[8])
//                .abv(arr[10])
//                .ibu(arr[12])
//                .build();
//    }
//
//    public List<WebElement> findElementsByXPathWithWait(String location, WebDriver webDriver,
//                                                         WebDriverWait webDriverWait) {
//
//        waitDuration(location, webDriverWait);
//        return findElementsByXPath(location, webDriver);
//    }
//
//    public void waitDuration(String location, WebDriverWait webDriverWait) {
//
//        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(location)));
//    }
//
//    public WebElement findElementByXPath(String location, WebDriver webDriver) {
//
//        return webDriver.findElement(By.xpath(location));
//    }
//
//    public List<WebElement> findElementsByXPath(String location, WebDriver webDriver) {
//
//        return webDriver.findElements(By.xpath(location));
//    }
//}
