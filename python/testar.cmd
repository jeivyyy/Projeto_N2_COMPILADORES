@echo off
cd /d "%~dp0"
if not exist ".venv\Scripts\python.exe" call preparar_ambiente.cmd
if errorlevel 1 exit /b 1
".venv\Scripts\python.exe" -m unittest -v test_compilador
