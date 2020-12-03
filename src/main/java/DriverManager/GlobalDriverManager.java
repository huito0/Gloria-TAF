package DriverManager;

import CLIOptions.GlobalConfig;
import Logger.Log;
import Utils.Environment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static Logger.Log.info;

public final class GlobalDriverManager {
    private static WebDriver driver = null;

    private GlobalDriverManager() {
    }

    private static void setupDriver(Browser browser) {
        browser.getDriverManager().setup();
        if (GlobalConfig.getEnvironment().equals(Environment.LOCALHOST)) {
            driver = browser.getDriverObject();
        } else {
            try {
                driver = new RemoteWebDriver(new URL(GlobalConfig.getHub()), GlobalConfig.getChromeOptions());
            } catch (MalformedURLException e) {
                Log.warn(e.getMessage());
            }
        }
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            info("Setting up new instance of " + GlobalConfig.getBrowserType().name().toLowerCase() + " WebDriver");
            setupDriver(GlobalConfig.getBrowserType());
            info("New instance of driver is ready to use");
        }
        return driver;
    }

    public static void destroyDriver() {
        if (driver != null) {
            info("Destroying instance of " + GlobalConfig.getBrowserType().name().toLowerCase() + " driver");
            driver.quit();
            driver = null;
            info("Driver has been destroyed");
        } else {
            info("Driver isn't opened, so nothing to close");
        }
    }
}
