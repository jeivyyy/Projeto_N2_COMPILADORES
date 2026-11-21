@echo off
chcp 65001 >nul
setlocal EnableExtensions
cd /d "%~dp0"
where javac >nul 2>&1
if errorlevel 1 (
    echo JDK 17 ou superior nao encontrado. 1>&2
    exit /b 1
)
if not exist ".java-env\classes" mkdir ".java-env\classes"
if not exist ".java-env\runtime\bin\java.exe" (
    jlink --add-modules java.base,jdk.localedata --strip-debug --no-header-files --no-man-pages --output ".java-env\runtime"
    if errorlevel 1 exit /b 1
)
javac -encoding UTF-8 -d ".java-env\classes" *.java
if errorlevel 1 exit /b 1
".java-env\runtime\bin\java.exe" -version
