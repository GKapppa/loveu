REM para iniciar el bat .\iniciar.bat

@echo off
SET SRIPT_DIR=%~dp0

echo Iniciando Eureka Server...
start /b cmd /c "cd /d "%SCRIPT_DIR%eureka" && mvnw.cmd  spring-boot:run"

echo Esperando 15 segundos...
timeout /t 15 /nobreak > null

echo Iniciando microservicios...
start /b cmd /c "cd /d "%SCRIPT_DIR%usuarios" && mvnw.cmd  spring-boot:run"
start /b cmd /c "cd /d "%SCRIPT_DIR%interaccion" && mvnw.cmd  spring-boot:run"
start /b cmd /c "cd /d "%SCRIPT_DIR%reporte" && mvnw.cmd  spring-boot:run"
start /b cmd /c "cd /d "%SCRIPT_DIR%gateway" && mvnw.cmd  spring-boot:run"

echo Todos los servicios estan iniciando en segundo plano.
pause