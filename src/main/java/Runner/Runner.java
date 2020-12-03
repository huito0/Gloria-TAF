package Runner;

import CLIOptions.GlobalConfig;
import io.cucumber.core.cli.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        List<String> programmArgs = getHardcodedConfiguration();
        programmArgs.addAll(Arrays.asList(args));

        GlobalConfig.parseOptions(programmArgs);

        // This method is workaround to add plugins,
        // owing to we have to know what environment is used to set json output path
        setCucumberPlugins();

        Main.main(getCucumberOptions());
    }

    private static List<String> getHardcodedConfiguration() {
        List<String> hardcodedPresetOptions = new ArrayList<>();
        hardcodedPresetOptions.add("--glue");
        hardcodedPresetOptions.add("StepDefs,BaseTest");
        hardcodedPresetOptions.add("--features");
        hardcodedPresetOptions.add("src/main/resources/Features");
        return hardcodedPresetOptions;
    }

    private static void setCucumberPlugins() {
        List<String> pluginsOptions = new ArrayList<>();

        pluginsOptions.add("--plugin");
        pluginsOptions.add("pretty," +
                "json:" + GlobalConfig.getEnvironment().getOuputPath() + "/cucumber-reporting/output.json," +
                "BaseTest.BaseTest," +
                "Report.ScreenshotPublisher");

        GlobalConfig.parseOptions(pluginsOptions);
    }

    private static String[] getCucumberOptions() {
        List<String> cucumberOptions = new ArrayList<>(getGluesConfiguration());
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
        if (GlobalConfig.getPlugins() == null) {
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
