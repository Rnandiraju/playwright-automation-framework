package com.demoqa.automation.pages;

import com.demoqa.automation.drivers.DriverManager;
import com.microsoft.playwright.Page;

public class BrowserWindowsPage {
    private final Page page;

    public BrowserWindowsPage() {
        this.page = DriverManager.page();
    }

    public void open() {
        page.navigate("https://demoqa.com/browser-windows");
    }

    public Page openNewTab() {
        return page.waitForPopup(() -> page.getByRole(
            com.microsoft.playwright.options.AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("New Tab")).click());
    }

    public Page openNewWindow() {
        return page.waitForPopup(() -> page.getByRole(
            com.microsoft.playwright.options.AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("New Window")).click());
    }

    public Page openNewWindowMessage() {
        return page.waitForPopup(() -> page.getByRole(
            com.microsoft.playwright.options.AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("New Window Message")).click());
    }

    public String pageHeading(Page openedPage) {
        return openedPage.locator("h1").innerText();
    }

    public String message(Page openedPage) {
        return openedPage.locator("body").innerText();
    }
}
