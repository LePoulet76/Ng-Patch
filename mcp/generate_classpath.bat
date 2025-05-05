@echo off
setlocal enabledelayedexpansion

set LIBDIR=lib
set CP=

for %%f in (%LIBDIR%\*.jar) do (
    set "f=%%~nxf"
    set "CP=!CP!,%LIBDIR:/=\!/!f!"
)

REM Ajouter minecraft_server.jar
set "CP=!CP!,jars/minecraft_server.jar"

REM Remplacer les \ par / pour MCP
set "CP=!CP:\=/!"

echo ClassPathServer = !CP!
pause