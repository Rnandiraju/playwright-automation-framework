package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ConfigManager: Handles reading configuration from properties file
 */
public class ConfigManager {
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static Properties properties;
    private static final String CONFIG_FILE = "src/test/resources/config.properties";

    static {
        loadProperties();
    }

    /**
     * Load properties from config file
     */
    private static void loadProperties() {
        properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE)) {
            properties.load(fileInputStream);
            logger.info("Configuration loaded from: " + CONFIG_FILE);
        } catch (IOException e) {
            logger.error("Failed to load configuration file: " + CONFIG_FILE, e);
            throw new RuntimeException("Configuration file not found: " + CONFIG_FILE, e);
        }
    }

    /**
     * Get browser type from config
     */
    public static String getBrowser() {
        String browser = properties.getProperty("browser", "chrome");
        logger.info("Browser configured as: " + browser);
        return browser;
    }

    /**
     * Get base URL from config
     */
    public static String getBaseUrl() {
        String baseUrl = properties.getProperty("baseUrl");
        if (baseUrl == null || baseUrl.isEmpty()) {
            logger.warn("Base URL not configured");
            return "";
        }
        logger.info("Base URL configured as: " + baseUrl);
        return baseUrl;
    }

    /**
     * Get implicit wait timeout
     */
    public static int getImplicitWaitTimeout() {
        String timeout = properties.getProperty("implicitWait", "10");
        logger.info("Implicit wait timeout set to: " + timeout + " seconds");
        return Integer.parseInt(timeout);
    }

    /**
     * Get explicit wait timeout
     */
    public static int getExplicitWaitTimeout() {
        String timeout = properties.getProperty("explicitWait", "10");
        logger.info("Explicit wait timeout set to: " + timeout + " seconds");
        return Integer.parseInt(timeout);
    }

    /**
     * Get page load timeout
     */
    public static int getPageLoadTimeout() {
        String timeout = properties.getProperty("pageLoadTimeout", "30");
        logger.info("Page load timeout set to: " + timeout + " seconds");
        return Integer.parseInt(timeout);
    }

    /**
     * Get headless mode flag
     */
    public static boolean isHeadless() {
        String headless = properties.getProperty("headless", "false");
        logger.info("Headless mode: " + headless);
        return Boolean.parseBoolean(headless);
    }

    /**
     * Get any property by key
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get any property by key with default value
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
