package Runner;

import CLIOptions.GlobalConfig;
import io.cucumber.core.cli.Main;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        GlobalConfig.parseOptions(args);

        Main.main(getCucumberOptions());
    }

    private static String[] getCucumberOptions() {
        List<String> cucumberOptions = new ArrayList<>();

        cucumberOptions.addAll(getGluesConfiguration());
        cucumberOptions.addAll(getPluginsConfiguration());
        cucumberOptions.addAll(getTags());
        cucumberOptions.add(GlobalConfig.getFeaturePath());

        return cucumberOptions.toArray(new String[]{});
    }

    private static List<String> getGluesConfiguration() {
        List<String> glues = new ArrayList<>();
        for (String glue : GlobalConfig.getGlues()) {
            glues.add("-g");
            glues.add(glue);
        }
        return glues;
    }

    private static List<String> getPluginsConfiguration() {
        if (GlobalConfig.getPlugins().size() == 0) {
            return new ArrayList<>();
        }
        List<String> plugins = new ArrayList<>();
        for (String plugin : GlobalConfig.getPlugins()) {
            plugins.add("-p");
            plugins.add(plugin);
        }
        return plugins;
    }

    private static List<String> getTags() {
        List<String> tags = new ArrayList<>();
        for (String tag : GlobalConfig.getTags()) {
            tags.add("-t");
            tags.add(tag);
        }
        return tags;
    }
}
