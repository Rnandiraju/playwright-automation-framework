package com.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.automation.base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

/** Page object for the Bing Maps directions workflow. */
public class BingMapsPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(BingMapsPage.class);
    private static final String MAPS_URL = "https://www.bing.com/maps";
    private static final String MAP_SEARCH = "input[aria-label*='Search'], input[placeholder*='Search'], #maps_sb";
    private static final String DIRECTIONS_BUTTON = "a:has-text('Directions'), button:has-text('Directions'), [aria-label*='Directions']";
    private static final String DIRECTIONS_PANEL = "div[id*='directions'], div[class*='directions'], "
        + "section[aria-label*='Directions'], [role='region'][aria-label*='Directions']";
    private static final String POINT_A_INPUT = "input[aria-label*='Start'], input[placeholder*='Start'], input[aria-label*='A']";
    private static final String POINT_B_INPUT = "input[aria-label*='End'], input[placeholder*='End'], input[aria-label*='B']";

    public void openMaps() {
        Locator mapsLink = page.locator("a[href*='/maps']").first();
        if (mapsLink.count() == 0) {
            mapsLink = page.getByText("Maps", new com.microsoft.playwright.Page.GetByTextOptions().setExact(true)).first();
        }
        mapsLink.click();
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        if (!page.url().contains("/maps")) {
            page.navigate(MAPS_URL);
        }
        logger.info("Bing Maps page opened: " + page.url());
    }

    public boolean isMapsPageOpened() {
        return page.url().contains("/maps");
    }

    public void searchPlace(String place) {
        Locator searchInput = visibleInput(MAP_SEARCH);
        searchInput.fill(place);
        searchInput.press("Enter");
        page.waitForLoadState(LoadState.NETWORKIDLE);
        logger.info("Searched Bing Maps for: " + place);
    }

    public boolean isPlaceDisplayed(String place) {
        return page.locator("body").innerText().contains(place)
            || page.url().toLowerCase().contains(place.toLowerCase());
    }

    public void openDirections() {
        Locator directions = page.locator(DIRECTIONS_BUTTON).first();
        directions.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        directions.click();
        logger.info("Bing Maps Directions opened");
    }

    public boolean isDirectionsPageOpened() {
        return directionsPanel().innerText().contains("Directions")
            || page.url().toLowerCase().contains("direction");
    }

    public void enterPointA(String location) {
        Locator pointA = visibleInput(POINT_A_INPUT);
        pointA.fill(location);
        pointA.press("Enter");
        logger.info("Point A entered: " + location);
    }

    public void enterPointB(String location) {
        Locator pointB = visibleInput(POINT_B_INPUT);
        pointB.fill(location);
        pointB.press("Enter");
        logger.info("Point B entered: " + location);
        page.waitForTimeout(2000);
        pointB.press("ArrowDown");
        pointB.press("Enter");
        page.waitForTimeout(5000);
    }

    public boolean routeContains(String location) {
        return directionsPanel().innerText().contains(location);
    }

    private Locator directionsPanel() {
        Locator panel = page.locator(DIRECTIONS_PANEL + ":visible").first();
        panel.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return panel;
    }

    private Locator visibleInput(String selector) {
        Locator input = page.locator(selector + ":visible").first();
        input.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return input;
    }
}
