# UrbanPark — Solución guiada en Kotlin

## 1. Objetivo

Esta solución implementa el caso **UrbanPark Centro** usando Kotlin y corrutinas. El enunciado exige reconocer tres tipos de vehículos, cuatro estados de espacio, calcular tarifas, manej/r errores sin detener la sesión, mantener consultas de ne/gocio y simular operaciones asíncronas de entrada/salida. También exige separar las responsabilidades en varios archivos `.kt` y declarar `kotlinx-coroutines-core` en `build.gradle.kts`.

## 2. Estructura del proyecto

```text
UrbanPark/
├── build.gradle.kts
├── settings.gradle.kts
├── README.md
└── src/
    └── main/
        └── kotlin/
            └── cl/urbanpark/
                ├── Main.kt
                ├── Models.kt
                ├── Validators.kt
                ├── Tarificador.kt
                └── Estacionamiento.kt
```

### Responsabilidad de cada archivo

**Models.kt**: contiene los modelos de datos y los tipos enumerados: clientes, vehículos, estados de espacio, espacios y tickets.

**Validators.kt**: concentra las validaciones de datos. La patente se valida con una expresión regular y el tipo de cliente se restringe mediante `enum class`.

**Tarificador.kt**: contiene exclusivamente las reglas de cobro.

**Estacionamiento.kt**: administra los espacios, entradas, salidas, historial, recaudación y consultas de negocio. También contiene la sincronización necesaria para que varias corrutinas no modifiquen el mismo estado al mismo tiempo.

**Main.kt**: sirve como punto de entrada y demuestra el funcionamiento del sistema con los datos sugeridos por el enunciado.

## 3. Lógica general del problema

La idea principal es separar el sistema en cuatro bloques:

1. **Representar los datos**.
2. **Validar que los datos sean correctos**.
3. **Calcular el cobro según el tipo de vehículo y cliente**.
4. **Administrar los espacios y las operaciones de entrada/salida**.

Esto permite que `Main.kt` no tenga toda la lógica mezclada.

## 4. Modelado de vehículos

Todos los vehículos comparten patente, marca/modelo, hora de ingreso y tipo de cliente. Por eso se utiliza una clase sellada:

```kotlin
sealed class Vehiculo(
    open val patente: String,
    open val marcaModelo: String,
    open val ingreso: LocalDateTime,
    open val cliente: TipoCliente
) {
    abstract val tipo: TipoVehiculo
}
```

Después se crean tres clases concretas:

```kotlin
data class Particular(...) : Vehiculo(...)
data class Moto(...) : Vehiculo(...)
data class Camioneta(...) : Vehiculo(...)
```

### ¿Por qué `sealed class`?

Porque conocemos de antemano las variantes permitidas: particular, moto y camioneta. Además, al usar `when (vehiculo)`, Kotlin puede trabajar de manera segura con esas variantes.

### ¿Por qué `data class`?

Porque estas clases representan datos. Kotlin genera automáticamente funciones útiles como `toString()`, `equals()`, `hashCode()` y `copy()`.

## 5. `enum class`

Los valores que solo pueden pertenecer a un conjunto fijo se modelan con `enum class`:

```kotlin
enum class TipoCliente {
    REGULAR,
    ABONADO,
    DISCAPACITADO
}
```

Lo mismo se hace con `TipoVehiculo` y `EstadoEspacio`.

Esto evita errores como escribir libremente `"abonadoo"` o `"motocicleta"` en distintos lugares del programa.

## 6. Estados de los espacios

El enunciado exige cuatro estados:

```kotlin
enum class EstadoEspacio {
    LIBRE,
    OCUPADO,
    PROCESANDO,
    FUERA_DE_SERVICIO
}
```

El programa nunca debería asumir que un espacio solo puede estar libre u ocupado.

Por ejemplo, al mostrar el estado:

```kotlin
val detalle = when (espacio.estado) {
    EstadoEspacio.LIBRE -> "disponible"
    EstadoEspacio.OCUPADO -> "vehículo=${espacio.vehiculo?.patente ?: "sin patente"}"
    EstadoEspacio.PROCESANDO -> espacio.motivoProcesando ?: "procesando"
    EstadoEspacio.FUERA_DE_SERVICIO -> espacio.motivoFueraServicio ?: "fuera de servicio"
}
```

`when` obliga a considerar todas las opciones del `enum`.

## 7. Validación de patente

La patente exigida por el enunciado tiene el formato `AA00AA`.

Se utiliza:

```kotlin
private val patenteRegex = Regex("^[A-Z]{2}[0-9]{2}[A-Z]{2}$")
```

La validación se hace con:

```kotlin
require(patenteRegex.matches(patente)) {
    "Patente inválida. Debe tener el formato AB12CD."
}
```

### Sintaxis importante: `require`

