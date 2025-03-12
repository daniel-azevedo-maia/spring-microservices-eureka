@echo off
echo ========================================
echo [1] Compilando projeto (Maven build)...
echo ========================================
CALL mvn clean package -DskipTests

echo ========================================
echo [2] Derrubando containers e removendo antigos...
echo ========================================
CALL docker-compose down
CALL docker rm -f discovery-server user-service order-service product-service postgres-user postgres-order postgres-product 2>nul

echo ========================================
echo [3] Subindo containers com Docker Compose...
echo ========================================
CALL docker-compose up --build
pause
