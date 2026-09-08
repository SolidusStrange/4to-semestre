# Sistema de gestion de eventos en Kotlin

Este proyecto resuelve el caso de estudio de una aplicacion de consola para gestionar entradas de eventos. Modela entradas generales y VIP, calcula ventas, cuenta entradas VIP y valida una entrada de forma asincrona.

## Requisitos que cubre

- Clase base `Entrada` y clases derivadas `EntradaGeneral` y `EntradaVIP`.
- Polimorfismo con `mostrarDetalle()` y `override`.
- Coleccion `List<Entrada>` con calculo de ingreso total y filtrado de entradas VIP.
- `sealed class EstadoValidacion` con los estados `Validando`, `Valida` y `NoValida`.
- Funcion suspendida `validarEntrada`, con espera simulada de dos segundos.
- Ejecucion de la corrutina desde `runBlocking` y manejo exhaustivo con `when`.
- Dos pruebas automatizadas para los resultados valido y no valido.

## Como ejecutarlo

1. Instala un JDK 17 y abre la carpeta del proyecto con IntelliJ IDEA.
2. IntelliJ detectara `build.gradle.kts`; acepta cargar el proyecto de Gradle.
3. Ejecuta la funcion `main` de `src/main/kotlin/Main.kt`.

Tambien puedes usar una terminal que tenga Gradle instalado:

```powershell
gradle run
gradle test
```

La primera ejecucion descarga Kotlin y la libreria de corrutinas desde Maven Central.

### Gradle es necesario

No. Gradle **no es parte del requisito academico** ni es necesario para entender la solucion. Se incluyo porque es la forma mas comun de declarar que el programa necesita la libreria `kotlinx-coroutines`, que proporciona `runBlocking` y `delay`.

Si el docente entrega un proyecto Kotlin ya creado, usa ese proyecto y copia los archivos de `src/main/kotlin`. Solo verifica que incluya esta dependencia de corrutinas, ya sea mediante Gradle o mediante la configuracion que indique el docente. Para un ejercicio de consola, lo importante es el codigo Kotlin; `build.gradle.kts` es solo la configuracion para compilarlo con la libreria correcta.

## Salida esperada

El programa mostrara los detalles de las cuatro entradas, un ingreso total de `$128000`, dos entradas VIP, el mensaje de validacion y el detalle de la entrada VIP con id `202`.

## Analisis paso a paso de la solucion

Esta seccion relaciona cada solicitud del enunciado con la decision tomada en el codigo. La idea es que puedas leer el enunciado y saber que construir antes de empezar a escribir Kotlin.

### Paso 1. Identificar lo comun entre las entradas

**El enunciado pide:** una clase base abierta llamada `Entrada`, con propiedades como `id` y `precio`, y un metodo `mostrarDetalle()`.

**Que hacer:** crear una clase padre con los datos que toda entrada comparte. En Kotlin se agrega `open` tanto a la clase como al metodo porque, a diferencia de Java, las clases y metodos son finales por defecto.

```kotlin
open class Entrada(val id: Int, val precio: Double) {
    open fun mostrarDetalle(): String = "Entrada #$id | Precio: $precio"
}
```

**Por que:** evita repetir `id`, `precio` y la idea de "mostrar una entrada" en cada tipo de entrada. Es el mismo criterio que usar una clase padre en Java.

### Paso 2. Crear los tipos especificos

**El enunciado pide:** `EntradaGeneral` y `EntradaVIP` deben heredar de `Entrada`; la VIP debe tener un dato adicional.

**Que hacer:** extender la clase padre con `: Entrada(...)`. `EntradaGeneral` agrega `sector`; `EntradaVIP` agrega una lista de `beneficiosExtra`.

```kotlin
class EntradaVIP(
    id: Int,
    precio: Double,
    val beneficiosExtra: List<String>
) : Entrada(id, precio)
```

**Por que:** cada tipo conserva lo comun de `Entrada`, pero puede guardar su propia informacion. En Java, la misma idea se escribiria como `class EntradaVIP extends Entrada`.

### Paso 3. Aplicar polimorfismo

**El enunciado pide:** ambas subclases deben sobrescribir `mostrarDetalle()` y mostrar informacion propia.

**Que hacer:** escribir `override fun mostrarDetalle()` en cada subclase. Se usa `super.mostrarDetalle()` para reaprovechar el detalle comun y se concatena el campo especial.

```kotlin
override fun mostrarDetalle(): String =
    "[VIP] ${super.mostrarDetalle()} | Beneficios: ${beneficiosExtra.joinToString()}"
```

**Por que:** una variable de tipo `Entrada` puede apuntar a un objeto general o VIP. Al llamar `mostrarDetalle()`, Kotlin ejecuta la version correcta segun el objeto real. Eso es polimorfismo.

### Paso 4. Construir la coleccion de datos

**El enunciado pide:** en `main`, crear una `List` con varias entradas generales y VIP.

**Que hacer:** declarar la lista como `List<Entrada>`, aunque dentro haya objetos de ambos tipos.

