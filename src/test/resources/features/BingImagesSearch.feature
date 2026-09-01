Feature: Bing Images search

  Scenario: Click on Images and search for MS Dhoni
    Given I am on the Bing home page
    When I click on Images
    Then Images page opens
    When I enter "MS Dhoni" in the search box
    And I submit the search
    Then search results should be displayed
    And the page should contain "MS Dhoni"
