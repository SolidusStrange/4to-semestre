---
title: Análisis del problema y decisiones de diseño de TechLab Pro
tags:
  - kotlin
  - analisis-de-problemas
  - poo
  - corrutinas
  - dsy1105
related:
  - Guia_TechLabPro_Linea_por_Linea_Obsidian
---

# Análisis del problema y decisiones de diseño de TechLab Pro

## Objetivo de esta nota

Esta nota explica el razonamiento que debería ocurrir **antes** de escribir el código de TechLab Pro. No se limita a decir qué hace una línea: muestra qué problema resuelve, qué alternativas eran posibles y por qué se eligió una de ellas.

La guía complementaria [[Guia_TechLabPro_Linea_por_Linea_Obsidian]] explica cada instrucción con más detalle. Esta se concentra en el proceso mental de análisis y diseño.

> [!tip]
> En una prueba, no intentes programar el menú primero. Parte identificando: datos, estados, reglas variables, reglas generales, errores y resultados que deben quedar guardados. Esa separación hace que el código se arme casi solo.

---

# 1. Convertir el enunciado en piezas de software

## 1.1 Extraer sustantivos, verbos y reglas

El caso no entrega clases Kotlin. Entrega lenguaje de negocio. El primer trabajo es traducirlo.

| Frase del caso | Pregunta de análisis | Decisión resultante |
|---|---|---|
| “Laptop, Desktop y Estación Gráfica tienen reglas de cobro distintas” | ¿Una sola clase con muchos `if`, o una jerarquía? | Clase base `Equipo` y tres subclases. |
| “Cada puesto tiene un estado” | ¿Cómo impedir combinaciones inválidas? | `sealed class EstadoPuesto`. |
| “Disponible, en uso, en proceso o fuera de servicio” | ¿Qué información acompaña cada estado? | Cada variante guarda solo el dato que necesita. |
| “Código, marca/modelo, usuario y hora de ingreso no cambian” | ¿Qué propiedades deben ser inmutables? | Propiedades `val`. |
| “Registrar entradas y salidas no debe bloquear” | ¿Cómo simular una espera sin `Thread.sleep`? | `suspend fun` y `delay`. |
| “El sistema continúa ante errores” | ¿Cómo informar un problema sin cerrar `main`? | Excepciones de negocio y `try/catch`. |
| “Historial, promedio, máximo y total por tipo” | ¿Qué datos deben sobrevivir después de liberar el puesto? | `MutableList<Ticket>`. |

## 1.2 Requisitos transformados en responsabilidades

| Requisito | Pregunta correcta | Archivo responsable |
|---|---|---|
| R1: tarifas por tipo | “¿Qué comportamiento cambia según el equipo?” | `Modelos.kt` |
| R2: estado de puestos | “¿Qué situaciones son mutuamente excluyentes?” | `Modelos.kt` y `Sistema.kt` |
| R3: IVA, descuentos y validación | “¿Qué reglas son comunes a todos los equipos?” | `Sistema.kt` y `Errores.kt` |
| R4: historial y reporte | “¿Qué resultados se deben conservar?” | `Sistema.kt` |
| R5: operaciones lentas | “¿Qué parte debe esperar sin bloquear?” | `Main.kt` |
| R6: continuidad ante errores | “¿Qué datos y acciones pueden fallar?” | `Errores.kt` y `Main.kt` |
| R7: organización | “¿Qué no debería vivir en `main`?” | Todos los archivos separados |

La decisión clave es no confundir **datos** con **operaciones**:

```text
Equipo y Ticket              -> describen cosas
EstadoPuesto                 -> describe una situación posible
TechLabProSistema            -> modifica y consulta el negocio
Main                         -> conversa con la consola y coordina corrutinas
```

---

# 2. Diseñar los datos antes de los métodos

## 2.1 Alternativa para los estados de puesto

El requisito R2 dice que un puesto puede estar en cuatro estados y cada uno necesita datos distintos. Había varias opciones.

| Alternativa | Ejemplo | Problema o ventaja |
|---|---|---|
| Varios booleanos | `ocupado`, `enProceso`, `fueraServicio` | Permite estados contradictorios, como ocupado y disponible a la vez. Debe evitarse. |
| `enum class` solamente | `enum class Estado { DISPONIBLE, ... }` | Sirve para nombres fijos, pero no puede guardar el equipo ocupado ni el motivo. |
| Clase `Puesto` con campos nullable | `estado`, `equipo: Equipo?`, `motivo: String?` | Funciona, pero permite combinaciones incompletas: `EN_USO` con equipo `null`. |
| `sealed class` | Una subclase por estado | Cada estado contiene solo los datos válidos y el `when` es exhaustivo. Es la elegida. |

