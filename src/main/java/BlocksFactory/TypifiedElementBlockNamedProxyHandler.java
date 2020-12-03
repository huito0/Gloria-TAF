package BlocksFactory;

import DriverManager.GlobalDriverManager;
import Utils.ElementUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import static BlocksFactory.HtmlElementLoaderDecorator.createTypifiedElement;

public class TypifiedElementBlockNamedProxyHandler<T extends TypifiedElement> implements InvocationHandler {
    private final Class<T> elementClass;
    private final ElementLocator locator;
    private final String name;

    public TypifiedElementBlockNamedProxyHandler(Class<T> elementClass, ElementLocator locator, String name) {
        this.elementClass = elementClass;
        this.locator = locator;
        this.name = name;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if ("toString".equals(method.getName())) {
            return name;
        }

        List<T> elements = new LinkedList<>();
        int elementNumber = 0;

        List<WebElement> notFormattedElements = locator.findElements();

        for (int i = 0; i < notFormattedElements.size(); i++) {
            WebElement element = GlobalDriverManager.getDriver().findElement(ElementUtils.getByFromElement(notFormattedElements.get(i), i));

            String newName = String.format("%s [%d]", name, elementNumber++);
            elements.add(createTypifiedElement(elementClass, element, newName));
        }

        try {
            return method.invoke(elements, objects);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }
}
