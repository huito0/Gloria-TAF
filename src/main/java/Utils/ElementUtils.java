package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ElementUtils {
    private ElementUtils() {}

    private List<String> chainLocatorsParts = new ArrayList<>();
    private String selection;

    private void setSelectionAndValue(Pattern pattern, String elementAsString) {
        Matcher matcher = pattern.matcher(elementAsString);

        if (matcher.find()) {
            String[] selectionAndValue = matcher.group(2).split(":");
            selection = selectionAndValue[0].trim();
            chainLocatorsParts.add(selectionAndValue[1].trim());
            setSelectionAndValue(pattern, elementAsString.replace(matcher.group(1), ""));
        }
    }

    private SelectionAndValue getSelectionAndValue(WebElement element, int index) {
        String elementAsString = element.toString();
        Pattern pattern = Pattern.compile(".*(]\\s->(.*)])");

        setSelectionAndValue(pattern, elementAsString);
        Collections.reverse(chainLocatorsParts);

        StringBuilder chainLocatorBuilder = new StringBuilder();
        chainLocatorsParts.forEach(chainLocatorBuilder::append);
        return new SelectionAndValue(selection, chainLocatorBuilder.toString());
    }

    public static By getByFromElement(WebElement element, int index) {
        By by;
        ElementUtils utils = new ElementUtils();
        SelectionAndValue selectorWithValue = utils.getSelectionAndValue(element, index);

        String selector = selectorWithValue.getSelection();

        String value = selector.equals("xpath")
                ? "(" + selectorWithValue.getValue() + ")[" + (index + 1) + "]"
                : selectorWithValue.getValue();

        switch (selector) {
            case "id":
                by = By.id(value);
                break;
            case "className":
                by = By.className(value);
                break;
            case "tagName":
                by = By.tagName(value);
                break;
            case "xpath":
                by = By.xpath(value);
                break;
            case "cssSelector":
                by = By.cssSelector(value);
                break;
            case "linkText":
                by = By.linkText(value);
                break;
            case "name":
                by = By.name(value);
                break;
            case "partialLinkText":
                by = By.partialLinkText(value);
                break;
            default:
                throw new IllegalStateException("locator : " + selector + " not found!!!");
        }
        return by;
    }

    private static class SelectionAndValue {
        private final String selection;
        private final String value;

        public SelectionAndValue(String selection, String value) {
            this.selection = selection;
            this.value = value;
        }

        public String getSelection() {
            return selection;
        }

        public String getValue() {
            return value;
        }
    }

    public static String getXpathLocator(SearchContext searchContext) {
        return (searchContext.toString().split("->")[1].replaceFirst("(?s)(.*)]", "$1" + "")).split(":")[1].trim();
    }

    public static String getXpathLocator(ElementLocator locator) {
        return (locator.toString()).split("By\\.xpath:")[1].trim().replaceFirst(".$", "");
    }
}

