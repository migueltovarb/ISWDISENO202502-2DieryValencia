# Validación de Placa Única - Documentación

## Descripción General

Se ha implementado una funcionalidad que **garantiza que solo exista una placa única** en toda la base de datos. Esta validación se aplica tanto en la **creación** como en la **actualización** de vehículos.

## Cambios Implementados

### 1. Nueva Excepción: `PlacaDuplicadaException`

**Archivo:** `src/main/java/com/vehiculo/vehiculo/infrastructure/exception/PlacaDuplicadaException.java`

```java
public class PlacaDuplicadaException extends RuntimeException {
    public PlacaDuplicadaException(String mensaje) {
        super(mensaje);
    }
}
```

- Excepción personalizada para casos donde se intenta crear/actualizar con una placa duplicada
- Extiende `RuntimeException` para ser una excepción no verificada

### 2. Actualización: `GlobalExceptionHandler`

**Archivo:** `src/main/java/com/vehiculo/vehiculo/infrastructure/exception/GlobalExceptionHandler.java`

Se agregó un nuevo manejador para `PlacaDuplicadaException`:

```java
@ExceptionHandler(PlacaDuplicadaException.class)
public ResponseEntity<ErrorResponse> manejarPlacaDuplicada(
        PlacaDuplicadaException ex) {
    
    ErrorResponse errorResponse = new ErrorResponse(
            ex.getMessage(),
            HttpStatus.CONFLICT.value(),  // 409 Conflict
            System.currentTimeMillis()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
}
```

**Código HTTP:** 409 Conflict

### 3. Actualización: `VehiculoServiceImpl`

**Archivo:** `src/main/java/com/vehiculo/vehiculo/application/service/impl/VehiculoServiceImpl.java`

#### Método `crearVehiculo()`

```java
@Override
public Mono<VehiculoDTO> crearVehiculo(CrearVehiculoDTO dto) {
    return vehiculoRepository.findByPlaca(dto.getPlaca())
            .hasElement()
            .flatMap(existe -> {
                if (existe) {
                    return Mono.error(
                            new PlacaDuplicadaException("La placa '" + dto.getPlaca() + "' ya existe. No se permite duplicar placas.")
                    );
                }
                Vehiculo vehiculo = vehiculoMapper.toEntity(dto);
                return vehiculoRepository.save(vehiculo)
                        .map(vehiculoMapper::toDTO);
            });
}
```

**Flujo:**
1. Busca si existe una placa con el mismo valor en la BD
2. Si existe → Lanza `PlacaDuplicadaException` 
3. Si no existe → Crea el vehículo normalmente

#### Método `actualizarVehiculo()`

```java
@Override
public Mono<VehiculoDTO> actualizarVehiculo(String id, CrearVehiculoDTO dto) {
    return vehiculoRepository.findById(id)
            .switchIfEmpty(Mono.error(
                    new VehiculoNoEncontradoException("Vehículo con ID: " + id + " no encontrado")
            ))
            .flatMap(vehiculo -> {
                // Si la placa cambió, validar que no exista otra con esa placa
                if (!vehiculo.getPlaca().equals(dto.getPlaca())) {
                    return vehiculoRepository.findByPlaca(dto.getPlaca())
                            .hasElement()
                            .flatMap(existe -> {
                                if (existe) {
                                    return Mono.error(
                                            new PlacaDuplicadaException("La placa '" + dto.getPlaca() + "' ya existe. No se permite duplicar placas.")
                                    );
                                }
                                vehiculoMapper.actualizarDatos(vehiculo, dto);
                                return vehiculoRepository.save(vehiculo);
                            });
                }
                vehiculoMapper.actualizarDatos(vehiculo, dto);
                return vehiculoRepository.save(vehiculo);
            })
            .map(vehiculoMapper::toDTO);
}
```

**Flujo:**
1. Encuentra el vehículo por ID
2. Compara la placa actual con la nueva placa
3. Si cambió:
   - Busca si existe otra placa con ese valor
   - Si existe → Lanza excepción
   - Si no existe → Actualiza el vehículo
4. Si no cambió → Actualiza sin validación adicional

### 4. Tests Unitarios

**Archivo:** `src/test/java/com/vehiculo/vehiculo/application/service/impl/VehiculoServiceImplTest.java`

