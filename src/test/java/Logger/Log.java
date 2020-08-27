package Logger;

public final class Log {

    public static void warn(String msg) {
        System.out.println("[WARN] " + msg);
    }

    public static void info(String msg) {
        System.out.println("[INFO] " + msg);
    }

    public static void trace(String msg) {
        System.out.println("[TRACE] " + msg);
    }

}
