# 🚗 API de Gestión de Vehículos

[![Java](https://img.shields.io/badge/Java-17-orange?logo=java)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-green?logo=spring)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-5.0-green?logo=mongodb)](https://www.mongodb.com/)
[![Maven](https://img.shields.io/badge/Maven-3.6-red?logo=apachemaven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)

Una API REST profesional y completa para gestionar vehículos usando Spring Boot, MongoDB y arquitectura limpia.

## 📋 Tabla de Contenidos

- [🌟 Características](#-características)
- [🛠️ Tecnologías](#️-tecnologías)
- [⚙️ Requisitos](#️-requisitos)
- [📥 Instalación](#-instalación)
- [🚀 Inicio Rápido](#-inicio-rápido)
- [📚 Documentación](#-documentación)
- [🔌 Endpoints](#-endpoints)
- [📝 Ejemplos](#-ejemplos)
- [🏗️ Arquitectura](#️-arquitectura)
- [✅ Testing](#-testing)
- [📞 Soporte](#-soporte)

## 🌟 Características

### CRUD Completo
- ✅ **Crear** nuevos vehículos
- ✅ **Leer** vehículos (todos, por ID, por placa)
- ✅ **Actualizar** datos de vehículos
- ✅ **Eliminar** vehículos

### Arquitectura 
- ✅ Arquitectura limpia en 4 capas
- ✅ Separación de responsabilidades
- ✅ Inyección de dependencias
- ✅ Patrones de diseño implementados
- ✅ Principios SOLID aplicados

### Código de Calidad
- ✅ Código limpio y legible
- ✅ Nombres descriptivos
- ✅ Métodos pequeños y enfocados
- ✅ Sin código duplicado
- ✅ Documentación inline

### Base de Datos
- ✅ MongoDB NoSQL integrado
- ✅ Operaciones reactivas
- ✅ Persistencia automática de fechas
- ✅ Índices automáticos

### API REST
- ✅ Endpoints RESTful
- ✅ Códigos HTTP correctos (201, 200, 204, 404, 500)
- ✅ CORS habilitado
- ✅ JSON para request/response
- ✅ Manejo de errores centralizado

### Testing
- ✅ 7 casos de prueba unitaria
- ✅ Mocking con Mockito
- ✅ Testing reactivo con Reactor


## 🛠️ Tecnologías

| Tecnología | Versión | Propósito |
|------------|---------|----------|
| **Java** | 17 | Lenguaje de programación |
| **Spring Boot** | 3.5.7 | Framework web |
| **Spring WebFlux** | 3.5.7 | Programación reactiva |
| **Spring Data MongoDB** | Reactive | Acceso a datos |
| **MongoDB** | 5.0+ | Base de datos |
| **Lombok** | 1.18.30 | Reduce boilerplate |
| **JUnit 5** | 5.9.3 | Testing unitario |
| **Mockito** | 5.2.1 | Mocking |
| **Maven** | 3.6+ | Gestor de dependencias |
| **Reactor** | 2022.0.13 | Programación reactiva |

## ⚙️ Requisitos

### Requisitos del Sistema
- **Java 17** o superior
- **Maven 3.6** o superior
- **MongoDB 5.0** o superior
- **500MB** de espacio en disco
- **Windows/Mac/Linux**

### Verificar Instalación
```bash
# Java
java -version

# Maven
mvn -version

# MongoDB (después de instalar)
mongosh
```



### 2. Clonar el Repositorio
```bash
git clone <url-del-repositorio>
cd vehiculo
```

### 3. Compilar el Proyecto
```bash
mvn clean install
```

Deberías ver:
```
[INFO] BUILD SUCCESS
[INFO] Total time: X.XXX s
```

### 4. Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

## 🚀 Inicio Rápido

### Paso 1: Verificar MongoDB
```bash
mongosh
```

Deberías ver el prompt de MongoDB. Si no funciona, asegúrate de que MongoDB está corriendo.

### Paso 2: Compilar
```bash
mvn clean install
```

### Paso 3: Ejecutar
```bash
mvn spring-boot:run
```

Espera hasta ver:
```
Started VehiculoApplication in X.XXX seconds
Tomcat started on port(s): 8080
```

### Paso 4: Probar
Abre en tu navegador o Postman:
```
GET http://localhost:8080/api/vehiculos
```

¡Listo! La API está corriendo. 🎉

## 📚 Documentación

Documentación completa disponible en:

| Documento | Descripción |
|-----------|-------------|
| **[PROYECTO_COMPLETO.md](./PROYECTO_COMPLETO.md)** | 📊 Resumen visual y estructura |
| **[API_DOCUMENTACION.md](./API_DOCUMENTACION.md)** | 📖 Guía completa de endpoints |
| **[ARQUITECTURA.md](./ARQUITECTURA.md)** | 🏗️ Diagramas y diseño |
| **[PRINCIPIOS_SOLID.md](./PRINCIPIOS_SOLID.md)** | 🎓 Principios implementados |
| **[FAQ.md](./FAQ.md)** | ❓ Preguntas frecuentes |
| **[RESUMEN_IMPLEMENTACION.md](./RESUMEN_IMPLEMENTACION.md)** | 📝 Detalles técnicos |
| **[CHECKLIST.txt](./CHECKLIST.txt)** | ✅ Lista de verificación |
| **[INDICE.md](./INDICE.md)** | 📑 Índice de documentación |

## 🔌 Endpoints

### Base URL
```
http://localhost:8080/api/vehiculos
```

### Operaciones Disponibles

#### 1. Crear Vehículo
```http
POST /api/vehiculos
Content-Type: application/json

{
  "placa": "ABC123",
  "marca": "Toyota",
  "modelo": "Corolla",
  "año": 2023,
  "color": "Blanco",
  "tipo": "Auto",
  "precio": 25000.00,
  "estado": "Disponible"
}
```

**Response: 201 Created**
```json
{
  "id": "507f1f77bcf86cd799439011",
  "placa": "ABC123",
  "marca": "Toyota",
  "modelo": "Corolla",
  "año": 2023,
  "color": "Blanco",
  "tipo": "Auto",
  "precio": 25000.00,
  "estado": "Disponible",
  "fechaCreacion": "2025-11-15T10:30:00",
  "fechaActualizacion": "2025-11-15T10:30:00"
}
```

#### 2. Obtener Todos los Vehículos
```http
GET /api/vehiculos
```

**Response: 200 OK**
```json
[
  {
    "id": "507f1f77bcf86cd799439011",
    "placa": "ABC123",
    ...
  },
  {
    "id": "507f1f77bcf86cd799439012",
    "placa": "XYZ789",
    ...
  }
]
```

#### 3. Obtener Vehículo por ID
```http
GET /api/vehiculos/507f1f77bcf86cd799439011
```

**Response: 200 OK** o **404 Not Found**

#### 4. Obtener Vehículo por Placa
```http
GET /api/vehiculos/placa/ABC123
```

**Response: 200 OK** o **404 Not Found**

#### 5. Actualizar Vehículo
```http
PUT /api/vehiculos/507f1f77bcf86cd799439011
Content-Type: application/json

{
  "precio": 23000.00,
  "estado": "No disponible"
}
```

**Response: 200 OK** con datos actualizados

#### 6. Eliminar Vehículo
```http
DELETE /api/vehiculos/507f1f77bcf86cd799439011
```

**Response: 204 No Content**

### Tabla de Endpoints

| Método | Endpoint | Descripción | Status |
|--------|----------|-------------|--------|
| POST | `/api/vehiculos` | Crear vehículo | 201 |
| GET | `/api/vehiculos` | Obtener todos | 200 |
| GET | `/api/vehiculos/{id}` | Obtener por ID | 200/404 |
| GET | `/api/vehiculos/placa/{placa}` | Obtener por placa | 200/404 |
| PUT | `/api/vehiculos/{id}` | Actualizar | 200/404 |
| DELETE | `/api/vehiculos/{id}` | Eliminar | 204/404 |

## 📝 Ejemplos

### Usando cURL

**Crear vehículo:**
```bash
curl -X POST http://localhost:8080/api/vehiculos \
  -H "Content-Type: application/json" \
  -d '{
    "placa":"ABC123",
    "marca":"Toyota",
    "modelo":"Corolla",
    "año":2023,
    "color":"Blanco",
    "tipo":"Auto",
    "precio":25000.00,
    "estado":"Disponible"
  }'
```

**Obtener todos:**
```bash
curl http://localhost:8080/api/vehiculos
```

**Obtener por ID:**
```bash
curl http://localhost:8080/api/vehiculos/507f1f77bcf86cd799439011
```

**Obtener por placa:**
```bash
curl http://localhost:8080/api/vehiculos/placa/ABC123
```

**Actualizar:**
```bash
curl -X PUT http://localhost:8080/api/vehiculos/507f1f77bcf86cd799439011 \
  -H "Content-Type: application/json" \
  -d '{"precio":23000.00}'
```

**Eliminar:**
```bash
curl -X DELETE http://localhost:8080/api/vehiculos/507f1f77bcf86cd799439011
```

### Usando Postman

1. Descarga [Postman](https://www.postman.com/downloads/)
2. Importa `EJEMPLOS_REQUESTS.json`
3. Ejecuta los ejemplos

### Usando Thunder Client (VS Code)

1. Instala extensión "Thunder Client"
2. Copia los ejemplos de `EJEMPLOS_REQUESTS.json`
3. Prueba directamente en VS Code


## 🏗️ Arquitectura

### Capas de la Aplicación

```
┌─────────────────────────────────────┐
│  Presentation (REST Controllers)    │ ← Recibe requests HTTP
├─────────────────────────────────────┤
│  Application (Services, DTOs)       │ ← Lógica de negocio
├─────────────────────────────────────┤
│  Domain (Entities, Repositories)    │ ← Modelo y contratos
├─────────────────────────────────────┤
│  Infrastructure (Exceptions)        │ ← Manejo de errores
└─────────────────────────────────────┘
         ↓
    MONGODB (Base de datos)
```

### Estructura de Carpetas

```
src/main/java/com/vehiculo/vehiculo/
├── domain/                          # Capa de dominio
│   ├── entity/
│   │   └── Vehiculo.java           # Entidad MongoDB
│   └── repository/
│       └── VehiculoRepository.java  # Acceso a datos
│
├── application/                     # Capa de aplicación
│   ├── dto/
│   │   ├── VehiculoDTO.java        # DTO de respuesta
│   │   └── CrearVehiculoDTO.java   # DTO de entrada
│   ├── service/
│   │   ├── VehiculoService.java    # Interfaz de servicio
│   │   └── impl/
│   │       └── VehiculoServiceImpl.java  # Implementación
│   └── mapper/
│       └── VehiculoMapper.java     # Conversión de datos
│
├── presentation/                    # Capa de presentación
│   └── controller/
│       └── VehiculoController.java  # Endpoints REST
│
├── infrastructure/                  # Capa de infraestructura
│   └── exception/
│       ├── VehiculoNoEncontradoException.java
│       ├── ErrorResponse.java
│       └── GlobalExceptionHandler.java
│
└── VehiculoApplication.java         # Clase principal
```

### Flujo de Datos (Ejemplo: Crear Vehículo)

## ✅ Testing

### Ejecutar Pruebas

```bash
mvn test
```

Deberías ver:
```
[INFO] -------------------------------------------------------
[INFO] T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.vehiculo.vehiculo.application.service.impl.VehiculoServiceImplTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
[INFO] -------------------------------------------------------
[INFO] BUILD SUCCESS
```

### Ejecutar un Test Específico

```bash
mvn test -Dtest=VehiculoServiceImplTest#crearVehiculo_Exitoso
```

## 🐛 Solución de Problemas

### Port 8080 Already in Use
```bash
# Windows - Ver qué está usando el puerto
netstat -ano | findstr :8080

# Matar el proceso
taskkill /PID <PID> /F
```

**O cambiar puerto en `application.properties`:**
```properties
server.port=8081
```

### MongoDB Connection Refused
1. Verifica que MongoDB está corriendo
2. Comprueba que está en localhost:27017
3. Usa `mongosh` para conectarte manualmente

### Build Success pero No Compila
```bash
mvn clean compile -DskipTests
```

### Dependencias no Descargan
```bash
# Limpiar caché de Maven
mvn clean
mvn install -U
```

## 🎯 Estructura del Código

### Principios SOLID Aplicados

- ✅ **S**ingle Responsibility - Una responsabilidad por clase
- ✅ **O**pen/Closed - Abierto para extensión, cerrado para modificación
- ✅ **L**iskov Substitution - Sustitución de tipos
- ✅ **I**nterface Segregation - Interfaces específicas
- ✅ **D**ependency Inversion - Dependencias en abstracciones

### Patrones de Diseño

- ✅ **Repository Pattern** - Abstracción de acceso a datos
- ✅ **Service Layer** - Lógica de negocio centralizada
- ✅ **DTO Pattern** - Transfer Objects
- ✅ **Mapper Pattern** - Conversión entre objetos
- ✅ **Exception Handler** - Manejo centralizado de errores
- ✅ **Dependency Injection** - Inyección de dependencias
`

## 📦 Dependencias

| Dependencia | Propósito |
|-------------|----------|
| spring-boot-starter-webflux | REST y programación reactiva |
| spring-data-mongodb-reactive | MongoDB reactivo |
| spring-boot-starter-validation | Validaciones |
| lombok | Reduce boilerplate |
| spring-boot-starter-test | Testing |
| reactor-test | Testing reactivo |

Ver `pom.xml` para versiones exactas.


Autor: Diery Valencia 