```kotlin
val entradas: List<Entrada> = listOf(
    EntradaGeneral(101, 15_000.0, "Cancha"),
    EntradaVIP(202, 45_000.0, listOf("Zona lounge"))
)
```

**Por que:** declarar el tipo padre permite reunir objetos hijos distintos en una misma lista. Luego se puede recorrer toda la venta sin tratar cada tipo por separado.

### Paso 5. Calcular el ingreso total

**El enunciado pide:** calcular el ingreso generado por todas las entradas.

**Que hacer:** recorrer la lista y sumar la propiedad `precio` de cada elemento. Kotlin ofrece `sumOf` para expresar esa operacion de manera directa.

```kotlin
val ingresoTotal = entradas.sumOf { entrada -> entrada.precio }
```

**Por que:** equivale a un acumulador con `for` o a `stream().mapToDouble(...).sum()` en Java. Como todas las entradas heredan `precio`, no importa si son generales o VIP.

### Paso 6. Filtrar y contar las entradas VIP

**El enunciado pide:** filtrar la lista y contar cuantas entradas VIP se vendieron.

**Que hacer:** usar `filterIsInstance<EntradaVIP>()` y después consultar `size`.

```kotlin
val entradasVIP = entradas.filterIsInstance<EntradaVIP>()
println(entradasVIP.size)
```

**Por que:** este metodo filtra por tipo y devuelve directamente una lista de `EntradaVIP`; no exige conversiones manuales. En Java seria parecido a filtrar con `instanceof` dentro de un `stream`.

### Paso 7. Definir todos los resultados de validacion

**El enunciado pide:** una `sealed class EstadoValidacion` con `Validando`, `Valida` y `NoValida`; los dos ultimos deben transportar datos.

**Que hacer:** usar una clase sellada. `Validando` no necesita datos, por eso es `object`. `Valida` recibe la entrada encontrada y `NoValida` recibe el mensaje de error, por eso son `data class`.

```kotlin
sealed class EstadoValidacion {
    object Validando : EstadoValidacion()
    data class Valida(val entrada: Entrada) : EstadoValidacion()
    data class NoValida(val mensaje: String) : EstadoValidacion()
}
```

**Por que:** en vez de devolver solo `true` o `false`, el resultado explica que ocurrio y entrega la informacion necesaria. Al ser `sealed`, Kotlin sabe que esos son todos los casos posibles.

### Paso 8. Validar de forma asincrona

**El enunciado pide:** una `suspend fun validarEntrada` que reciba un id y la lista, espere dos segundos, busque el id y devuelva el estado apropiado.

**Que hacer:** marcar la funcion con `suspend`, llamar a `delay(2_000)`, buscar con `find` y devolver `Valida` o `NoValida`.

```kotlin
suspend fun validarEntrada(id: Int, entradas: List<Entrada>): EstadoValidacion {
    delay(2_000)
    val entradaEncontrada = entradas.find { it.id == id }

    return if (entradaEncontrada != null) {
        EstadoValidacion.Valida(entradaEncontrada)
    } else {
        EstadoValidacion.NoValida("No existe una entrada con el id $id.")
    }
}
```

**Por que:** `delay` simula una operacion que tardaria, por ejemplo, una consulta a servidor. A diferencia de `Thread.sleep`, no bloquea el hilo mientras la corrutina espera. `find` puede devolver `null`, por lo que el `if` decide entre los dos resultados finales.

### Paso 9. Iniciar la corrutina y manejar todos los resultados

**El enunciado pide:** llamar a `validarEntrada` desde `runBlocking` y usar `when` para tratar todos los estados.

**Que hacer:** usar `runBlocking` en `main`, mostrar primero el estado `Validando`, llamar a la funcion suspendida y enviar el resultado a una funcion con `when`.

```kotlin
fun main() = runBlocking {
    mostrarResultadoValidacion(EstadoValidacion.Validando)
    val resultado = validarEntrada(idABuscar, entradas)
    mostrarResultadoValidacion(resultado)
}
```

**Por que:** `validarEntrada` devuelve el resultado final despues de dos segundos, por lo que el estado visual `Validando` se muestra antes de invocarla. El `when` cubre los tres estados y Kotlin verifica que no falte ninguno.

### Paso 10. Comprobar los dos caminos importantes

**Que hacer:** probar al menos un id que exista y otro que no exista.

**Por que:** asi compruebas que el programa no solo encuentra una entrada valida, sino que tambien informa correctamente cuando el id no esta en la lista. El proyecto incluye esas dos pruebas en `src/test/kotlin/MainTest.kt`.

## Mapa Kotlin para alguien que conoce Java

