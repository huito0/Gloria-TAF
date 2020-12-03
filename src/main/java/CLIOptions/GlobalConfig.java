package CLIOptions;

import DriverManager.Browser;
import Utils.Environment;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static Logger.Log.info;
import static Logger.Log.warn;

public final class GlobalConfig {
    public static String PROJECT_NAME;
    public static final String DOWNLOADING_PATH = new File("Downloads").getAbsolutePath();

    private static Browser browserType = null;
    private static int timeout = 0;
    private static List<String> tags = new ArrayList<>();
    private static List<String> glues = null;
    private static List<String> plugins = null;
    private static List<String> argOptionsList = null;
    private static String featurePath = null;
    private static String hub = null;
    private static Environment environment = null;

    private GlobalConfig() {
    }

    public static void parseOptions(List<String> args) {
        for (int i = 0; i < args.size() - 1; i++) {
            switch (args.get(i)) {
                case "--browser":
                    browserType = Browser.valueOf(args.get(++i).toUpperCase());
                    info("Driver type is " + browserType.name());
                    break;
                case "--timeout":
                    timeout = Integer.parseInt(args.get(++i));
                    info("Timeout is " + timeout);
                    break;
                case "--tags":
                    tags = Arrays.asList(args.get(++i).split(","));
                    info("tags are " + tags);
                    break;
                case "--glue":
                    glues = Arrays.asList(args.get(++i).split(","));
                    break;
                case "--plugin":
                    plugins = Arrays.asList(args.get(++i).split(","));
                    break;
                case "--features":
                    featurePath = args.get(++i);
                    break;
                case "--chromeOptions":
                    argOptionsList = Arrays.asList(args.get(++i).split(","));
                    break;
                case "--hub":
                    hub = args.get(++i);
                    break;
                case "--environment":
                    environment = Environment.valueOf(args.get(++i).toUpperCase());
                    break;
                case "--host":
                    setHostAddress(args.get(++i));
                    break;
                default:
                    throw new RuntimeException("Unexpected program argument " + args.get(i));
            }
        }
    }

    private static void setHostAddress(String address) {
        getEnvironment().setHostAddress(address);
    }

    public static Browser getBrowserType() {
        if (browserType == null) {
            warn("You didn't set [--driver] option in program args, so CHROME will be used by default");
            browserType = Browser.CHROME;
        }
        return browserType;
    }

    public static int getTimeout() {
        if (timeout == 0) {
            info("You didn't set [--timeout] option in program args, so 30 sec timeout will be used by default");
            timeout = 30;
        }
        return timeout;
    }

    /**
     * That method returns time in 10 times less then just getTimeout
     * @return getTimeout() / 10;
     */
    public static int getShortTimeout() {
        return getTimeout() / 10;
    }

    public static List<String> getTags() {
        if (tags.size() == 0) {
            info("You didn't set [--tags] option in program args, so [@Regression] tag will be used by default");
            tags.add("@Regression");
        }
        return tags;
    }

    public static List<String> getGlues() {
        if (glues.size() == 0) {
            warn("You have to set glue path!");
            throw new RuntimeException("No glue path has been set");
        }
        return glues;
    }

    public static List<String> getPlugins() {
        if (plugins == null) {
            info("No one plugin was set, only default plugins will be used");
        }
        return plugins;
    }

    public static String getFeaturePath() {
        if (featurePath == null) {
            warn("You have to set feature path!");
            throw new RuntimeException("No feature path has been set");
        }
        return featurePath;
    }

    public static String getHub() {
        if (hub == null) {
            hub = "http://localhost:4444/wd/hub";
            info("Local hub will be used on default address: " + hub);
        }
        return hub;
    }

    public static Environment getEnvironment() {
        if (environment == null) {
            warn("You have to set environment");
            throw new RuntimeException("No environment has been set");
        }
        return environment;
    }

    public static ChromeOptions getChromeOptions() {
        if (argOptionsList == null) {
            argOptionsList = new ArrayList<>();
            info("No any specific options have been specified, so only application mode will be used");
            argOptionsList.add("--app=" + getHub());
        }

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", DOWNLOADING_PATH);

        ChromeOptions options = new ChromeOptions();

        //By default small screen resolution uses in headless mode, but not all applications work with it correctly
        if (argOptionsList.stream().anyMatch(a -> a.contains("headless"))) {
            options.addArguments("window-size=1920,1080");
        }

        options.setExperimentalOption("prefs", prefs);
        argOptionsList.forEach(options::addArguments);

        return options;
    }

    public static String getFormattedCurrentDateAndTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy");
        LocalDateTime localDateTime = LocalDateTime.now();
        return dateTimeFormatter.format(localDateTime);
    }
}
