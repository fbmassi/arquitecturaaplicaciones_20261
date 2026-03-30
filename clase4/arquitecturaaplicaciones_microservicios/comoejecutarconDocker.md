# Cómo ejecutar inventory-service con Docker (modo mínimo)

Nota de seguridad: "Solo para desarrollo; en producción usar variables de entorno / vault y credenciales robustas".

Levanta únicamente los 5 servicios necesarios para usar **inventory-service** via API Gateway, sin brokers de mensajería ni herramientas de observabilidad.

Consumo estimado: **~1.5 GB RAM** (frente a los ~4-6 GB del compose completo).

---

## Requisitos previos

- Java 21+
- Maven 3.9+
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) corriendo

---

## Levantar los servicios

```bash
docker compose -f docker-compose.minimal.yml up --build
```

Docker levanta los servicios en el orden correcto automáticamente gracias a los `depends_on` y healthchecks:

| Orden | Servicio          | Puerto | Memoria límite |
|-------|-------------------|--------|----------------|
| 1     | Config Server     | 8888   | 256 MB         |
| 2     | Eureka Server     | 8761   | 256 MB         |
| 3     | Auth Service      | 8083   | 384 MB         |
| 4     | Inventory Service | 8082   | 384 MB         |
| 5     | API Gateway       | 8080   | 384 MB         |

Una vez que la terminal muestre que el `api-gateway` está registrado en Eureka, el sistema está listo.

---

## Detener los servicios

```bash
# Ctrl+C para frenar, luego:
docker compose -f docker-compose.minimal.yml down
```

---

Para ver las imágenes generadas después del build:

docker images


## Optimizaciones de memoria aplicadas

Cada contenedor tiene configuradas las siguientes flags de JVM via `JAVA_TOOL_OPTIONS`:

| Flag | Efecto |
|------|--------|
| `-XX:+UseSerialGC` | Garbage collector de menor footprint de memoria |
| `-XX:MaxRAMPercentage=75.0` | La JVM usa como máximo el 75% del límite del contenedor |
| `-Xss256k` | Reduce el tamaño del stack por hilo (default 512k) |
| `-XX:TieredStopAtLevel=1` | Compilación JIT mínima, ahorra memoria en arranque |

---

## Liberar espacio en disco (opcional)

Después de trabajar, para recuperar el espacio ocupado por imágenes y capas de build:

```bash
# Eliminar contenedores detenidos, redes huérfanas e imágenes sin usar
docker system prune

# Incluir también volúmenes (más agresivo)
docker system prune --volumes
```

Para ver cuánto espacio ocupa Docker actualmente:

```bash
docker system df
```

---

## Verificación rápida

| Servicio      | URL de salud                              |
|---------------|-------------------------------------------|
| Config Server | http://localhost:8888/actuator/health     |
| Eureka        | http://localhost:8761                     |
| Auth Service  | http://localhost:8083/actuator/health     |
| API Gateway   | http://localhost:8080/actuator/health     |
| Inventory     | http://localhost:8082/actuator/health     |
