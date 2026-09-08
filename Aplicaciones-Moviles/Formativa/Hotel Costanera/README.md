# Hotel Costanera en Kotlin

Aplicacion de consola para practicar una evaluacion parcial de Desarrollo de Aplicaciones Moviles. El proyecto registra habitaciones, procesa check-in y check-out de forma asincrona, calcula tarifas, controla errores y genera un reporte de cierre de turno.

## Requisitos del enunciado que cubre

| Requisito | Implementacion |
| --- | --- |
| R1 Tipos y cobro | `HabitacionIndividual`, `HabitacionDoble` y `HabitacionSuite` heredan de `Habitacion` y sobrescriben el calculo de subtotal. |
| R2 Estados | `EstadoHabitacion` es una `sealed class` con Libre, Ocupada, EnProceso y FueraDeServicio. |
| R3 Tarifas y validaciones | La clase base aplica el orden subtotal, IVA y descuento por movilidad reducida. Se valida codigo, tarifa y tipo de huesped. |
| R4 Consultas y cierre | `HotelCostanera` calcula disponibilidad, frecuentes, promedio, codigos de salida, estadia maxima y reporte. |
| R5 Corrutinas | `registrarCheckIn` espera 3 segundos y `registrarCheckOut` espera 6,5 segundos usando `delay`. |
| R6 Errores | Las situaciones esperables devuelven `ResultadoOperacion.Error`; la aplicacion informa el mensaje y sigue funcionando. |
| R7 Organizacion | Cada modelo y responsabilidad se encuentra en su propio archivo `.kt`. |

## Ejecutar el proyecto

1. Instala JDK 17 y abre la carpeta con IntelliJ IDEA.
2. Acepta cargar el proyecto desde `build.gradle.kts`.
3. Ejecuta la funcion `main` de `src/main/kotlin/Main.kt`.

Tambien puedes usar una terminal con Gradle instalado:

```powershell
gradle run
gradle test
```

Gradle si es necesario en esta guia porque el enunciado solicita declarar `kotlinx-coroutines-core` en `build.gradle.kts`. Esa dependencia proporciona `runBlocking` y `delay`.

La demostracion principal hace cinco check-in y cinco check-out. Como respeta las esperas solicitadas, tarda aproximadamente 48 segundos en completarse.

## Analisis de la solucion paso a paso

### 1. Cuando el enunciado pide datos y reglas comunes

**Pide:** las habitaciones tienen codigo, estado y reglas compartidas, pero cada tipo cobra distinto.

**Hacer:** crear una clase abstracta `Habitacion`. La clase base contiene `codigo`, `tarifaBase`, `estado` y el algoritmo general de cobro; cada clase hija solo define su regla particular mediante `calcularSubtotal`.

```kotlin
abstract class Habitacion(val codigo: String, val tarifaBase: Double) {
    abstract val tipo: TipoHabitacion
    protected abstract fun calcularSubtotal(noches: Int, tipoHuesped: TipoHuesped): Double
}
```

**Por que:** evita repetir la logica de IVA, validaciones y estado en las tres habitaciones. En Java corresponde a una clase abstracta con metodos abstractos.

### 2. Cuando las reglas cambian segun el tipo de habitacion

**Pide:** tres tarifas y tres reglas distintas: individual con descuento frecuente, doble con recargo por una noche y suite con recargo por vista al mar.

**Hacer:** crear una clase por tipo. Cada una sobrescribe solo `calcularSubtotal`.

```kotlin
override fun calcularSubtotal(noches: Int, tipoHuesped: TipoHuesped): Double {
    val montoPorNoches = tarifaBase * noches
    return if (noches == 1) montoPorNoches + 10_000.0 else montoPorNoches
}
```

**Por que:** el objeto conoce su propia regla. No hace falta un `if` gigante preguntando el tipo de habitacion en muchas partes del programa. Eso es polimorfismo.

### 3. Cuando el enunciado fija un orden para los descuentos

**Pide:** primero calcular por tipo y noches, despues IVA de 19%, y al final descuento de 50% si existe movilidad reducida.

**Hacer:** centralizar el orden en `Habitacion.calcularMontoFinal`.

