$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot
if (!(Get-Command javac -ErrorAction SilentlyContinue)) { throw "É necessário instalar o JDK 17+ (javac)." }
if (!(Get-Command jlink -ErrorAction SilentlyContinue)) { throw "É necessário o jlink do JDK 17+." }
$classes = Join-Path $PSScriptRoot ".java-env/classes"
$runtime = Join-Path $PSScriptRoot ".java-env/runtime"
New-Item -ItemType Directory -Force -Path $classes | Out-Null
if (!(Test-Path (Join-Path $runtime "bin/java.exe"))) {
    & jlink --add-modules java.base,jdk.localedata --strip-debug --no-header-files --no-man-pages --output $runtime
    if ($LASTEXITCODE -ne 0) { throw "Falha ao criar runtime local." }
}
$fontes = @(Get-ChildItem -Path $PSScriptRoot -Filter *.java | ForEach-Object { $_.FullName })
& javac -encoding UTF-8 -d $classes @fontes
if ($LASTEXITCODE -ne 0) { throw "Falha na compilação do projeto Java." }
Write-Host "Ambiente local criado em .java-env (runtime próprio e classes isoladas)."