`require(condición) { mensaje }` sirve para indicar que un argumento recibido por una función debe cumplir una condición.

Si la condición es falsa, se lanza `IllegalArgumentException`.

## 8. Cálculo de tarifa

El cálculo debe respetar este orden:

1. Calcular el valor según tipo de vehículo.
2. Aplicar IVA del 19%.
3. Si el cliente es discapacitado, aplicar 50% de descuento sobre el monto con IVA.

En código:

```kotlin
val conIva = montoBase * 1.19

val final = if (vehiculo.cliente == TipoCliente.DISCAPACITADO) {
    conIva * 0.50
} else {
    conIva
}
```

### Particular

Tarifa base: `$1.500` por hora.

Cliente abonado: descuento del 20%.

```kotlin
val subtotal = 1500.0 * horas
if (vehiculo.cliente == TipoCliente.ABONADO) {
    subtotal * 0.80
} else {
    subtotal
}
```

### Moto

Tarifa base: `$800` por hora.

Menos de 30 minutos: `$0`, independientemente del cliente.

```kotlin
if (minutosEstacionado < 30) {
    0.0
} else {
    800.0 * horas
}
```

Esto es una excepción explícita del R1, por lo que la validación de tarifa permite el cero solamente en ese caso.

### Camioneta

Tarifa base: `$2.500` por hora.

Si es carga pesada, recargo de 30%:

```kotlin
if (vehiculo.cargaPesada) subtotal *= 1.30
```

## 9. Decisión sobre las fracciones de hora

El enunciado define una tarifa "por hora" y después entrega tiempos concretos en minutos, pero no especifica expresamente si una fracción de hora debe redondearse hacia arriba, hacia abajo o cobrarse proporcionalmente.

En esta solución se usa cobro proporcional:

```kotlin
private fun calcularHorasCobradas(minutos: Long): Double {
    return minutos / 60.0
}
```

Por ejemplo, `75` minutos equivalen a `1.25` horas.

Esto queda aislado en una sola función. Si en clases o material del curso se establece que se cobra la **hora iniciada**, la función podría cambiar a:

```kotlin
private fun calcularHorasCobradas(minutos: Long): Double {
    return ceil(minutos / 60.0)
}
```

La ventaja es que el resto del programa no cambia.

## 10. Corrutinas y operaciones asíncronas

El requisito R5 pide que las operaciones que esperan al sensor no bloqueen el procesamiento.

Por eso se utiliza `suspend`:

```kotlin
suspend fun registrarEntrada(vehiculo: Vehiculo): String
```

y:

```kotlin
suspend fun registrarSalida(patente: String, minutosEstacionado: Long): String
```

La espera se simula con:

```kotlin
delay(3000)
```

para la entrada y:

```kotlin
delay(6500)
```

para la salida.

### `delay()` vs `Thread.sleep()`

`delay()` suspende la corrutina sin bloquear el hilo del sistema. Por eso es adecuado para modelar una operación asíncrona.

## 11. `async` y `await`

En `Main.kt` se lanzan varias operaciones de forma concurrente:

```kotlin
val tareasEntrada = vehiculos.map { vehiculo ->
    async { estacionamiento.registrarEntrada(vehiculo) }
}
```

Después se recupera el resultado:

```kotlin
tareasEntrada.forEach { tarea ->
    println(tarea.await())
}
```

`async` inicia una corrutina que devuelve un resultado futuro (`Deferred`). `await()` obtiene ese resultado cuando está disponible.

Esto sirve para demostrar que mientras una operación está esperando el sensor, las demás corrutinas pueden avanzar.

## 12. `runBlocking` y `coroutineScope`

El punto de entrada del programa usa:

```kotlin
fun main() = runBlocking {
```

`main()` normalmente no es una función suspendible. `runBlocking` crea un contexto de corrutina desde el cual se pueden llamar funciones `suspend`.

Dentro se usa:

```kotlin
coroutineScope {
    ...
}
```

`coroutineScope` agrupa las corrutinas hijas y espera a que terminen antes de continuar.

## 13. Protección del estado con `Mutex`

Como varias corrutinas pueden acceder a los mismos espacios al mismo tiempo, se utiliza:

```kotlin
private val mutex = Mutex()
```

Y para modificar el estado:

```kotlin
mutex.withLock {
    // modificación segura
}
```

La idea es evitar situaciones como estas:

- Dos vehículos intentando usar el mismo espacio.
- Una entrada y una salida modificando simultáneamente un espacio.
- Dos operaciones actualizando la recaudación al mismo tiempo.

## 14. Flujo de una entrada

La entrada sigue esta secuencia:

```text
Validar patente
      ↓
Buscar primer espacio LIBRE
      ↓
Cambiar a PROCESANDO
      ↓
Esperar 3 segundos
      ↓
Cambiar a OCUPADO
      ↓
Asociar vehículo
```