```kotlin
val subtotal = calcularSubtotal(noches, tipoHuesped)
val montoConIva = subtotal * 1.19
val montoFinal = if (tipoHuesped == TipoHuesped.MOVILIDAD_REDUCIDA) {
    montoConIva * 0.50
} else montoConIva
```

**Por que:** asi todas las habitaciones aplican el IVA y el descuento final de la misma forma. Solo el subtotal varia por tipo. Cambiar el orden daria montos incorrectos.

### 4. Cuando los valores validos son una lista cerrada

**Pide:** un huesped solo puede ser regular, frecuente o con movilidad reducida.

**Hacer:** usar `enum class TipoHuesped` en lugar de texto libre dentro del modelo.

```kotlin
enum class TipoHuesped {
    REGULAR, FRECUENTE, MOVILIDAD_REDUCIDA
}
```

**Por que:** el compilador evita crear por accidente un cuarto tipo. Para datos que vienen como texto, `TipoHuesped.desdeTexto` convierte los tres valores permitidos y devuelve un error claro para los demas.

### 5. Cuando una entidad puede estar en varios estados

**Pide:** una habitacion puede estar libre, ocupada, en proceso o fuera de servicio; las operaciones deben contemplar los cuatro.

**Hacer:** usar una `sealed class` para representar los estados. Los estados que necesitan informacion la contienen: `Ocupada` guarda la estadia, y `EnProceso` y `FueraDeServicio` guardan su motivo.

```kotlin
sealed class EstadoHabitacion {
    object Libre : EstadoHabitacion()
    data class Ocupada(val estadia: RegistroEstadia) : EstadoHabitacion()
    data class EnProceso(val motivo: String) : EstadoHabitacion()
    data class FueraDeServicio(val motivo: String) : EstadoHabitacion()
}
```

**Por que:** no se usan textos ambiguos como `"ocupada"`. Kotlin obliga a cubrir los cuatro casos en un `when`, por ejemplo antes de permitir un check-out.

### 6. Cuando los datos no deben cambiar despues del check-in

**Pide:** nombre, tipo de huesped y fecha/hora de check-in no cambian.

**Hacer:** crear `Huesped` y `RegistroEstadia` como `data class` con propiedades `val`.

```kotlin
data class RegistroEstadia(
    val huesped: Huesped,
    val fechaHoraCheckIn: LocalDateTime
)
```

**Por que:** `val` equivale aproximadamente a una referencia `final` en Java. El hotel puede cambiar el estado de una habitacion, pero no modifica los datos del registro ya creado.

### 7. Cuando se pide una operacion asincrona

**Pide:** el check-in debe dejar una habitacion en proceso, esperar 3 segundos y confirmarla ocupada; el check-out debe esperar 6,5 segundos.

**Hacer:** declarar las operaciones como `suspend fun` y usar `delay`.

```kotlin
habitacion.cambiarEstado(EstadoHabitacion.EnProceso("Registrando check-in"))
delay(3_000)
habitacion.cambiarEstado(EstadoHabitacion.Ocupada(estadia))
```

**Por que:** `delay` suspende la corrutina sin bloquear el hilo. Es una simulacion de la espera por el sistema central de reservas. En una consola, `runBlocking` permite iniciar la corrutina desde `main`.

### 8. Cuando un check-in debe usar la primera habitacion libre

**Pide:** buscar la primera habitacion disponible y no asignar una ocupada o fuera de servicio.

**Hacer:** filtrar por el estado exacto `Libre` y obtener el primer resultado.

```kotlin
val habitacion = habitaciones.firstOrNull { it.estado is EstadoHabitacion.Libre }
    ?: return ResultadoOperacion.Error("Hotel sin capacidad")
```

**Por que:** una habitacion `EnProceso` o `FueraDeServicio` no pasa el filtro. Si no hay ninguna libre, se devuelve un error controlado y el programa continua.

### 9. Cuando un check-out debe emitir un ticket y actualizar el turno

**Pide:** localizar por codigo, dejar en proceso, calcular, emitir ticket, guardar historial, sumar recaudacion y liberar la habitacion.

