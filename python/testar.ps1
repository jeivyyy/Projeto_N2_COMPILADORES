$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot
if (!(Test-Path ".venv/Scripts/python.exe")) { & ".\preparar_ambiente.ps1" }
& ".\.venv\Scripts\python.exe" -m unittest -v test_compilador
