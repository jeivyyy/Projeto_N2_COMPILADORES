$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot
if (!(Test-Path ".venv/Scripts/python.exe")) {
    if (Get-Command py -ErrorAction SilentlyContinue) {
        & py -3 -m venv .venv
    } elseif (Get-Command python -ErrorAction SilentlyContinue) {
        & python -m venv .venv
    } else {
        throw "Python 3.10+ não encontrado."
    }
}
Write-Host "Ambiente Python preparado: .venv"
& ".\.venv\Scripts\python.exe" --version