**Hacer:** guardar el estado ocupado antes de iniciar, cambiar temporalmente a `EnProceso`, calcular el monto, crear el `Ticket`, actualizar las colecciones y finalmente volver a `Libre`.

**Por que:** el estado ocupado inicial contiene el huesped necesario para calcular la tarifa. Si la tarifa es invalida, el bloque `catch` restablece ese estado: no se pierde el huesped ni queda la habitacion bloqueada.

### 10. Cuando se piden consultas de negocio

**Pide:** disponibilidad, huespedes frecuentes, promedio, codigos de salida, estadia mas larga y tipo de mayor recaudacion.

**Hacer:** usar las colecciones del catalogo y del historial de tickets.

```kotlin
fun cantidadHabitacionesDisponibles() = habitaciones.count {
    it.estado is EstadoHabitacion.Libre
}

fun huespedConMasNoches(): Ticket? = historialTickets.maxByOrNull { it.noches }
```

**Por que:** el historial representa lo que ya termino durante el turno; las habitaciones representan la situacion actual. Separar ambas colecciones evita mezclar una estadia presente con una salida ya cobrada.

### 11. Cuando el sistema debe continuar ante errores

**Pide:** no terminar ante codigo invalido, tarifa no valida, huesped no encontrado o hotel lleno.

**Hacer:** devolver `ResultadoOperacion.Exito` o `ResultadoOperacion.Error` para errores esperables. Las tarifas invalidas usan una excepcion propia, que se captura dentro de `registrarCheckOut`.

```kotlin
sealed class ResultadoOperacion<out T> {
    data class Exito<T>(val valor: T, val mensaje: String) : ResultadoOperacion<T>()
    data class Error(val mensaje: String) : ResultadoOperacion<Nothing>()
}
```

**Por que:** `main` usa `when` para imprimir una respuesta clara. No se muestran trazas tecnicas y la siguiente operacion puede continuar.

## Comparacion rapida con Java

| Kotlin | Idea similar en Java |
| --- | --- |
| `abstract class Habitacion` | `abstract class Habitacion` |
| `class HabitacionSuite(...) : Habitacion(...)` | `class HabitacionSuite extends Habitacion` |
| `override fun` | `@Override` en un metodo |
| `val` | Referencia `final` |
| `data class` | Clase con `equals`, `hashCode`, `toString` y getters generados |
| `sealed class` | Jerarquia cerrada de tipos; parecida a una combinacion de clases finales controladas |
| `when` | `switch` mejorado o `if/else` por tipo |
| `firstOrNull`, `count`, `map`, `filter` | Operaciones de `Stream` |
| `suspend fun` y `delay` | Trabajo asincrono cooperativo; no es igual a `Thread.sleep` porque no bloquea el hilo |
| `runBlocking` | Punto de entrada que espera una corrutina; util en una consola, no para la UI de Android |

## Calculos esperados con los datos de prueba

| Codigo | Regla aplicada | Monto final |
| --- | --- | --- |
| A05 | Individual, 3 noches, frecuente: 84.000 - 15%, luego IVA | $84966 |
| B12 | Doble, 2 noches, luego IVA | $99960 |
| B03 | Doble, 1 noche: incluye recargo de $10000, luego IVA | $61880 |
| C01 | Suite vista al mar, 4 noches, IVA y descuento movilidad reducida | $232050 |
| C02 | Suite sin vista al mar, 2 noches, luego IVA | $178500 |

## Estructura

```text
HotelCostanera/
├── build.gradle.kts
├── settings.gradle.kts
├── README.md
└── src/
    ├── main/kotlin/
    │   ├── EstadoHabitacion.kt
    │   ├── Habitacion.kt
    │   ├── HabitacionDoble.kt
    │   ├── HabitacionIndividual.kt
    │   ├── HabitacionSuite.kt
    │   ├── HotelCostanera.kt
    │   ├── Huesped.kt
    │   ├── Main.kt
    │   ├── RegistroEstadia.kt
    │   ├── ResultadoOperacion.kt
    │   ├── Ticket.kt
    │   └── TiposDominio.kt
    └── test/kotlin/HabitacionTest.kt
```
