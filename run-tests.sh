#!/bin/bash

# Run all tests and generate the Allure HTML report
echo "Running Playwright tests with the installed browser..."
./mvnw test io.qameta.allure:allure-maven:2.15.0:report
RESULT=$?
if [ $RESULT -ne 0 ]; then
	exit $RESULT
fi

echo "Allure report generated at target/site/allure-maven/index.html"

if command -v allure >/dev/null 2>&1; then
	echo "Starting Allure report server..."
	allure serve target/allure-results
fi

# Run specific test suite
# echo "Running smoke tests..."
# mvn clean test -Dsuites=src/test/resources/testng.xml

# Run specific test class
# echo "Running GoogleSearchTest..."
# mvn clean test -Dtest=GoogleSearchTest
