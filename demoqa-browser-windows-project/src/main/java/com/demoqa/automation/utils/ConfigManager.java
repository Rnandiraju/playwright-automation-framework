package com.demoqa.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ConfigManager {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (FileInputStream input = new FileInputStream("src/test/resources/config.properties")) {
            PROPERTIES.load(input);
        } catch (IOException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    private ConfigManager() {
    }

    public static String getBaseUrl() {
        return getProperty("baseUrl", "https://demoqa.com/browser-windows");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "true"));
    }

    public static String getProperty(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }
}
