package com.automation.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.automation.base.BaseTest;
import com.automation.pages.BingSearchPage;

/**
 * BingSearchTest: Example test cases for Bing Search using Page Object Model
 */
public class BingSearchTest extends BaseTest {
    private static final Logger testLogger = LogManager.getLogger(BingSearchTest.class);

    /**
     * Test to verify Bing page loads successfully
     */
    @Test(description = "Verify Bing search page loads successfully")
    public void testBingPageLoads() {
        testLogger.info("Starting test: testBingPageLoads");
       
        BingSearchPage bingPage = new BingSearchPage();

        pauseBeforeValidation("Bing page loaded");
        assertTrue(bingPage.isBingPageLoaded(), "Bing page should be loaded");

        testLogger.info("Test completed: testBingPageLoads");
    }

    /**
     * Test to verify that Bing home page links are present
     */
    @Test(description = "Verify links are present on the Bing home page")
    public void testBingHomePageLinks() {
        testLogger.info("Starting test: testBingHomePageLinks");

        BingSearchPage bingPage = new BingSearchPage();
        int linkCount = bingPage.getHomePageLinkCount();
        System.out.println("Bing home page contains " + linkCount + " links.");

        pauseBeforeValidation("Bing home page links present");
        assertTrue(linkCount > 0,
                   "Bing home page should contain at least one link, but found " + linkCount);

        testLogger.info("Test completed: testBingHomePageLinks");
    }

    /** Verify Bing Images search for MS Dhoni. */
    @Test(description = "Verify Bing Images search for MS Dhoni")
    public void testBingImagesSearchForMsDhoni() {
        testLogger.info("Starting test: testBingImagesSearchForMsDhoni");

        BingSearchPage bingPage = new BingSearchPage();
        bingPage.openImages();
        pauseBeforeValidation("Bing Images page should be opened");
        assertTrue(bingPage.isImagesPageOpened(), "Bing Images page should be opened");

        bingPage.enterImagesSearchQuery("MS Dhoni");
        bingPage.submitImagesSearch();
        pauseBeforeValidation("Bing Images results should be displayed");
        assertTrue(bingPage.areImageResultsDisplayed(), "Bing Images results should be displayed");
        assertTrue(bingPage.pageContainsText("MS Dhoni"),
                   "Bing Images page should contain 'MS Dhoni'");

        testLogger.info("Test completed: testBingImagesSearchForMsDhoni");
    }

    /**
     * Test to perform a search on Bing
     */
    @Test(description = "Verify search functionality on Bing")
    public void testBingSearch() {
        testLogger.info("Starting test: testBingSearch");
        
        BingSearchPage bingPage = new BingSearchPage();
        String searchQuery = "Selenium WebDriver";

        bingPage.searchFor(searchQuery);

        pauseBeforeValidation("Bing search results displayed");
        assertTrue(bingPage.areSearchResultsDisplayed(),
                   "Bing search results should be displayed");

        String pageTitle = bingPage.getPageTitle();
        pauseBeforeValidation("Bing page title contains search query");
        assertTrue(pageTitle.contains(searchQuery),
                   "Bing page title should contain: " + searchQuery);

        testLogger.info("Test completed: testBingSearch");
    }

    /**
     * Test to verify search with multiple keywords
     */
    @Test(description = "Verify search with multiple keywords")
    public void testBingSearchMultipleKeywords() {
        testLogger.info("Starting test: testBingSearchMultipleKeywords");

        BingSearchPage bingPage = new BingSearchPage();
        String searchQuery = "Java Selenium Automation";

        bingPage.searchFor(searchQuery);

        String currentQuery = bingPage.getSearchQuery();
        pauseBeforeValidation("Bing search query contains Java");
        assertTrue(currentQuery.contains("Java"),
                   "Search query should contain 'Java'");

        testLogger.info("Test completed: testBingSearchMultipleKeywords");
    }
}
