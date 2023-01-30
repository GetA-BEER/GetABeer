package be.domain.crawler;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@Component
@RequiredArgsConstructor
public class TestCrawler {
    private WebDriver webDriver;
    @Value("${webdriver.chrome.driverid}")
    private String driverId;
    @Value("${webdriver.chrome.location}")
    private String driverPath;
    public static final String targetUrl = "https://www.ratebeer.com/beer/jeju-pellong-ale/784135";
    public static final String companyCategoryTargetSelector = "//*[@id=\"styleLink\"]";
    public static final String infoTargetSelector = "//*[@id=\"root\"]/div[2]/div[2]/div/div/div/div[2]/div[1]/div[1]";
    public static final String targetSelector = "//*[@id=\"root\"]/div[2]/div[2]/div/div/div/div[2]/div[1]/div/div[2]/div[1]/div/div[2]/div[3]/div[2]/div[2]/text()";
//    public static final String targetUrl = "https://gnidinger.tistory.com/752";
//    public static final String targetSelector = "//*[@id=\"mArticle\"]/div/div[2]/div[2]/p[1]";
//    public static final String targetUrl = "https://yourei.jp/腕を磨く";
//    public static final String targetSelector = "#sentence-example-list .sentence-list li";

    @Bean
    public void test() {

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
//        chromeOptions.addArguments("--disable-dev-shm-usage"); // CI가 구현되었거나 Docker를 사용하는 경우
        chromeOptions.addArguments("--ignore-certificate-errors"); // '안전하지 않은 페이지' 스킵
//        chromeOptions.addArguments("lang=en_US");
        chromeOptions.addArguments("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation")); // 상단 바 제거
//        chromeOptions.setExperimentalOption("useAutomationExtension", false); // 추가 확장 프로그램 설치 금지

        return new ChromeDriver(chromeOptions);
    }

    private List<String> getDataList() throws InterruptedException {

        List<String> list = new ArrayList<>();

        webDriver.get(targetUrl);
        Thread.sleep(3000);

        List<WebElement> elements = webDriver.findElements(By.xpath(infoTargetSelector));

        List<String> stringList = new ArrayList<>();

        for (WebElement webElement : elements) {
            String[] arr = webElement.getText().split("\n");
            stringList = List.of(arr[2], arr[7], arr[8], arr[10], arr[12]);
        }

        System.out.println("===============================================");
        stringList.forEach(System.out::println);
        System.out.println("===============================================");

        return list;
    }
}