### Decisión elegida: `sealed class`

En `Modelos.kt`, líneas 5-10:

```kotlin
5: sealed class EstadoPuesto {
6:     data object Disponible : EstadoPuesto()
7:     data class EnUso(val equipo: Equipo) : EstadoPuesto()
8:     data class EnProceso(val motivo: String, val equipo: Equipo? = null) : EstadoPuesto()
9:     data class FueraDeServicio(val motivo: String) : EstadoPuesto()
10: }
```

### Por qué cada línea existe

| Línea | Decisión | Problema que evita |
|---|---|---|
| 5 | `sealed class` restringe la familia de estados. | Que aparezca un estado no contemplado y un `when` incompleto. |
| 6 | `data object` representa disponibilidad sin datos. | Crear objetos inútiles o usar `null` como estado. |
| 7 | `EnUso` obliga a traer un `Equipo`. | Un puesto ocupado sin saber quién lo ocupa. |
| 8 | `EnProceso` guarda motivo y, si aplica, el equipo. | No poder mostrar el progreso ni verificar qué operación se está esperando. |
| 9 | `FueraDeServicio` obliga a traer una razón. | Deshabilitar un puesto sin explicar por qué. |

> [!example]
> Si el estado hubiera sido un `String`, un `when` podría recibir `"ocupado"`, `"Ocupado"` o `"en uso"`. Con una clase sellada, el compilador conoce los cuatro casos y ayuda a no olvidar uno.

## 2.2 Invariantes que el diseño debe proteger

Un **invariante** es una regla que siempre debe ser verdadera si el sistema está sano. Antes de programar, conviene escribirlos:

1. Un puesto tiene exactamente un estado.
2. Solo `EnUso` tiene un equipo operando.
3. Solo `EnProceso` puede ser confirmado como entrada o finalizado como salida.
4. Un código activo no puede ocupar dos puestos.
5. Un ticket solo existe después de una salida válida.
6. La recaudación es la suma de tickets, no la suma de equipos activos.
7. Una operación fallida no debe dejar un puesto atrapado en `EnProceso`.

Estas reglas explican decisiones de `Sistema.kt`, especialmente las líneas 16-86 y 183-196.

---

# 3. Elegir herencia o condicionales para las tarifas

## 3.1 El problema

Las tres categorías comparten datos:

```text
código + marca/modelo + usuario + fecha de ingreso
```

Pero no comparten exactamente el cálculo:

```text
Laptop          -> $2.000/hora; mensual tiene 20% de descuento
Desktop         -> $1.500/hora; menos de 30 min no cobra
Estación gráfica -> $4.000/hora; GPU añade 30%
```

## 3.2 Alternativas pensadas

| Diseño | Cómo sería | Evaluación |
|---|---|---|
| Un solo `Equipo` y un `when(tipo)` | `if (tipo == "Laptop") ...` | Válido para un programa muy pequeño, pero incumple el objetivo de herencia/polimorfismo y crece mal. |
| Una interfaz `Cobrable` | Cada equipo implementa una tarifa | Buena opción si no compartieran datos. Aquí sí comparten cuatro propiedades, por lo que habría repetición. |
| Clase base abstracta | `abstract class Equipo` | Es una opción idiomática. La pauta menciona explícitamente `open class`, por eso se eligió esa forma. |
| `open class Equipo` con `override` | Subclases cambian solo la tarifa | Cumple la pauta, evita duplicación y es la elegida. |

## 3.3 Clase base: qué debe vivir en ella

En `Modelos.kt`, líneas 22-38:

```kotlin
22: open class Equipo(
23:     val codigo: String,
24:     val marcaModelo: String,
25:     val usuario: TipoUsuario,
26:     val fechaIngreso: LocalDateTime = LocalDateTime.now(),
27: ) {
28:     open fun calcularCostoBase(minutosUso: Int): Double {
29:         throw UnsupportedOperationException("Cada tipo de equipo debe definir su tarifa")
30:     }
32:     open fun categoria(): String = "Equipo"
34:     open fun detalle(): String = "${categoria()} $codigo - $marcaModelo"
36:     fun fechaIngresoFormateada(): String =
37:         fechaIngreso.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
38: }
```

