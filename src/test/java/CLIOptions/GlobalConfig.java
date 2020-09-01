package CLIOptions;

import DriverManager.Browser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Logger.Log.info;
import static Logger.Log.warn;

public final class GlobalConfig {
    //TODO make this appropriate to your project
    public static final String PROJECT_NAME = "Gloria-TAF";

    private static Browser browserType = null;
    private static int timeout = 0;
    private static List<String> tags = new ArrayList<>();
    private static List<String> glues = null;
    private static List<String> plugins = null;
    private static String featurePath = null;

    private GlobalConfig() {
    }

    public static void parseOptions(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            switch (args[i]) {
                case "--browser":
                    browserType = Browser.valueOf(args[++i].toUpperCase());
                    info("Driver type is " + browserType.name());
                    break;
                case "--timeout":
                    timeout = Integer.parseInt(args[++i]);
                    info("Timeout is " + timeout);
                    break;
                case "--tags":
                    tags = Arrays.asList(args[++i].split(","));
                    info("tags are " + tags);
                    break;
                case "--glue":
                    glues = Arrays.asList(args[++i].split(","));
                    break;
                case "--plugin":
                    plugins = Arrays.asList(args[++i].split(","));
                    break;
                case "--features":
                    featurePath = args[++i];
                    break;
                default:
                    throw new RuntimeException("Unexpected program argument");
            }
        }
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
            throw new RuntimeException("No glue path was set");
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
            throw new RuntimeException("No feature path was set");
        }
        return featurePath;
    }
}