La parte crítica se realiza antes del `delay()`: el espacio pasa inmediatamente a `PROCESANDO`, por lo que otra operación no puede considerarlo disponible.

## 15. Flujo de una salida

La salida sigue esta secuencia:

```text
Buscar patente en un espacio OCUPADO
      ↓
Cambiar a PROCESANDO
      ↓
Esperar 6,5 segundos
      ↓
Calcular tarifa
      ↓
Crear ticket
      ↓
Agregar historial
      ↓
Actualizar recaudación
      ↓
Liberar espacio
```

Si algo falla durante el cálculo, el espacio vuelve a `OCUPADO` para no perder el vehículo del sistema.

## 16. Manejo de errores

El objetivo es que un dato incorrecto no termine el programa.

Por ejemplo:

```kotlin
try {
    Validators.validarPatente(vehiculo.patente)
    ...
} catch (e: IllegalArgumentException) {
    "Error de datos: ${e.message}"
}
```

Se manejan los casos exigidos por el enunciado:

- patente inválida;
- resultado de tarifa inválido;
- vehículo no encontrado;
- estacionamiento sin capacidad.

El usuario recibe un mensaje de negocio, no una traza técnica.

## 17. Consultas de negocio

La clase `Estacionamiento` implementa consultas como:

```kotlin
suspend fun cantidadEspaciosDisponibles(): Int
```

```kotlin
suspend fun abonadosAtendidos(): List<Ticket>
```

```kotlin
suspend fun patentesQueHanSalido(): List<String>
```

```kotlin
suspend fun ingresoPromedio(): Double
```

```kotlin
suspend fun vehiculoMasTiempo(): Ticket?
```

Para obtener el máximo se usa:

```kotlin
historial.maxByOrNull { it.minutosEstacionado }
```

La lambda `{ it.minutosEstacionado }` indica el valor que se utilizará para comparar.

## 18. Sintaxis Kotlin que conviene aprender para la prueba

### `val` y `var`

```kotlin
val nombre = "UrbanPark"
var total = 0L
```

`val` no permite reasignar la referencia; `var` sí.

### Interpolación de strings

```kotlin
println("Total: $total")
println("Patente: ${vehiculo.patente}")
```

Con `${...}` se puede evaluar una expresión dentro del texto.

### `when`

```kotlin
when (vehiculo) {
    is Particular -> ...
    is Moto -> ...
    is Camioneta -> ...
}
```

Es la alternativa de Kotlin para tomar decisiones entre varias opciones y es especialmente útil con `enum` y `sealed class`.

### `if` como expresión

```kotlin
val descuento = if (cliente == TipoCliente.ABONADO) 0.80 else 1.0
```

En Kotlin `if` puede devolver un valor.

### Operador seguro `?.`

```kotlin
espacio.vehiculo?.patente
```

Si `vehiculo` es `null`, no se produce una excepción y el resultado es `null`.

### Elvis `?:`

```kotlin
espacio.vehiculo?.patente ?: "sin patente"
```

Significa: usa el valor de la izquierda; si es `null`, usa el de la derecha.

### Lambdas

```kotlin
espacios.firstOrNull { it.estado == EstadoEspacio.LIBRE }
```

`{ ... }` es una función anónima que se pasa a `firstOrNull`.

### Colecciones

```kotlin
historial.filter { ... }
historial.map { it.patente }
historial.maxByOrNull { it.minutosEstacionado }
```

`filter` selecciona elementos, `map` transforma elementos y `maxByOrNull` busca el de mayor valor según la expresión indicada.

### Excepciones

```kotlin
throw IllegalArgumentException("Dato inválido")
```

y:

```kotlin
try {
    ...
} catch (e: IllegalArgumentException) {
    ...
}
```

### Funciones `suspend`

```kotlin
suspend fun registrarEntrada(...) { ... }
```

Indica que la función puede suspenderse y reanudarse dentro de una corrutina.

## 19. Build de Gradle

El enunciado exige declarar las corrutinas. En `build.gradle.kts`:

```kotlin
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
}
```

Y se utiliza Kotlin con `kotlin("jvm")`.

## 20. Qué recordar al resolver un ejercicio parecido

La estrategia más útil para una prueba de este tipo es:

```text
1. Leer los sustantivos → convertirlos en clases/enums.
2. Leer las reglas → convertirlas en funciones.
3. Leer los estados → modelarlos explícitamente.
4. Leer los errores → decidir qué excepción se captura.
5. Leer las operaciones que esperan → convertirlas en suspend.
6. Separar responsabilidades en archivos.
7. Probar primero los casos normales y después los casos de error.
```

La idea no es memorizar el proyecto completo. Es reconocer qué parte del enunciado corresponde a qué construcción de Kotlin.
