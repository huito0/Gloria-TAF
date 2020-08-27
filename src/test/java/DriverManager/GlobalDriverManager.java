package DriverManager;

import CLIOptions.GlobalConfig;
import org.openqa.selenium.WebDriver;

import static Logger.Log.info;

public final class GlobalDriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private GlobalDriverManager() {
    }

    private static void setupDriver(DriverType driverType) {
        driverType.getDriverManager().setup();
        driver.set(driverType.getDriverObject());
        driver.get().manage().window().maximize();
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            info("Setting up new instance of " + GlobalConfig.getDriverType().name().toLowerCase() + " WebDriver");
            setupDriver(GlobalConfig.getDriverType());
            info("New instance of driver is ready to use");
        }
        return driver.get();
    }

    public static void destroyDriver() {
        info("Destroying instance of " + GlobalConfig.getDriverType().name().toLowerCase() + " driver");
        driver.get().quit();
        driver.set(null);
        info("Driver has been destroyed");
    }
}
