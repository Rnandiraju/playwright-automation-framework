package com.demoqa.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.demoqa.automation.base.BaseTest;
import com.demoqa.automation.pages.BrowserWindowsPage;
import com.microsoft.playwright.Page;

public class BrowserWindowsTest extends BaseTest {
    @Test(description = "Verify DemoQA opens a new tab")
    public void shouldOpenNewTab() {
        BrowserWindowsPage browserWindows = new BrowserWindowsPage();
        Page newTab = browserWindows.openNewTab();

        Assert.assertEquals(browserWindows.pageHeading(newTab), "This is a sample page");
        newTab.close();
    }

    @Test(description = "Verify DemoQA opens a new window")
    public void shouldOpenNewWindow() {
        BrowserWindowsPage browserWindows = new BrowserWindowsPage();
        Page newWindow = browserWindows.openNewWindow();

        Assert.assertEquals(browserWindows.pageHeading(newWindow), "This is a sample page");
        newWindow.close();
    }

    @Test(description = "Verify DemoQA opens a window with a message")
    public void shouldOpenNewWindowMessage() {
        BrowserWindowsPage browserWindows = new BrowserWindowsPage();
        Page messageWindow = browserWindows.openNewWindowMessage();

        Assert.assertTrue(browserWindows.message(messageWindow).contains("Knowledge increases by sharing"));
        messageWindow.close();
    }
}
