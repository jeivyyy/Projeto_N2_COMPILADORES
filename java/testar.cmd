@echo off
chcp 65001 >nul
cd /d "%~dp0"
call preparar_ambiente.cmd
if errorlevel 1 exit /b 1
".java-env\runtime\bin\java.exe" -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp ".java-env\classes" TestCompilador
