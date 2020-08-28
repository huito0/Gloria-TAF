package DriverManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.function.Supplier;

public enum Browser {
    CHROME(WebDriverManager::chromedriver, ChromeDriver::new),
    FIREFOX(WebDriverManager::firefoxdriver, FirefoxDriver::new);

    private Supplier<WebDriverManager> driverFactory;
    private Supplier<WebDriver> driverObject;

    Browser(Supplier<WebDriverManager> driverFactory, Supplier<WebDriver> driverObject) {
        this.driverFactory = driverFactory;
        this.driverObject = driverObject;
    }

    public WebDriverManager getDriverManager() {
        return driverFactory.get();
    }

    public WebDriver getDriverObject() {
        return driverObject.get();
    }
}
