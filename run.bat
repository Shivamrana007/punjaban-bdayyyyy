@echo off
title My Babygirl Punjaban - Birthday Celebration Server
color 0D

echo ===============================================================
echo    💖 MY BABYGIRL PUNJABAN - BIRTHDAY CELEBRATION 💖
echo ===============================================================
echo.

where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [!] Java is not found in your system PATH.
    echo [*] Opening the website directly in your default browser...
    start index.html
    echo.
    echo [*] Website launched successfully!
    echo [*] (If you want to use the Java server in the future, install JDK from https://adoptium.net)
    pause
    exit /b
)

echo [*] Compiling BirthdayServer.java...
javac BirthdayServer.java
if %ERRORLEVEL% NEQ 0 (
    echo [!] Compilation failed. Opening index.html directly...
    start index.html
    pause
    exit /b
)

echo [*] Starting Java server on port 8080...
java BirthdayServer
pause
