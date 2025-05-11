@echo off
setlocal enabledelayedexpansion

echo ========================================
echo [1] Compilando projeto com Maven...
echo ========================================
CALL mvn clean package -DskipTests

IF %ERRORLEVEL% NEQ 0 (
    echo ERRO: Falha na compilação com Maven. Verifique os erros acima.
    pause
    exit /b %ERRORLEVEL%
)

echo ========================================
echo [2] Derrubando containers e limpando resíduos...
echo ========================================
CALL docker-compose down -v --remove-orphans

echo Removendo containers manualmente (caso ainda existam)...
FOR %%C IN (discovery-server user-service order-service product-service api-gateway-service postgres-user postgres-order postgres-product) DO (
    docker rm -f %%C 2>nul
)

echo Limpando imagens não utilizadas...
CALL docker image prune -af --filter "until=1h"

echo ========================================
echo [3] Subindo containers com Docker Compose (rebuild forçado)...
echo ========================================
CALL docker-compose up --build --force-recreate

IF %ERRORLEVEL% NEQ 0 (
    echo ERRO: Falha ao iniciar os containers. Verifique o docker-compose.
    pause
    exit /b %ERRORLEVEL%
)

echo ========================================
echo Projeto iniciado com sucesso!
echo Abra http://localhost:8761 para ver o Discovery Server (Eureka).
echo Pressione qualquer tecla para encerrar este terminal...
pause
endlocal