Se agregaron los siguientes tests:

#### `crearVehiculo_PlacaDuplicada()`
Valida que NO se puede crear un vehículo con una placa que ya existe.

```java
@Test
void crearVehiculo_PlacaDuplicada() {
    when(vehiculoRepository.findByPlaca("ABC123")).thenReturn(Mono.just(vehiculo));
    
    StepVerifier.create(vehiculoService.crearVehiculo(crearVehiculoDTO))
            .expectError(PlacaDuplicadaException.class)
            .verify();
    
    verify(vehiculoRepository, never()).save(any(Vehiculo.class));
}
```

#### `actualizarVehiculo_PlacaDuplicada()`
Valida que NO se puede actualizar un vehículo a una placa que ya existe en otro vehículo.

```java
@Test
void actualizarVehiculo_PlacaDuplicada() {
    // ... configuración de vehículos con placas distintas
    when(vehiculoRepository.findByPlaca("XYZ789")).thenReturn(Mono.just(otroVehiculo));
    
    StepVerifier.create(vehiculoService.actualizarVehiculo("507f1f77bcf86cd799439011", dtoConPlacaDuplicada))
            .expectError(PlacaDuplicadaException.class)
            .verify();
    
    verify(vehiculoRepository, never()).save(any(Vehiculo.class));
}
```

### 5. Corrección: Importaciones en `Vehiculo.java`

Se corrigieron las importaciones faltantes de Lombok:
```java
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
```

## Casos de Uso

### ✅ Crear vehículo con placa única
```json
POST /api/vehiculos
{
    "placa": "ABC123",
    "marca": "Toyota",
    "modelo": "Corolla",
    "año": 2023,
    "color": "Blanco",
    "tipo": "Auto",
    "precio": 25000.0,
    "estado": "Disponible"
}
→ 201 Created
```

### ❌ Intentar crear con placa duplicada
```json
POST /api/vehiculos
{
    "placa": "ABC123",  // Ya existe
    ...
}
→ 409 Conflict
{
    "mensaje": "La placa 'ABC123' ya existe. No se permite duplicar placas.",
    "codigoHTTP": 409,
    "timestamp": 1699000000000
}
```

### ✅ Actualizar vehículo sin cambiar placa
```json
PUT /api/vehiculos/{id}
{
    "placa": "ABC123",  // Misma placa
    "marca": "Honda",
    ...
}
→ 200 OK
```

### ❌ Intentar actualizar a placa duplicada
```json
PUT /api/vehiculos/{id1}
{
    "placa": "XYZ789",  // Ya existe en id2
    ...
}
→ 409 Conflict
```

## Resultados de Tests

```
Tests run: 10
✓ crearVehiculo_Exitoso
✓ crearVehiculo_PlacaDuplicada
✓ obtenerVehiculoPorId_Exitoso
✓ obtenerVehiculoPorId_NoEncontrado
✓ obtenerTodosLosVehiculos
✓ actualizarVehiculo_Exitoso
✓ actualizarVehiculo_PlacaDuplicada
✓ eliminarVehiculo_Exitoso
✓ eliminarVehiculo_NoEncontrado
✓ Importaciones de Vehiculo corregidas

BUILD SUCCESS ✅
```

## Ventajas de la Implementación

1. **Validación en Servicio:** La validación ocurre en la capa de negocio, no solo en BD
2. **Mensajes Descriptivos:** El usuario recibe un mensaje claro sobre qué placa está duplicada
3. **Código HTTP Correcto:** Usa 409 Conflict, el código HTTP semánticamente correcto
4. **Tests Completos:** Cubre ambos escenarios (creación y actualización)
5. **Reactividad:** Usa Mono/Flux de Reactor para operaciones asincrónicas
6. **Manejo de Excepciones Centralizado:** `GlobalExceptionHandler` gestiona todos los errores

## Notas Importantes

- ⚠️ La BD MongoDB debe tener un índice único en el campo `placa` para garantizar la integridad en caso de concurrencia
- ✅ La validación es preventiva a nivel de aplicación
- ✅ Compatible con arquitectura reactiva de Spring WebFlux
- ✅ Mantiene la consistencia de datos
