param(
  [string]$OutName = ""
)

$ErrorActionPreference = "Stop"

$ProjectDir = Get-Location
if (-not $OutName) {
  $OutName = "$(Split-Path -Leaf $ProjectDir).zip"
}
$OutPath = Join-Path $ProjectDir $OutName

Write-Host "Working dir: $ProjectDir"
Write-Host "Output zip: $OutPath"

# 1) Maven clean
if (Get-Command mvn -ErrorAction SilentlyContinue) {
  Write-Host "Running: mvn clean"
  mvn clean | Out-Null
} else {
  Write-Warning "mvn nie znaleziony w PATH — pomijam 'mvn clean'"
}

# 2) Javadoc
if (Get-Command mvn -ErrorAction SilentlyContinue) {
  Write-Host "Generating Javadoc: mvn javadoc:javadoc"
  mvn javadoc:javadoc | Out-Null
} else {
  Write-Warning "mvn nie znaleziony — pomijam generowanie javadoc"
}

# 3) Zip (wyklucz .git, .gitignore, README.md)
if (Test-Path $OutPath) {
  Remove-Item $OutPath -Force
}

# Zbuduj listę plików do zapakowania (exclude określone wzorce)
$excludes = @(".git", ".gitignore", "README.md", "README.MD", "README", "build-package.ps1")
$files = Get-ChildItem -Recurse -File | Where-Object {
  foreach ($exc in $excludes) {
    # jeśli plik lub sciezka zawiera .git\ lub folder .git gdziekolwiek - pomiń
    if ($_.FullName -match [regex]::Escape("\.git\")) { return $false }
    # nazwa pliku równająca się exclude -> pomiń
    if ($_.Name -ieq $exc) { return $false }
  }
  return $true
}

if (-not (Get-Command Compress-Archive -ErrorAction SilentlyContinue)) {
  Write-Error "Brak Compress-Archive. Upewnij się, że używasz PowerShell 5+."
  exit 1
}

# Stwórz tymczasowy folder i skopiuj tam pliki zachowując strukturę
$tmp = Join-Path $env:TEMP ("package_tmp_" + [guid]::NewGuid().ToString())
New-Item -ItemType Directory -Path $tmp | Out-Null

foreach ($f in $files) {
  $dest = Join-Path $tmp ($f.FullName.Substring($ProjectDir.Path.Length).TrimStart('\'))
  $destDir = Split-Path $dest
  if (-not (Test-Path $destDir)) { New-Item -ItemType Directory -Path $destDir -Force | Out-Null }
  Copy-Item $f.FullName -Destination $dest -Force
}

Compress-Archive -Path (Join-Path $tmp '*') -DestinationPath $OutPath -Force
Remove-Item $tmp -Recurse -Force

Write-Host "Stworzono: $OutPath"
Write-Host "Gotowe."
