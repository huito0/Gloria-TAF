package BlocksFactory;

import Utils.ElementUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;
import ru.yandex.qatools.htmlelements.loader.decorator.proxyhandlers.WebElementListNamedProxyHandler;
import ru.yandex.qatools.htmlelements.loader.decorator.proxyhandlers.WebElementNamedProxyHandler;
import ru.yandex.qatools.htmlelements.pagefactory.CustomElementLocatorFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

import static BlocksFactory.HtmlElementLoaderDecorator.createHtmlElement;
import static BlocksFactory.HtmlElementLoaderDecorator.createTypifiedElement;
import static ru.yandex.qatools.htmlelements.loader.decorator.ProxyFactory.*;
import static ru.yandex.qatools.htmlelements.utils.HtmlElementUtils.*;

public class HtmlBlockDecorator implements FieldDecorator {
    protected ElementLocatorFactory factory;

    public HtmlBlockDecorator(CustomElementLocatorFactory factory) {
        this.factory = factory;
    }

    public Object decorate(ClassLoader loader, Field field) {
        try {
            if (isTypifiedElement(field)) {
                return decorateTypifiedElement(loader, field);
            }
            if (isHtmlElement(field)) {
                return decorateHtmlElement(loader, field);
            }
            if (isWebElement(field) && !field.getName().equals("wrappedElement")) {
                return decorateWebElement(loader, field);
            }
            if (isTypifiedElementList(field)) {
                return decorateTypifiedElementList(loader, field);
            }
            if (isHtmlElementList(field)) {
                return decorateHtmlElementList(loader, field);
            }
            if (isWebElementList(field)) {
                return decorateWebElementList(loader, field);
            }
            return null;
        } catch (ClassCastException ignore) {
            return null; // See bug #94 and NonElementFieldsTest
        }
    }

    protected <T extends TypifiedElement> T decorateTypifiedElement(ClassLoader loader, Field field) {
        WebElement elementToWrap = decorateWebElement(loader, field);

        //noinspection unchecked
        return createTypifiedElement((Class<T>) field.getType(), elementToWrap, getElementName(field));
    }

    protected <T extends HtmlElement> T decorateHtmlElement(ClassLoader loader, Field field) {
        WebElement elementToWrap = decorateWebElement(loader, field);

        //noinspection unchecked
        return createHtmlElement((Class<T>) field.getType(), elementToWrap, getElementName(field));
    }

    private ElementLocator getElementLocator(SearchContext searchContext) throws NoSuchFieldException, IllegalAccessException {
        // first of all we have to get a proxy from search context
        Field h = searchContext.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        // and now we get access to a locator in wrapped element
        WebElementNamedProxyHandler proxyHandler = (WebElementNamedProxyHandler) h.get(searchContext);
        Field locatorField = proxyHandler.getClass().getSuperclass().getDeclaredField("locator");
        locatorField.setAccessible(true);
        return (ElementLocator) locatorField.get(proxyHandler);
    }

    private ElementLocator getChainLocator(ElementLocator locator, Field field) {
        String parentLocator = "";
        String childLocator = "";
        try {
            // We get search context from field, to get locator of it.
            Field scField = locator.getClass().getSuperclass().getDeclaredField("searchContext");
            scField.setAccessible(true);
            SearchContext searchContext = (SearchContext) scField.get(locator);
            try {
                // if search context located inside element from a list, search context has a locator inside
                parentLocator = ElementUtils.getXpathLocator(searchContext);
            } catch (ArrayIndexOutOfBoundsException e) {
                try {
                    // if search context located inside element without listing, locator will be in proxy,
                    // and we have to get element locator object first
                    parentLocator = ElementUtils.getXpathLocator(getElementLocator(searchContext));
                } catch (NoSuchFieldException ignored) {}
            }
            childLocator = field.getDeclaredAnnotation(FindBy.class).xpath();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Object oldValue = changeAnnotationValue(field.getDeclaredAnnotation(FindBy.class), "xpath", parentLocator + childLocator);
        locator = factory.createLocator(field);
        changeAnnotationValue(field.getDeclaredAnnotation(FindBy.class), "xpath", oldValue);

        return locator;
    }

    protected WebElement decorateWebElement(ClassLoader loader, Field field) {
        ElementLocator locator = getChainLocator(factory.createLocator(field), field);

        InvocationHandler handler = new WebElementNamedProxyHandler(locator, getElementName(field));
        return createWebElementProxy(loader, handler);
    }

    @SuppressWarnings("unchecked")
    public Object changeAnnotationValue(Annotation annotation, String key, Object newValue){
        Object handler = Proxy.getInvocationHandler(annotation);
        Field f;
        try {
            f = handler.getClass().getDeclaredField("memberValues");
        } catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(e);
        }
        f.setAccessible(true);
        Map<String, Object> memberValues;
        try {
            memberValues = (Map<String, Object>) f.get(handler);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        Object oldValue = memberValues.get(key);
        if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
            throw new IllegalArgumentException();
        }
        memberValues.put(key,newValue);
        return oldValue;
    }

    protected <T extends TypifiedElement> List<T> decorateTypifiedElementList(ClassLoader loader, Field field) {
        @SuppressWarnings("unchecked")
        Class<T> elementClass = (Class<T>) getGenericParameterClass(field);
        ElementLocator locator = factory.createLocator(field);
        String name = getElementName(field);

        InvocationHandler handler = new TypifiedElementBlockNamedProxyHandler<>(elementClass, locator, name);

        return createTypifiedElementListProxy(loader, handler);
    }

    protected <T extends HtmlElement> List<T> decorateHtmlElementList(ClassLoader loader, Field field) {
        @SuppressWarnings("unchecked")
        Class<T> elementClass = (Class<T>) getGenericParameterClass(field);
        ElementLocator locator = factory.createLocator(field);
        String name = getElementName(field);

        InvocationHandler handler = new HtmlElementBlockNamedProxyHandler<>(elementClass, locator, name);

        return createHtmlElementListProxy(loader, handler);
    }

    protected List<WebElement> decorateWebElementList(ClassLoader loader, Field field) {
        ElementLocator locator = factory.createLocator(field);
        InvocationHandler handler = new WebElementListNamedProxyHandler(locator, getElementName(field));

        return createWebElementListProxy(loader, handler);
    }
}
