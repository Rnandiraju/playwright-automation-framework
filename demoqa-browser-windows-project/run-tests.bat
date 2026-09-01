@echo off
call mvn clean test
if errorlevel 1 exit /b %errorlevel%
echo DemoQA Browser Windows tests passed.
