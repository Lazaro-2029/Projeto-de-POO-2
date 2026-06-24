@echo off
echo Editora Publixy

where mvn >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo [ERRO] Maven nao encontrado no PATH.
    echo Instale em: https://maven.apache.org/download.cgi
    pause
    exit /b 1
)

where java >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo [ERRO] Java nao encontrado no PATH.
    echo Instale JDK 17+ em: https://adoptium.net
    pause
    exit /b 1
)

mvn javafx:run
pause
