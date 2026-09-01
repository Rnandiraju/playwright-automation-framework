@REM Licensed to the Apache Software Foundation (ASF)
@REM Maven Wrapper

@if "%DEBUG%" == "" @echo off
@REM enable echoing by setting the variable
@setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@REM Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi
set MAVEN_PROJECTBASEDIR=%APP_HOME%
if "%MAVEN_PROJECTBASEDIR:~-1%" == "\" set MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%

@REM Add default JVM options here. You can also use JAVA_OPTS and MAVEN_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

@REM Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >nul 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@REM Download Maven if not present
if exist "%APP_HOME%\.mvn\wrapper\maven-wrapper.jar" goto mavenStart

echo Downloading Maven wrapper...
cd /d "%APP_HOME%"

if not exist ".mvn\wrapper" mkdir ".mvn\wrapper"

powershell -Command "(New-Object Net.WebClient).DownloadFile('https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.1.0/maven-wrapper-3.1.0.jar', '.mvn\wrapper\maven-wrapper.jar')"

if "%ERRORLEVEL%" neq "0" (
    echo.
    echo ERROR: Failed to download Maven wrapper.
    echo.
    goto fail
)

:mavenStart
@REM Find the project base dir, i.e. the directory that contains the functional maven wrapper jar.
cd /d "%APP_HOME%"

@REM Execute Maven
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %MAVEN_OPTS% "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" -classpath "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar" org.apache.maven.wrapper.MavenWrapperMain %*

:end
@endlocal & exit /b %ERRORLEVEL%

:fail
@endlocal & exit /b 1
