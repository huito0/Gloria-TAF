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

    private GlobalConfig() {
    }

    public static void parseOptions(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            switch (args[i]) {
                case "--driver":
                    driverType = DriverType.valueOf(args[++i]);
                    break;
                case "--timeout":
                    timeout = Integer.parseInt(args[++i]);
                    break;
                case "--tags":
                    tags = Arrays.asList(args[++i].split(","));
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
}