| Decisión | Razón | Alternativa descartada |
|---|---|---|
| `open class Equipo` | Todas las hijas reutilizan sus datos comunes. | Repetir los cuatro datos en cada subclase. |
| `val` en líneas 23-26 | El caso dice que esos datos no cambian después del registro. | `var`, que permitiría alterar identidad o hora de ingreso. |
| `open fun calcularCostoBase` | La variación pertenece a la categoría concreta. | Preguntar el tipo con `if` en cada cálculo. |
| Excepción en línea 29 | La base no tiene tarifa propia; un uso incorrecto no debe devolver un monto inventado. | Retornar 0, que ocultaría un error y chocaría con R3. |
| `categoria()` separada de `detalle()` | El reporte necesita agrupar por tipo, mientras pantalla necesita describir una instancia. | Usar un mismo string con código incluido; eso rompería el agrupamiento. |
| `LocalDateTime` | Es un dato temporal real, no texto sin formato. | Guardar `"10:00"` sin validación ni fecha. |

## 3.4 Dónde se ve el polimorfismo

En `Modelos.kt`, líneas 45-47, 58-60 y 72-74, cada hija cambia solo su parte de la regla:

```kotlin
45: override fun calcularCostoBase(minutosUso: Int): Double {
46:     val costo = 2000.0 * minutosUso / 60.0
47:     return if (usuario == TipoUsuario.MENSUAL) costo * 0.80 else costo
```

```kotlin
58: override fun calcularCostoBase(minutosUso: Int): Double {
59:     if (minutosUso < 30) return 0.0
60:     return 1500.0 * minutosUso / 60.0
```

```kotlin
72: override fun calcularCostoBase(minutosUso: Int): Double {
73:     val costo = 4000.0 * minutosUso / 60.0
74:     return if (gpuDedicada) costo * 1.30 else costo
```

Más adelante, `Sistema.kt` no pregunta qué clase tiene el equipo:

```kotlin
90: fun calcularMontoFinal(equipo: Equipo, minutosUso: Int): Double {
91:     validarMinutosUso(minutosUso)
92:     val costoConIva = equipo.calcularCostoBase(minutosUso) * 1.19
93:     return if (equipo.usuario == TipoUsuario.DOCENTE) costoConIva * 0.50 else costoConIva
94: }
```

La línea 92 llama al método correcto según el objeto real:

```text
Equipo declarado como Equipo
  pero objeto real Laptop           -> usa tarifa Laptop
  pero objeto real Desktop          -> usa regla Desktop
  pero objeto real EstacionGrafica  -> usa tarifa/GPU de estación
```

### Razón de separar costo base y monto final

| Posibilidad | Consecuencia |
|---|---|
| Aplicar IVA y descuento docente dentro de cada subclase | Repetiría la misma regla tres veces y aumentaría el riesgo de orden incorrecto. |
| Calcular toda la tarifa con `when` en el sistema | Duplicaría las reglas de clase y perdería polimorfismo. |
| Cada hija calcula base; el sistema aplica IVA/descuento común | Una sola fuente para cada regla. Esta es la opción elegida. |

---

# 4. Modelar las transiciones, no solo los estados

El enunciado no pide solamente una lista de cuatro nombres. Pide que cada operación actúe según el estado actual. Eso obliga a diseñar **transiciones válidas**.

## 4.1 Diagrama de transiciones

```text
Disponible
  -- iniciar entrada --> EnProceso("registrando entrada")
  -- deshabilitar ----> FueraDeServicio

EnProceso de entrada
  -- sensor confirma --> EnUso

EnUso
  -- iniciar salida --> EnProceso("calculando tarifa")

EnProceso de salida
  -- tarifa válida --> Disponible + Ticket
  -- error --------> EnUso

FueraDeServicio
  -- habilitar ----> Disponible
```

Todo otro movimiento se rechaza con `OperacionNoPermitidaException`.

## 4.2 Reservar antes de esperar

Un error frecuente sería buscar el puesto libre, ejecutar `delay` y recién después marcarlo ocupado. Si dos corrutinas hacen eso, las dos podrían elegir el puesto 1.

El código correcto está en `Sistema.kt`, líneas 16-29:

```kotlin
16: fun iniciarEntrada(equipo: Equipo): Int {
17:     validarCodigoEquipo(equipo.codigo)
18:     if (codigoEstaActivo(equipo.codigo)) {
19:         throw OperacionNoPermitidaException(...)
20:     }
22:     val indice = puestos.indexOfFirst { it is EstadoPuesto.Disponible }
23:     if (indice == -1) {
24:         throw OperacionNoPermitidaException("$nombre no tiene puestos disponibles.")
25:     }
27:     puestos[indice] = EstadoPuesto.EnProceso("registrando entrada con el sensor", equipo)
28:     return indice + 1
29: }
```

