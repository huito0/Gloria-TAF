package Utils;

import DriverManager.GlobalDriverManager;
import org.openqa.selenium.Dimension;

public enum Breakpoint {
    MOBILE(new Dimension(375, 670)),
    DESKTOP(new Dimension(1366, 768));

    private Dimension dimension;

    Breakpoint(Dimension dimension) {
        this.dimension = dimension;
    }

    public void accept() {
        GlobalDriverManager.getDriver().manage().window().setSize(dimension);
    }
}
