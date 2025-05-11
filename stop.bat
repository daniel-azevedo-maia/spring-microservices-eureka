@echo off
echo ========================================
echo [STOP] Derrubando containers e limpando antigos...
echo ========================================
docker-compose down
docker rm -f discovery-server user-service order-service product-service postgres-user postgres-order postgres-product 2>nul
pause
