@echo off
cd /d "%~dp0"
if exist ".venv\Scripts\python.exe" goto pronto
where py >nul 2>&1
if %errorlevel% equ 0 (
    py -3 -m venv .venv
) else (
    python -m venv .venv
)
if errorlevel 1 exit /b 1
:pronto
".venv\Scripts\python.exe" --version