| Línea | Pregunta que respondió | Decisión |
|---|---|---|
| 17 | ¿Cuándo validar código? | Antes de reservar recursos. |
| 18-20 | ¿Se puede registrar un código dos veces? | No; se corta antes de ocupar otro puesto. |
| 22 | ¿Qué puesto asignar? | El primer `Disponible`, como pide R5. |
| 23-25 | ¿Qué pasa sin capacidad? | Excepción controlada, no error técnico. |
| 27 | ¿En qué momento bloquear el puesto? | Antes de la espera; este es el punto que protege contra doble reserva. |
| 28 | ¿Qué retorna al flujo asíncrono? | El número que debe confirmar después del `delay`. |

## 4.3 Verificar antes de confirmar

Luego de una espera, el sistema no debería asumir que todo sigue igual. Por eso `confirmarEntrada` revisa el estado:

```kotlin
31: fun confirmarEntrada(numeroPuesto: Int, equipo: Equipo) {
32:     val indice = indicePuesto(numeroPuesto)
33:     when (val estado = puestos[indice]) {
34:         is EstadoPuesto.EnProceso -> {
35:             if (estado.equipo?.codigo != equipo.codigo) {
36:                 throw OperacionNoPermitidaException(...)
37:             }
38:             puestos[indice] = EstadoPuesto.EnUso(equipo)
39:         }
41:         EstadoPuesto.Disponible -> throw OperacionNoPermitidaException(...)
42:         is EstadoPuesto.EnUso -> throw OperacionNoPermitidaException(...)
43:         is EstadoPuesto.FueraDeServicio -> throw OperacionNoPermitidaException(...)
44:     }
45: }
```

La decisión de usar `when` en línea 33 no es decorativa. Es evidencia de que se pensaron los cuatro estados. Un `if (estado is EnProceso)` sin ramas para los demás podría ocultar estados inválidos y no demostrar R2.

---

# 5. Diseñar una salida como transacción simple

## 5.1 Qué debe ocurrir en una salida

Para cumplir el enunciado, una salida tiene varias acciones relacionadas:

1. Encontrar el equipo activo.
2. Cambiar el puesto a `EnProceso`.
3. Esperar 6,5 segundos.
4. Calcular tarifa, IVA y descuento.
5. Validar el monto.
6. Crear ticket y agregarlo al historial.
7. Acumular recaudación.
8. Liberar el puesto.

Si falla el paso 4 o 5, los pasos 6-8 no se pueden ejecutar. Pero tampoco se puede dejar el puesto en `EnProceso` para siempre.

## 5.2 Alternativas de implementación

| Alternativa | Ventaja | Problema |
|---|---|---|
| Una sola función que hace todo | Menos código inicial. | Difícil insertar la espera y recuperar el estado si algo falla. |
| `iniciarSalida` y `finalizarSalida` | Separa antes/después del `delay`. | Requiere un objeto temporal. |
| Cambiar directamente a `Disponible` antes de calcular | Parece simple. | El equipo se perdería si la tarifa es inválida. |
| Usar `SalidaPendiente` y `cancelarSalida` | Conserva contexto y permite rollback manual. | Tiene más piezas, pero protege el invariante. Es la elegida. |

## 5.3 Código que materializa la decisión

En `Modelos.kt`, líneas 92-95:

```kotlin
92: data class SalidaPendiente(
93:     val numeroPuesto: Int,
94:     val equipo: Equipo,
95: )
```

Ese objeto es el “recibo interno” de una salida que aún no terminó. En vez de pasar varios parámetros sueltos, mantiene juntos el puesto y el equipo.

En `Sistema.kt`, líneas 47-58:

```kotlin
47: fun iniciarSalida(codigo: String): SalidaPendiente {
48:     val indice = puestos.indexOfFirst {
49:         it is EstadoPuesto.EnUso && it.equipo.codigo.equals(codigo, ignoreCase = true)
50:     }
51:     if (indice == -1) throw OperacionNoPermitidaException(...)
55:     val equipo = (puestos[indice] as EstadoPuesto.EnUso).equipo
56:     puestos[indice] = EstadoPuesto.EnProceso("calculando tarifa de salida", equipo)
57:     return SalidaPendiente(indice + 1, equipo)
58: }
```

En `Main.kt`, líneas 27-46:

