$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot
if (!(Test-Path ".venv/Scripts/python.exe")) { & ".\preparar_ambiente.ps1" }
& ".\.venv\Scripts\python.exe" main.py @args
