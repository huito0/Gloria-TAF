package Logger;

import io.cucumber.java.Scenario;

import java.util.function.Consumer;

public final class Log {
    private static Scenario scenario;
    private static final Consumer<String> writer = m -> {
        if (scenario != null) {
            scenario.write(m);
        } else {
            System.out.println(m);
        }
    };

    public static void warn(String msg) {
        writer.accept("[WARN] " + msg);
    }

    public static void info(String msg) {
        writer.accept("[INFO] " + msg);
    }

    public static void trace(String msg) {
        writer.accept("[TRACE] " + msg);
    }

    public static void setScenario(Scenario scenario) {
        Log.scenario = scenario;
    }
}
