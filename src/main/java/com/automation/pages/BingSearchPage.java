package com.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.automation.base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * BingSearchPage: Page Object for Bing Search page
 * Demonstrates Page Object Model with locators and page-specific methods
 */
public class BingSearchPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(BingSearchPage.class);

    // Locators
    private final String searchBox = "#sb_form_q";
    private final String searchResults = "#b_results";
    private final String imagesResults = "a.iusc, img.mimg";

    /**
     * Verify if Bing search page is loaded
     */
    public boolean isBingPageLoaded() {
        try {
            page.locator(searchBox).waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
            logger.info("Bing page verified - search box is visible");
            return true;
        } catch (Exception e) {
            logger.warn("Bing page verification failed - search box not found");
            return false;
        }
    }

    /** Perform Bing search for a query. */
    public void searchFor(String searchQuery) {
        logger.info("Searching for: " + searchQuery);
        page.locator(searchBox).fill(searchQuery);
        page.locator(searchBox).press("Enter");
        waitForSearchResults();
    }

    /**
     * Get search query from search box
     */
    public String getSearchQuery() {
        String query = page.locator(searchBox).inputValue();
        logger.info("Current search query: " + query);
        return query;
    }

    /**
     * Clear search box
     */
    public void clearSearchBox() {
        page.locator(searchBox).fill("");
        logger.info("Search box cleared");
    }

    /**
     * Wait for search results to load
     */
    private void waitForSearchResults() {
        page.locator(searchResults).waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
            .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
        logger.info("Search results loaded successfully");
    }

    /**
     * Check if search results are displayed
     */
    public boolean areSearchResultsDisplayed() {
        boolean resultsVisible = isElementVisible(searchResults);
        logger.info("Search results visibility: " + resultsVisible);
        return resultsVisible;
    }

    /** Get the number of links currently rendered on the Bing home page. */
    public int getHomePageLinkCount() {
        int linkCount = page.locator("a").count();
        logger.info("Bing home page link count: " + linkCount);
        return linkCount;
    }

    /** Open Bing Images from the Bing home page. */
    public void openImages() {
        com.microsoft.playwright.Locator imagesLink = page.locator("#images").first();
        if (imagesLink.count() == 0) {
            imagesLink = page.locator("a[href*='/images']").first();
        }
        if (imagesLink.count() == 0) {
            imagesLink = page.locator("a").filter(new com.microsoft.playwright.Locator.FilterOptions()
                .setHasText("Images")).first();
        }
        imagesLink.click();
        page.waitForLoadState();
        if (!page.url().toLowerCase().contains("/images")) {
            page.navigate("https://www.bing.com/images");
        }
        logger.info("Bing Images page opened: " + page.url());
    }

    /** Verify that the Bing Images page is open. */
    public boolean isImagesPageOpened() {
        return page.url().contains("/images");
    }

    /** Enter a query in the Bing Images search box. */
    public void enterImagesSearchQuery(String searchQuery) {
        Locator searchInput = page.locator("#sb_form_q, input[name='q']").first();
        searchInput.waitFor(new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE));
        searchInput.click();
        searchInput.fill(searchQuery);
        logger.info("Entered Images search query: {}", searchQuery);
    }

    /** Submit the current Bing Images search. */
    public void submitImagesSearch() {
        page.locator("#sb_form_q, input[name='q']").first().press("Enter");
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.locator(imagesResults).first().waitFor(new Locator.WaitForOptions()
            .setTimeout(15000)
            .setState(WaitForSelectorState.VISIBLE));
        logger.info("Bing Images search submitted");
    }

    /** Verify that image results are displayed. */
    public boolean areImageResultsDisplayed() {
        return page.locator(imagesResults).count() > 0;
    }

    /** Verify that the page content contains the submitted search query. */
    public boolean pageContainsText(String text) {
        return page.locator("body").innerText().contains(text)
            || page.title().contains(text)
            || page.url().contains(text.replace(" ", "+"));
    }

    /**
     * Get page title (Bing search result page title)
     */
    @Override
    public String getPageTitle() {
        String title = super.getPageTitle();
        logger.info("Retrieved page title: " + title);
        return title;
    }
}