```kotlin
27: var salidaPendiente: SalidaPendiente? = null
29: salidaPendiente = sistema.iniciarSalida(codigo)
32: delay(DEMORA_SALIDA_MS)
34: val monto = sistema.calcularMontoFinal(salida.equipo, minutosUso)
36: validarMontoFinal(monto, cobroSinCosto)
38: val ticket = sistema.finalizarSalida(salida, minutosUso, monto)
42: } catch (error: ReglaNegocioException) {
43:     salidaPendiente?.let { sistema.cancelarSalida(it) }
44:     mostrarError(error)
45:     false
46: }
```

| Fragmento | Para qué se usa | Por qué no se omite |
|---|---|---|
| Línea 27 | Deja una referencia visible para el `catch`. | Si fuera una variable local dentro del `try`, el `catch` no podría restaurar el estado. |
| Línea 29 | Reserva la salida y cambia a `EnProceso`. | Evita otra salida concurrente del mismo equipo. |
| Línea 32 | Simula la comunicación lenta. | R5 exige que no bloquee; por eso usa `delay`. |
| Líneas 34-36 | Calculan y validan antes de modificar historial. | Impiden generar tickets de montos inválidos. |
| Línea 38 | Ejecuta el commit: ticket, recaudación y liberación. | Es el único momento donde la salida se considera terminada. |
| Línea 43 | Ejecuta el rollback. | Mantiene el equipo `EnUso` si la operación falla. |

---

# 6. Elegir una estrategia de errores

## 6.1 Qué errores son esperables

El enunciado menciona explícitamente:

```text
código inválido
tarifa negativa o cero no permitida
equipo no encontrado
sistema sin capacidad
```

Además se agregaron errores coherentes con el modelo: código duplicado, puesto fuera de rango, confirmación en estado incorrecto e intento de deshabilitar un puesto ocupado.

## 6.2 Alternativas

| Estrategia | Ejemplo | Evaluación |
|---|---|---|
| Retornar `null` o `false` desde todo | `fun validar(): Boolean` | Fácil al inicio, pero se pierde el motivo del error y se mezclan muchos `if`. |
| Retornar `Result<T>` | `Result.failure(...)` | Kotlin idiomático; FotoExpress lo usa para validar monto. Es una buena alternativa. |
| Lanzar excepciones propias | `throw CodigoInvalidoException(...)` | Encaja con la pauta que pide `try/catch` y permite mensajes detallados. Es la elegida. |

## 6.3 Jerarquía de errores elegida

En `Errores.kt`, líneas 1-8:

```kotlin
1: open class ReglaNegocioException(mensaje: String) : Exception(mensaje)
3: class CodigoInvalidoException(codigo: String) :
4:     ReglaNegocioException("El código '$codigo' debe tener dos letras, dos dígitos y dos letras...")
6: class DatosInvalidosException(mensaje: String) : ReglaNegocioException(mensaje)
8: class OperacionNoPermitidaException(mensaje: String) : ReglaNegocioException(mensaje)
```

La decisión importante es la línea 1: las tres hijas tienen un antepasado común. Gracias a eso, la capa de consola puede hacer:

```kotlin
19: } catch (error: ReglaNegocioException) {
20:     mostrarError(error)
21:     false
22: }
```

No se capturan `Exception` o `Throwable` de forma general. Eso sería peligroso porque podría ocultar errores de programación no previstos. Se captura exactamente el grupo de errores de negocio que el programa sabe explicar.

## 6.4 Validar en el lugar correcto

| Regla | Línea | Por qué se valida ahí |
|---|---|---|
| Formato de código | `Sistema.kt` 17 | Antes de reservar un puesto. |
| Código duplicado | `Sistema.kt` 18-20 | Antes de permitir una segunda entrada. |
| Capacidad | `Sistema.kt` 22-25 | Antes de cambiar a `EnProceso`. |
| Minutos positivos | `Sistema.kt` 91 | Antes de hacer operaciones aritméticas. |
| Monto final | `Main.kt` 36 | Después de que se aplicaron todas las reglas de la tarifa. |
| Rango de puesto | `Sistema.kt` 191-196 | Antes de acceder a una posición de lista. |

> [!warning]
> Para un sistema comercial se usaría normalmente dinero en centavos (`Long`) o `BigDecimal`, no `Double`, para evitar errores de precisión binaria. Aquí `Double` es una elección pedagógica razonable porque la rúbrica pide evidenciar tipos, operadores y cálculos de forma visible.

---

# 7. Analizar qué guardar para consultas y reportes

