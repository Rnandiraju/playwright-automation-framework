package com.demoqa.automation.drivers;

import com.demoqa.automation.utils.ConfigManager;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public final class DriverManager {
    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void start() {
        Playwright instance = Playwright.create();
        BrowserType chromium = instance.chromium();
        Browser launched = chromium.launch(new BrowserType.LaunchOptions()
            .setHeadless(ConfigManager.isHeadless())
            .setChannel(ConfigManager.getProperty("browserChannel", "chrome")));
        BrowserContext browserContext = launched.newContext(new Browser.NewContextOptions()
            .setViewportSize(1440, 900));

        playwright.set(instance);
        browser.set(launched);
        context.set(browserContext);
        page.set(browserContext.newPage());
    }

    public static Page page() {
        return page.get();
    }

    public static void stop() {
        BrowserContext browserContext = context.get();
        if (browserContext != null) {
            browserContext.close();
            browser.get().close();
            playwright.get().close();
        }
        page.remove();
        context.remove();
        browser.remove();
        playwright.remove();
    }
}
