#!/bin/bash
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

echo "Iniciando Eureka Server..."
(cd "$SCRIPT_DIR/eureka" && ./mvnw spring-boot:run) &

sleep 15

echo "Iniciando microservicios..."
(cd "$SCRIPT_DIR/usuarios" && ./mvnw spring-boot:run) &
(cd "$SCRIPT_DIR/interaccion" && ./mvnw spring-boot:run) &
(cd "$SCRIPT_DIR/reporte" && ./mvnw spring-boot:run) &
(cd "$SCRIPT_DIR/gateway" && ./mvnw spring-boot:run) &

wait