## 7.1 Decisión: no consultar puestos activos para el cierre

El reporte necesita equipos que **ya finalizaron**. Cuando un equipo sale, su puesto vuelve a `Disponible`. Por lo tanto, no se puede obtener el cierre mirando solamente la lista `puestos`.

Alternativas:

| Alternativa | Problema |
|---|---|
| Guardar solo total recaudado | No permite promedio, mayor tiempo, códigos, usuarios mensuales ni detalle por ticket. |
| Guardar equipos activos | Desaparecen al liberar el puesto. |
| Guardar `Ticket` por salida | Conserva una fotografía final de código, tipo, usuario, minutos y monto. Es la elegida. |

## 7.2 Código de la estructura de historial

En `Sistema.kt`, líneas 7-10:

```kotlin
7: private val puestos: MutableList<EstadoPuesto> = MutableList(capacidad) { EstadoPuesto.Disponible }
8: private val historial: MutableList<Ticket> = mutableListOf()
9: private var recaudacionTotal = 0.0
10: private var siguienteTicket = 1
```

| Línea | Decisión | Razón |
|---|---|---|
| 7 | Lista de estados actuales. | Responde preguntas operativas: qué puesto está libre o en proceso ahora. |
| 8 | Lista de tickets terminados. | Responde preguntas históricas del turno. |
| 9 | Acumulador total. | Hace directo el reporte; también se podría recalcular con `historial.sumOf`, pero el caso pide registrar recaudación durante el turno. |
| 10 | Contador correlativo. | Genera números de ticket únicos sin depender de un motor de base de datos. |

## 7.3 Consultas: pensar primero en la forma del resultado

| Pregunta del caso | Entrada | Operación elegida | Salida |
|---|---|---|---|
| ¿Cuántos puestos están disponibles? | `puestos` | `count` | `Int` |
| ¿Qué equipos son mensuales? | `historial` | `filter` | `List<Ticket>` |
| ¿Cuánto es el promedio? | `historial` | `sumOf / size` | `Double` |
| ¿Cuáles son los códigos finalizados? | `historial` | `map` | `List<String>` |
| ¿Cuál tuvo más tiempo? | `historial` | `maxByOrNull` | `Ticket?` |
| ¿Qué tipo recaudó más? | `historial` | `groupBy`, `mapValues`, `maxByOrNull` | `String?` |

Esto aparece en `Sistema.kt`, líneas 96-113:

```kotlin
96: fun puestosDisponibles(): Int = puestos.count { it is EstadoPuesto.Disponible }
98: fun equiposMensualesAtendidos(): List<Ticket> =
99:     historial.filter { it.tipoUsuario == TipoUsuario.MENSUAL }
101: fun ingresoPromedio(): Double =
102:     if (historial.isEmpty()) 0.0 else historial.sumOf { it.montoPagado } / historial.size
104: fun codigosFinalizados(): List<String> = historial.map { it.codigoEquipo }
106: fun equipoConMasTiempo(): Ticket? = historial.maxByOrNull { it.minutosUso }
108: fun ingresosPorTipo(): Map<String, Double> =
109:     historial.groupBy { it.tipoEquipo }
110:         .mapValues { (_, tickets) -> tickets.sumOf { it.montoPagado } }
112: fun tipoConMasIngresos(): String? =
113:     ingresosPorTipo().maxByOrNull { it.value }?.key
```

### Por qué no usar un `for` para todo

Se podría resolver cada consulta con un `for`. Pero el enunciado y la rúbrica piden demostrar funciones de orden superior. Además, `filter`, `map` y `sumOf` expresan qué se quiere obtener, no cómo mover un índice manualmente.

En Java, el paralelo más cercano son los streams:

```java
// Java
historial.stream()
    .filter(t -> t.getTipoUsuario() == TipoUsuario.MENSUAL)
    .toList();
```

```kotlin
// Kotlin
historial.filter { it.tipoUsuario == TipoUsuario.MENSUAL }
```

---

# 8. Diseñar la asincronía correctamente

## 8.1 Qué significa “no bloquear” en este caso

No significa que el programa de consola deba tener muchos hilos. Significa que la espera del sensor debe modelarse como una suspensión:

```kotlin
15: delay(DEMORA_ENTRADA_MS)
32: delay(DEMORA_SALIDA_MS)
```

