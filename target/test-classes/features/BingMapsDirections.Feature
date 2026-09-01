Feature: Bing Maps directions

	As a Bing user
	I want to search for a place and get directions
	So that I can view a route between two locations

	Scenario: Search Bangalore and view directions from Whitefield to Kempegowda Metro Station
		Given I am on the Bing home page
		Then the Bing page opens
		When I click on the Maps link
		Then the Bing Maps page opens
		When I search for "Bangalore"
		Then the map zooms to Bangalore
		When I click on Directions
		Then the Directions page opens
		When I enter "Whitefield, 291/4, Railway Station Road, Bengaluru East" as point A
		And I enter "Kempegowda Metro Station, Majestic, 7, Thotadappa Road, Bengaluru North" as point B
		And I view the directions
		Then the map shows the direction from Whitefield to Kempegowda Metro Station