| Kotlin | Equivalente o similitud en Java | Idea importante |
| --- | --- | --- |
| `val precio: Double` | `final double precio` | `val` no permite reasignar la referencia. |
| `var nombre: String` | `String nombre` | `var` permite reasignar el valor. |
| `class Hija(...) : Padre(...)` | `class Hija extends Padre` | Los dos puntos indican herencia o implementacion de una interfaz. |
| `open class` / `open fun` | Clase o metodo no `final` | En Kotlin todo es `final` por defecto; se debe declarar `open` para permitir herencia o sobreescritura. |
| `override fun` | `@Override void metodo()` | El compilador exige indicar explicitamente que se sobrescribe un metodo. |
| `fun nombre(...)` | Metodo de una clase o metodo `static` | Una funcion fuera de una clase se compila como metodo estatico en una clase generada por Kotlin. |
| `data class` | Clase con `equals`, `hashCode`, `toString` y getters | Kotlin genera esos metodos automaticamente; aqui se usa para los estados con datos. |
| `sealed class` | Jerarquia cerrada de clases | El compilador conoce todos sus subtipos, por eso `when` puede ser exhaustivo. |
| `when` | `switch` mejorado o cadena `if/else` | Puede comprobar tipos y no necesita `break`. |
| `listOf(...)` | `List.of(...)` | Crea una lista de solo lectura. |
| `filterIsInstance<EntradaVIP>()` | `stream().filter(x -> x instanceof EntradaVIP)` | Filtra por tipo y devuelve los elementos ya convertidos a ese tipo. |
| `sumOf { it.precio }` | `stream().mapToDouble(Entrada::getPrecio).sum()` | Lambda compacta para sumar una propiedad. |
| `find { it.id == id }` | `stream().filter(...).findFirst()` | Devuelve el elemento o `null` si no existe. |
| `String?` | Referencia que puede ser `null` | Kotlin distingue explicitamente valores anulables y no anulables. |
| `suspend fun` | Metodo asincrono cooperativo | Solo se llama desde una corrutina o desde otra funcion `suspend`. No equivale exactamente a `async` por si solo. |
| `delay(2_000)` | Aproximadamente `Thread.sleep(2000)` | La diferencia clave es que `delay` no bloquea el hilo de la corrutina. |
| `runBlocking { ... }` | Punto de entrada que espera una tarea asincrona | Es util para una aplicacion de consola o una prueba. En una app Android normalmente se usaria `lifecycleScope`, no `runBlocking`. |

## Lectura guiada del codigo

### 1. Herencia y polimorfismo

`Entrada` declara lo que todas las entradas comparten: `id`, `precio` y `mostrarDetalle()`. `EntradaGeneral` y `EntradaVIP` extienden esa clase. Aunque la lista se declara como `List<Entrada>`, al ejecutar `entrada.mostrarDetalle()` Kotlin usa automaticamente la version correcta segun el objeto real. Es el mismo polimorfismo que tendrias en Java con una referencia de tipo padre.

```kotlin
val entradas: List<Entrada> = listOf(
    EntradaGeneral(101, 15_000.0, "Cancha"),
    EntradaVIP(202, 45_000.0, listOf("Zona lounge"))
)

entradas.forEach { println(it.mostrarDetalle()) }
```

### 2. Colecciones y lambdas

Las expresiones entre llaves son lambdas. `it` significa el unico parametro de la lambda cuando no le das un nombre.

```kotlin
val ingresoTotal = entradas.sumOf { it.precio }
val entradasVIP = entradas.filterIsInstance<EntradaVIP>()
```

En Java, la idea seria parecida a:

```java
double ingresoTotal = entradas.stream()
    .mapToDouble(Entrada::getPrecio)
    .sum();

long cantidadVip = entradas.stream()
    .filter(entrada -> entrada instanceof EntradaVIP)
    .count();
```

### 3. Estados con sealed class y when

`EstadoValidacion` funciona como una respuesta con tipos definidos. `Valida` lleva una `Entrada`, mientras que `NoValida` lleva un mensaje. Como la jerarquia es `sealed`, Kotlin obliga a contemplar todos los casos en el `when`.

```kotlin
when (estado) {
    EstadoValidacion.Validando -> println("Validando...")
    is EstadoValidacion.Valida -> println(estado.entrada)
    is EstadoValidacion.NoValida -> println(estado.mensaje)
}
```

### 4. Corrutinas, paso a paso

1. `validarEntrada` lleva la palabra `suspend`, por lo que puede usar `delay`.
2. `delay(2_000)` simula una consulta remota sin detener el hilo.
3. `runBlocking` crea una corrutina y espera su termino, algo practico para `main`.
4. Una vez que `validarEntrada` devuelve un estado, el `when` muestra el resultado apropiado.

Para este ejercicio, `runBlocking` es correcto porque se trata de una consola. En Android, una espera de red se lanzaria desde una corrutina ligada al ciclo de vida de la pantalla.

## Estructura

```text
SistemaGestionEventos/
├── build.gradle.kts
├── settings.gradle.kts
├── README.md
└── src/
    ├── main/kotlin/
    │   ├── Entrada.kt
    │   ├── EstadoValidacion.kt
    │   ├── Main.kt
    │   └── ValidacionEntrada.kt
    └── test/kotlin/MainTest.kt
```
