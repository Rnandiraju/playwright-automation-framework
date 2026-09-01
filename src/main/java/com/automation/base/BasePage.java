package com.automation.base;

import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.automation.drivers.DriverManager;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * BasePage: Base class containing common element interaction methods and wait strategies
 */
public class BasePage {
    protected final Page page;
    private static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage() {
        this.page = DriverManager.getPage();
    }

    public void click(String locator) {
        try {
            page.locator(locator).first().click();
            logger.info("Clicked on element: " + locator);
        } catch (Exception e) {
            logger.error("Failed to click element: " + locator, e);
            throw e;
        }
    }

    public void type(String locator, String text) {
        page.locator(locator).fill(text);
        logger.info("Typed text into element: " + locator);
    }

    public String getText(String locator) {
        return page.locator(locator).innerText();
    }

    public boolean isElementVisible(String locator) {
        try {
            return page.locator(locator).isVisible();
        } catch (Exception e) {
            logger.warn("Element is not visible: " + locator);
            return false;
        }
    }

    public boolean isElementPresent(String locator) {
        return page.locator(locator).count() > 0;
    }

    public void waitForElementToDisappear(String locator) {
        page.locator(locator).waitFor(new Locator.WaitForOptions()
            .setState(WaitForSelectorState.HIDDEN));
    }

    public void selectByVisibleText(String locator, String text) {
        page.locator(locator).selectOption(new SelectOption().setLabel(text));
    }

    public String getPageTitle() {
        return page.title();
    }

    public String getCurrentUrl() {
        return page.url();
    }

    public void navigateTo(String url) {
        page.navigate(url);
    }

    public Object executeJavaScript(String script, Object... args) {
        return page.evaluate(script, args.length == 0 ? null : args[0]);
    }

    public void scrollToElement(String locator) {
        page.locator(locator).scrollIntoViewIfNeeded();
    }

    public void takeScreenshot(String fileName) {
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(fileName)));
    }
}
