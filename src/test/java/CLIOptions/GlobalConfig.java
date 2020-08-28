package CLIOptions;

import DriverManager.DriverType;

import java.util.Arrays;
import java.util.List;

import static Logger.Log.info;
import static Logger.Log.warn;

public final class GlobalConfig {
    private static DriverType driverType = null;
    private static int timeout = 0;
    private static List<String> tags = null;
    private static List<String> glues = null;
    private static List<String> plugins = null;
    private static String featurePath = null;

    private GlobalConfig() {
    }

    public static void parseOptions(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            switch (args[i]) {
                case "--driver":
                    driverType = DriverType.valueOf(args[++i]);
                    info("Driver type is " + driverType.name());
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

    public static DriverType getDriverType() {
        if (driverType == null) {
            warn("You didn't set [--driver] option in program args, so CHROME will be used by default");
            driverType = DriverType.CHROME;
        }
        return driverType;
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
        if (plugins.size() == 0) {
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
