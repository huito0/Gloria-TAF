package Utils;

import CLIOptions.GlobalConfig;
import DriverManager.GlobalDriverManager;
import Logger.Log;
import io.cucumber.core.api.Scenario;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    private static final String SCREENSHOTS_DIR_PATH = GlobalConfig.getEnvironment().getOuputPath() + "/screenshots/";
    private static Scenario scenario;

    public static void setScenario(Scenario scenario) {
        ScreenshotUtil.scenario = scenario;
    }

    public static void makeScreenShot(ShootingStrategy shootingStrategy, String fileNamePrefix) {
        try {
            Screenshot screenshot = new AShot().shootingStrategy(shootingStrategy).takeScreenshot(GlobalDriverManager.getDriver());
            File screenshotsDir = new File(SCREENSHOTS_DIR_PATH);

            if (screenshotsDir.exists() || screenshotsDir.mkdir()) {
                String fileName = SCREENSHOTS_DIR_PATH + fileNamePrefix + "-" + new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date()) + ".png";
                File screenshotFile = new File(fileName);
                ImageIO.write(screenshot.getImage(), "png", screenshotFile);
                scenario.embed(Files.readAllBytes(screenshotFile.toPath()), "image/png", fileNamePrefix);
            } else {
                Log.warn("Sorry couldn’t create specified directory");
            }
        } catch (IOException e) {
             Log.warn(e.getMessage());
        }
    }
}
