package Runner;

import CLIOptions.GlobalConfig;
import DriverManager.GlobalDriverManager;

public class Runner {
    public static void main(String[] args) {
        GlobalConfig.parseOptions(args);

        GlobalDriverManager.getDriver();
        GlobalDriverManager.destroyDriver();

    }
}
