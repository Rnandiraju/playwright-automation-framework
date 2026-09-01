@echo off
REM Run all tests and generate the Allure HTML report
echo Running Playwright tests with the installed browser...
call mvnw.bat test io.qameta.allure:allure-maven:2.15.0:report
if errorlevel 1 exit /b %errorlevel%

echo Allure report generated at target\site\allure-maven\index.html

where allure >nul 2>nul
if not errorlevel 1 (
	echo Starting Allure report server...
	call allure serve target\allure-results
)

REM Run specific test suite
REM echo Running smoke tests...
REM call mvn clean test -Dsuites=src/test/resources/testng.xml

REM Run specific test class
REM echo Running GoogleSearchTest...
REM call mvn clean test -Dtest=GoogleSearchTest