| Opción | Consecuencia |
|---|---|
| `Thread.sleep(3000)` | Bloquea el hilo actual; ninguna otra corrutina puede avanzar ahí. |
| `delay(3000)` dentro de `suspend fun` | Suspende solo esa corrutina y permite trabajo cooperativo. Es la opción elegida. |
| Crear un `Thread` manual | Mucha complejidad para una simulación; no evidencia la herramienta pedida por la pauta. |

## 8.2 Secuencial versus concurrente

FotoExpress llama sus operaciones una por una. Eso ya cumple el mínimo pedagógico:

```kotlin
runBlocking {
    registrarIngreso(...)
    registrarSalida(...)
}
```

TechLab agrega una demostración concurrente en `Main.kt`, líneas 66-74:

```kotlin
66: coroutineScope {
67:     casos.map { (equipo, _) -> async { registrarIngreso(sistema, equipo) } }.awaitAll()
68: }
72: coroutineScope {
73:     casos.map { (equipo, minutos) -> async { registrarSalida(sistema, equipo.codigo, minutos) } }.awaitAll()
74: }
```

| Construcción | Qué aporta | Por qué se necesita junto a las otras |
|---|---|---|
| `coroutineScope` | Define vida útil de un conjunto de corrutinas. | Evita avanzar a reportes mientras aún hay entradas/salidas en curso. |
| `map` | Crea una tarea por caso. | Reutiliza la lista de datos de prueba sin bucle manual. |
| `async` | Inicia una corrutina y produce un `Deferred<Boolean>`. | Permite que varias esperas ocurran en paralelo. |
| `awaitAll` | Espera todos los resultados. | Sin esto, el reporte podría imprimirse antes de terminar las salidas. |

> [!warning]
> La demostración actual funciona porque las corrutinas se coordinan desde `runBlocking` y cambian estado antes de su primer `delay`. Si una versión futura usara `Dispatchers.Default` con hilos reales, habría que agregar sincronización o proteger las colecciones para impedir condiciones de carrera.

## 8.3 Por qué el menú también es `suspend`

En `Main.kt`, línea 97:

```kotlin
97: private suspend fun ejecutarMenu(sistema: TechLabProSistema) {
```

No hay un `delay` directamente dentro del ciclo del menú, pero sí llama a funciones suspendidas en las líneas 101, 105 y 113. Una función solo puede llamar otra `suspend` si ella también es `suspend` o si está dentro de una corrutina. Es una cadena de permisos:

```text
main -> runBlocking -> ejecutarMenu -> registrarIngreso / registrarSalida
```

---

# 9. Separar interfaz de consola y lógica de negocio

## 9.1 Pregunta de diseño

¿Dónde debe vivir un `println`? ¿Dónde se debe leer con `readlnOrNull`? ¿La clase del sistema debería saber si el usuario escogió “1” o “2” en un menú?

La respuesta elegida es:

| Capa | Hace | No hace |
|---|---|---|
| `Main.kt` | Lee texto, presenta opciones, llama funciones y muestra mensajes. | No altera directamente listas ni calcula IVA. |
| `Sistema.kt` | Valida transiciones, calcula, conserva historial y responde consultas. | No usa `readlnOrNull` ni decide el diseño del menú. |
| `Modelos.kt` | Representa estados, equipos y tickets. | No imprime ni busca puestos. |
| `Errores.kt` | Declara reglas de validación y excepciones. | No sabe si el error apareció desde menú o demo. |

## 9.2 Ejemplo concreto: leer no es registrar

En `Main.kt`, líneas 124-144 se obtienen datos de consola y se crea un objeto:

```kotlin
124: private fun leerEquipoDesdeConsola(): Equipo? {
126:     val tipo = leerTexto("Tipo: ")
127:     val codigo = leerTexto("Código: ")
128:     val marcaModelo = leerTexto("Marca y modelo: ")
129:     val usuario = leerTipoUsuario() ?: return null
131:     return when (tipo) {
132:         "1" -> Laptop(codigo, marcaModelo, usuario)
133:         "2" -> Desktop(codigo, marcaModelo, usuario)
134:         "3" -> { ... EstacionGrafica(...) }
139:         else -> { ... null }
143:     }
144: }
```

En `Main.kt`, línea 101 se pide al sistema hacer la operación real:

```kotlin
101: "1" -> leerEquipoDesdeConsola()?.let { registrarIngreso(sistema, it) }
```

Y en `Sistema.kt`, línea 17 se valida antes de cambiar estado:

```kotlin
17: validarCodigoEquipo(equipo.codigo)
```

Esto permite que la demostración automática cree un `Laptop` sin usar consola y aun así reciba las mismas validaciones. Si la validación estuviera solo en `leerEquipoDesdeConsola`, los datos de demo podrían saltársela.

