package com.demoqa.automation.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.demoqa.automation.drivers.DriverManager;
import com.demoqa.automation.utils.ConfigManager;

public abstract class BaseTest {
    @BeforeMethod
    public void setUp() {
        DriverManager.start();
        DriverManager.page().navigate(ConfigManager.getBaseUrl());
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.stop();
    }
}
