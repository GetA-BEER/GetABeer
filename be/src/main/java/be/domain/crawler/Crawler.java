//package be.domain.crawler;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.util.List;
//
//public interface Crawler {
//
//    void run();
//
//    default List<WebElement> findElementsByXPathWithWait(String location, WebDriver webDriver,
//                                                         WebDriverWait webDriverWait) {
//
//        waitDuration(location, webDriverWait);
//        return findElementsByXPath(location, webDriver);
//    }
//
//    default void waitDuration(String location, WebDriverWait webDriverWait) {
//
//        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(location)));
//    }
//
//    default WebElement findElementByXPath(String location, WebDriver webDriver) {
//
//        return webDriver.findElement(By.xpath(location));
//    }
//
//    default List<WebElement> findElementsByXPath(String location, WebDriver webDriver) {
//
//        return webDriver.findElements(By.xpath(location));
//    }
//}
