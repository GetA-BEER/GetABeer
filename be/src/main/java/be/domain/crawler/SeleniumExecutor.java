package be.domain.crawler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Collections;

@Slf4j
//@Configuration
@RequiredArgsConstructor
public class SeleniumExecutor {

    @Value("${webdriver.chrome.driverid}")
    private final String WEB_DRIVER_ID;
    @Value("${webdriver.chrome.location}")
    private final String CHROME_DRIVER_PATH;

    public WebDriver initWebDriver() {

        System.setProperty(WEB_DRIVER_ID, CHROME_DRIVER_PATH);

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
//        chromeOptions.addArguments("--disable-dev-shm-usage"); // CI가 구현되었거나 Docker를 사용하는 경우
        chromeOptions.addArguments("--ignore-certificate-errors"); // '안전하지 않은 페이지' 스킵
        chromeOptions.addArguments("lang=en_US");
        chromeOptions.addArguments("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation")); // 상단 바 제거
        chromeOptions.setExperimentalOption("useAutomationExtension", false); // 추가 확장 프로그램 설치 금지

        return new ChromeDriver(chromeOptions);
    }

    /*
     * 웹 페이지 로딩 완료될 때까지 대기시간 명시적으로 정하기
     */
    public WebDriverWait webDriverWait() {
        return new WebDriverWait(initWebDriver(), Duration.ofSeconds(5L));
    }
}
