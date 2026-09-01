package com.automation.drivers;

import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.automation.utils.ConfigManager;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * DriverManager: Manages Playwright lifecycle using ThreadLocal for thread-safe parallel execution
 */
public class DriverManager {
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> contextThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    /**
    * Initialize Playwright based on browser type from configuration
     */
    public static void initializeDriver(String browserType) {
        Playwright playwright = Playwright.create();
        BrowserType browser = selectBrowser(playwright, browserType);
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
            .setHeadless(ConfigManager.isHeadless())
            .setSlowMo(500);
        configureInstalledBrowser(launchOptions, browserType);
        Browser launchedBrowser = browser.launch(launchOptions);
        BrowserContext context = launchedBrowser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080));

        playwrightThreadLocal.set(playwright);
        browserThreadLocal.set(launchedBrowser);
        contextThreadLocal.set(context);
        pageThreadLocal.set(context.newPage());
        logger.info("Playwright initialized for browser: " + browserType);
    }

    private static void configureInstalledBrowser(BrowserType.LaunchOptions launchOptions, String browserType) {
        String executablePath = ConfigManager.getProperty("browserExecutablePath", "").trim();
        if (!executablePath.isEmpty()) {
            launchOptions.setExecutablePath(Paths.get(executablePath));
            logger.info("Using installed browser executable: " + executablePath);
            return;
        }

        if (Boolean.parseBoolean(ConfigManager.getProperty("useInstalledBrowser", "true"))) {
            if ("chrome".equalsIgnoreCase(browserType)) {
                launchOptions.setChannel("chrome");
            } else if ("edge".equalsIgnoreCase(browserType)) {
                launchOptions.setChannel("msedge");
            }
        }
    }

    private static BrowserType selectBrowser(Playwright playwright, String browserType) {
        switch (browserType.toLowerCase()) {
            case "firefox":
                return playwright.firefox();
            case "webkit":
                return playwright.webkit();
            case "chrome":
            case "edge":
                return playwright.chromium();
            default:
                logger.warn("Unknown browser type: " + browserType + ". Defaulting to Chromium");
                return playwright.chromium();
        }
    }

    /**
     * Get current thread's Playwright page
     */
    public static Page getPage() {
        return pageThreadLocal.get();
    }

    /**
    * Quit Playwright and remove from ThreadLocal
     */
    public static void quitDriver() {
        BrowserContext context = contextThreadLocal.get();
        if (context != null) {
            context.close();
            browserThreadLocal.get().close();
            playwrightThreadLocal.get().close();
            pageThreadLocal.remove();
            contextThreadLocal.remove();
            browserThreadLocal.remove();
            playwrightThreadLocal.remove();
            logger.info("Playwright closed and removed from ThreadLocal");
        }
    }

    /**
     * Check if driver is initialized
     */
    public static boolean isDriverInitialized() {
        return pageThreadLocal.get() != null;
    }
}
