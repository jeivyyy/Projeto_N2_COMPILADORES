$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot
& ".\preparar_ambiente.ps1"
$java = Join-Path $PSScriptRoot ".java-env/runtime/bin/java.exe"
& $java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp ".java-env/classes" TestCompilador