---

# 10. Decisiones menores que también enseñan Kotlin

## 10.1 `enum class` en vez de `String`

En `Modelos.kt`, líneas 12-16:

```kotlin
12: enum class TipoUsuario {
13:     ESTUDIANTE,
14:     MENSUAL,
15:     DOCENTE,
16: }
```

Usar `String` parecería más rápido, pero permitiría `"Docente"`, `"docente"`, `"profesor"` o cualquier otro texto. Un enum transforma una regla de negocio cerrada en un tipo cerrado.

## 10.2 `Ticket` como fotografía final

En `Modelos.kt`, líneas 83-90:

```kotlin
83: data class Ticket(
84:     val numero: Int,
85:     val codigoEquipo: String,
86:     val tipoEquipo: String,
87:     val tipoUsuario: TipoUsuario,
88:     val minutosUso: Int,
89:     val montoPagado: Double,
90: )
```

No se guarda solo una referencia a `Equipo` porque el reporte pide también duración y monto de **esa salida concreta**. El ticket representa el hecho histórico terminado.

## 10.3 `private` para encapsulación

En `Sistema.kt`, líneas 4-10 se usa `private` en la configuración y colecciones:

```kotlin
4: private val nombre: String = "TechLab Pro"
7: private val puestos: MutableList<EstadoPuesto> = ...
8: private val historial: MutableList<Ticket> = mutableListOf()
9: private var recaudacionTotal = 0.0
```

La alternativa sería dejar esas propiedades públicas. Eso permitiría escribir desde `Main.kt` algo como `sistema.puestos[0] = Disponible` y saltarse validaciones, tickets y recaudación. `private` obliga a usar métodos que respetan invariantes.

## 10.4 Nulos manejados explícitamente

| Construcción | Línea | Significado |
|---|---|---|
| `Equipo?` | `Modelos.kt` 8 | Puede no haber equipo asociado a cierto proceso. |
| `Ticket?` | `Sistema.kt` 106 | El máximo puede no existir si no hay historial. |
| `?.let { ... }` | `Main.kt` 43, 101, `Sistema.kt` 159 | Ejecuta un bloque solo si hay valor. |
| `?:` | `Main.kt` 50, 104, 129, 160 | Entrega alternativa o corta el flujo si aparece `null`. |
| `as` | `Sistema.kt` 55 | Conversión usada cuando una validación previa ya garantiza el tipo. |

Venir de Java ayuda a entenderlo así: Kotlin no prohíbe `null`; obliga a declarar y tratar dónde puede existir.

---

# 11. Secuencia recomendada para resolver un caso parecido

1. **Subraya sustantivos:** recursos físicos, objetos atendidos, tipos de usuario y comprobantes.
2. **Escribe estados e invariantes:** qué contiene cada estado y qué transiciones son legales.
3. **Separa reglas variables y comunes:** las variables suelen indicar herencia; las comunes suelen vivir en un servicio/sistema.
4. **Define qué queda en historial:** no intentes construir el reporte con datos que se liberan durante el turno.
5. **Enumera los errores antes de programar el menú:** para cada error, decide si debes mostrar mensaje, no mutar nada o restaurar estado.
6. **Diseña la operación asíncrona en dos fases:** preparar estado -> esperar -> confirmar o revertir.
7. **Escribe consultas sobre la lista:** usa primero `filter`, `map`, `sumOf`, `groupBy` y `maxByOrNull` antes de recurrir a bucles manuales.
8. **Agrega `main` al final:** el menú debe coordinar funcionalidades ya probables de forma aislada.
9. **Prueba recorridos completos:** entrada válida, entrada inválida, salida válida, salida no encontrada, monto especial, sin capacidad y reporte vacío.

# 12. Conclusión

TechLab Pro no parte de “necesito escribir Kotlin”. Parte de estas decisiones:

```text
Estados incompatibles       -> sealed class
Reglas de cobro variables   -> herencia y override
Reglas generales de cálculo -> Sistema central
Resultados históricos       -> Ticket + lista
Esperas no bloqueantes      -> suspend + delay
Errores esperables          -> excepciones de negocio + try/catch
Consola separada            -> Main como orquestador
```

Cuando puedes justificar cada flecha de esa lista, puedes adaptar la solución a otro negocio: estacionamientos, préstamos, reservas, talleres, ventas o cabinas fotográficas. Cambian los nombres y las tarifas; el método de análisis permanece.
