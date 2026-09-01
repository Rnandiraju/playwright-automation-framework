package com.automation.tests;

import org.testng.annotations.Test;

import com.automation.base.BaseTest;
import com.automation.pages.BingMapsPage;

/** Executes the Bing Maps directions feature as a Playwright TestNG test. */
public class BingMapsDirectionsTest extends BaseTest {
    @Test(description = "Search Bangalore and view directions from Whitefield to Kempegowda Metro Station")
    public void searchBangaloreAndViewDirections() {
        BingMapsPage mapsPage = new BingMapsPage();

        mapsPage.openMaps();
        assertTrue(mapsPage.isMapsPageOpened(), "Bing Maps page should open");

        mapsPage.searchPlace("Bangalore");
        assertTrue(mapsPage.isPlaceDisplayed("Bangalore"), "Bangalore should be displayed on the map");

        mapsPage.openDirections();
        assertTrue(mapsPage.isDirectionsPageOpened(), "Directions page should open");

        mapsPage.enterPointA("Whitefield, 291/4, Railway Station Road, Bengaluru East");
        pauseBeforeValidation("Kempegowda Metro Station, Majestic, 7, Thotadappa Road, Bengaluru North");
        mapsPage.enterPointB("Kempegowda Metro Station, Majestic, 7, Thotadappa Road, Bengaluru North");
        
     }
}
