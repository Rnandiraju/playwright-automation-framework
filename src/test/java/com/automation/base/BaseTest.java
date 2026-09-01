package com.automation.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.automation.drivers.DriverManager;
import com.automation.utils.ConfigManager;

/**
 * BaseTest: Abstract test class for test setup and teardown
 */
public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    /**
     * Setup method - runs before each test
     */
    @BeforeMethod
    public void setUp() {
        logger.info("========== Test Setup Started ==========");
        
        // Initialize Playwright
        String browser = ConfigManager.getBrowser();
        DriverManager.initializeDriver(browser);
        
        // Navigate to base URL
        String baseUrl = ConfigManager.getBaseUrl();
        if (baseUrl != null && !baseUrl.isEmpty()) {
            DriverManager.getPage().navigate(baseUrl);
            logger.info("Navigated to: " + baseUrl);
        }
        
        logger.info("========== Test Setup Completed ==========");
    }

    /**
     * Teardown method - runs after each test
     */
    @AfterMethod
    public void tearDown() {
        logger.info("========== Test Teardown Started ==========");
        
        if (DriverManager.isDriverInitialized()) {
            DriverManager.quitDriver();
            logger.info("Playwright closed successfully");
        }
        
        logger.info("========== Test Teardown Completed ==========");
    }

    /** Pause before each validation stage. */
    protected void pauseBeforeValidation(String validationName) {
        logger.info("Pausing for 15 seconds before validation: " + validationName);
        DriverManager.getPage().waitForTimeout(15_000);
    }

    /**
     * Assert method - check if condition is true
     */
    public void assertTrue(boolean condition, String message) {
        if (!condition) {
            logger.error("Assertion failed: " + message);
            throw new AssertionError(message);
        }
        logger.info("Assertion passed: " + message);
    }

    /**
     * Assert method - check if condition is true (without message)
     */
    public void assertTrue(boolean condition) {
        assertTrue(condition, "Expected true but got false");
    }

    /**
     * Assert method - check if strings are equal
     */
    public void assertEquals(String actual, String expected, String message) {
        if (!actual.equals(expected)) {
            logger.error("Assertion failed: " + message + " | Expected: " + expected + " | Actual: " + actual);
            throw new AssertionError(message);
        }
        logger.info("Assertion passed: " + message);
    }

    /**
     * Assert method - check if objects are equal
     */
    public void assertEquals(Object actual, Object expected) {
        if (!actual.equals(expected)) {
            logger.error("Assertion failed: Expected " + expected + " but got " + actual);
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
        logger.info("Assertion passed");
    }

    /**
     * Assert method - check if condition is false
     */
    public void assertFalse(boolean condition, String message) {
        if (condition) {
            logger.error("Assertion failed: " + message);
            throw new AssertionError(message);
        }
        logger.info("Assertion passed: " + message);
    }
}
