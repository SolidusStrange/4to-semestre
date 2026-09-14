---
title: Guía línea por línea de TechLab Pro
tags:
  - kotlin
  - dsy1105
  - poo
  - corrutinas
  - guia-de-codigo
source_project: TechLabPro
---

# Guía línea por línea de TechLab Pro

## Propósito de esta nota

Esta guía explica el proyecto [[TechLabPro]] desde el archivo de Gradle hasta `main`. Para cada línea declarativa o ejecutable indica:

1. **Qué hace técnicamente.**
2. **Qué requisito del enunciado evidencia.**
3. **Por qué se eligió esa construcción de Kotlin.**

Las líneas vacías se omiten porque solo separan visualmente bloques. Las llaves de apertura y cierre se explican junto a la declaración que delimitan, ya que no agregan lógica por sí solas.

## Clave de requisitos del caso TechLab Pro

| Clave | Qué pide el caso |
|---|---|
| R1 | Jerarquía `Laptop`, `Desktop` y `EstacionGrafica`, con reglas de tarifa distintas. |
| R2 | Estados `Disponible`, `EnUso`, `EnProceso` y `FueraDeServicio` para cada puesto. |
| R3 | IVA, descuento docente, validaciones y recaudación del turno. |
| R4 | Historial, consultas de negocio y reporte de cierre. |
| R5 | Entrada y salida asíncronas con una espera simulada. |
| R6 | Errores controlados sin detener el programa. |
| R7 | Responsabilidades separadas en archivos Kotlin. |

## Mapa rápido de archivos

```text
build.gradle.kts  -> prepara Kotlin, Gradle y corrutinas
settings.gradle.kts -> nombra el proyecto
Modelos.kt        -> datos, estados y polimorfismo
Errores.kt        -> validaciones y excepciones
Sistema.kt        -> reglas de negocio, listas, consultas y reportes
Main.kt           -> consola, corrutinas y coordinación del flujo
```

---

# 1. `build.gradle.kts`

Ruta: `TechLabPro/build.gradle.kts`

Este archivo no contiene la lógica del laboratorio. Le dice a Gradle cómo obtener Kotlin y la biblioteca de corrutinas, y cuál es el punto de inicio de la aplicación.

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 1 | Abre el bloque `buildscript`. | R7: proyecto Gradle correctamente configurado. | Se usa para declarar una dependencia necesaria durante la configuración de Gradle, no durante la ejecución del programa. |
| 2 | Abre la lista de repositorios del propio `buildscript`. | R7. | Gradle necesita saber desde dónde descargar el plugin Kotlin. |
| 3 | Declara Maven Central como repositorio. | R7. | Maven Central es el repositorio estándar para plugins y bibliotecas JVM. |
| 4 | Cierra los repositorios del `buildscript`. | Estructura de Gradle. | Marca que ya no se agregarán fuentes para el plugin. |
| 5 | Abre las dependencias que Gradle necesita para configurar el proyecto. | R7. | Estas dependencias no se importan desde el código Kotlin; las usa Gradle. |
| 6 | Descarga o localiza el plugin Kotlin JVM versión `2.2.10`. | El documento exige Kotlin 1.9 o superior. | El plugin convierte los archivos `.kt` en bytecode JVM. La versión supera 1.9. |
| 7 | Cierra las dependencias del `buildscript`. | Estructura de Gradle. | Termina ese bloque de configuración. |
| 8 | Cierra `buildscript`. | Estructura de Gradle. | A partir de aquí se configura el proyecto propiamente tal. |
| 10 | Abre el bloque de plugins aplicados mediante la sintaxis de Gradle. | R7. | Es distinto del bloque `buildscript`: aquí se activan capacidades del proyecto. |
| 11 | Activa el plugin `application`. | Aplicación de consola ejecutable. | Aporta las tareas `run`, `distZip` y la configuración del `main`. |
| 12 | Cierra el bloque `plugins`. | Estructura de Gradle. | Finaliza la lista de plugins declarados con DSL. |
| 14 | Aplica el plugin Kotlin para JVM. | Kotlin y R7. | `org.jetbrains.kotlin.jvm` agrega compilación Kotlin y la configuración `implementation`. Se aplicó de esta forma para que Gradle pueda resolver el plugin desde el bloque anterior. |
| 16 | Define el grupo del proyecto. | Organización técnica. | Equivale a un namespace de publicación; no cambia la lógica del programa. |
| 17 | Declara la versión de la aplicación. | Organización técnica. | Permite identificar una versión distribuible; por ahora es la primera. |
| 19 | Abre los repositorios de dependencias de la aplicación. | R7. | No es el repositorio del plugin: aquí se buscan bibliotecas usadas por el código. |
| 20 | Usa Maven Central para las dependencias de ejecución. | R7 y R5. | Allí vive `kotlinx-coroutines-core`. |
| 21 | Cierra la lista de repositorios. | Estructura de Gradle. | Evita que configuraciones posteriores se interpreten como repositorios. |
| 23 | Abre el bloque de dependencias de la aplicación. | R5. | Aquí se declara lo que podrá importar `Main.kt`. |
| 24 | Agrega `kotlinx-coroutines-core:1.9.0` al classpath de implementación. | R5 y requisito explícito de declarar corrutinas. | Aporta `runBlocking`, `delay`, `async`, `awaitAll` y `coroutineScope`. Sin ella los `import kotlinx.coroutines...` no compilarían. |
| 25 | Cierra dependencias. | Estructura de Gradle. | Finaliza los artefactos requeridos por el código. |
| 27 | Establece `MainKt` como clase principal. | Aplicación de consola. | Kotlin compila las funciones de nivel superior de `Main.kt` en una clase JVM llamada `MainKt`. Por eso no se escribe una clase Java llamada `Main`. |

> [!note]
> FotoExpress usa la forma más habitual `kotlin("jvm") version "..."` dentro de `plugins` y fija JDK 17. Ambas configuraciones permiten una aplicación Kotlin con corrutinas. Esta implementación usa `buildscript` para aplicar el plugin Kotlin; la lógica del caso no depende de esa diferencia.

---

# 2. `settings.gradle.kts`

Ruta: `TechLabPro/settings.gradle.kts`

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 1 | Asigna `TechLabPro` como nombre raíz del proyecto. | R7 y entrega ordenada. | Gradle usa este nombre en tareas, artefactos y al importarlo en IntelliJ. No es el nombre de ninguna clase Kotlin. |

---

# 3. `Modelos.kt`

Ruta: `TechLabPro/src/main/kotlin/Modelos.kt`

Este archivo contiene los tipos que describen el dominio. No imprime menús ni busca datos en listas: define qué significa un equipo, un estado y un ticket. Esa separación es R7.

## 3.1 Importaciones y estados del puesto

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 1 | Importa `LocalDateTime`. | R1: fecha y hora exacta de ingreso. | Es un tipo Java estándar para representar fecha y hora sin zona horaria. Es más seguro que guardar la fecha como texto libre. |
| 2 | Importa `DateTimeFormatter`. | R1 y salida legible. | Permite convertir el `LocalDateTime` a un formato entendible por una persona. |
| 4 | Comentario de documentación sobre los estados. | R7: comentarios en lógica clave. | No se ejecuta. Explica por qué existe la clase siguiente. |
| 5 | Declara `EstadoPuesto` como `sealed class`. | R2 y pauta de clases selladas. | Una jerarquía sellada restringe las alternativas a las declaradas en este archivo. Así `when` puede obligar a cubrir todos los estados. |
| 6 | Crea el único objeto `Disponible`. | R2. | `data object` es un singleton: no necesita datos adicionales y no hay razón para crear muchas instancias idénticas. |
| 7 | Declara el estado `EnUso` con el equipo que ocupa el puesto. | R2: el sistema debe saber qué equipo está en cada puesto ocupado. | `data class` guarda datos, crea `toString`, `equals` y `copy` automáticamente. |
| 8 | Declara `EnProceso` con un motivo y un `Equipo?` opcional. | R2 y R5. | El motivo se muestra al usuario; el equipo opcional permite comprobar qué operación sigue pendiente durante una corrutina. `?` significa que puede ser `null`. |
| 9 | Declara `FueraDeServicio` con el motivo de la inhabilitación. | R2. | El caso exige conservar el motivo, por lo que no basta un booleano como `activo`. |
| 10 | Cierra la jerarquía de estados. | R2. | Después de esta llave, no se puede agregar otro estado fuera de la clase sellada. |

## 3.2 Tipo de usuario

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 12 | Declara `TipoUsuario` como `enum class`. | R1 y R3: solo estudiante, mensual o docente. | Un enum evita errores de escritura como `"mensualidad"` y limita los valores posibles. |
| 13 | Declara el valor `ESTUDIANTE`. | R1. | Es el tipo sin descuento especial en las reglas del caso. |
| 14 | Declara `MENSUAL`. | R1. | Se usa en la regla particular de `Laptop`. |
| 15 | Declara `DOCENTE`. | R3. | Se usa para aplicar el descuento general luego del IVA. |
| 16 | Cierra el enum. | R1 y R3. | El compilador conoce exactamente sus tres alternativas. |

## 3.3 Clase base `Equipo`

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 18-21 | Comentario que documenta el propósito de la clase base. | R7. | Explica que la tarifa variable pertenece a las hijas y que IVA/descuentos generales no. |
| 22 | Declara `Equipo` como clase `open` y abre su constructor primario. | R1 y pauta de herencia. | En Kotlin las clases son `final` por defecto; `open` es el equivalente necesario para usar `extends` en Java. |
| 23 | Declara la propiedad inmutable `codigo`. | R1: identificador único y no modificable. | `val` crea campo y getter de solo lectura en una sola línea. |
| 24 | Declara `marcaModelo`. | R1. | También es un dato común e inmutable de todos los equipos. |
| 25 | Declara `usuario`. | R1 y R3. | El sistema necesita este dato al calcular descuentos y consultas. |
| 26 | Declara la fecha de ingreso con valor por defecto `LocalDateTime.now()`. | R1. | Se registra al crear el objeto sin que el menú deba pedirla. Sigue siendo `val`, por lo que no puede cambiar. |
| 27 | Cierra los parámetros y abre el cuerpo de `Equipo`. | R1. | Las subclases heredarán estas propiedades. |
| 28 | Declara el método abierto `calcularCostoBase`. | R1 y polimorfismo. | `open fun` permite que cada subtipo cambie la regla de tarifa. |
| 29 | Lanza un error si alguien intenta calcular una tarifa con el tipo base. | R1. | `Equipo` representa datos comunes, pero no tiene una tarifa real. Fallar explícitamente evita devolver un monto falso. |
| 30 | Cierra el método. | Estructura de la clase. | Finaliza el comportamiento base que las hijas reemplazan. |
| 32 | Declara `categoria()` con valor por defecto `"Equipo"`. | R4: agrupar y mostrar el tipo. | También queda abierto para que el reporte use el nombre específico de cada hija sin `if`. |
| 34 | Declara `detalle()` con interpolación de strings. | R1 y presentación en consola. | `${categoria()}` y `$codigo` sustituyen concatenaciones largas como en Java. Puede sobrescribirse si un tipo necesita más detalle. |
| 36 | Inicia la función que convierte la fecha a texto. | R1 y legibilidad. | Separar formato de dato mantiene la fecha como `LocalDateTime` internamente. |
| 37 | Aplica el patrón `dd-MM-yyyy HH:mm:ss`. | R1. | El formato muestra día, mes, año, hora, minuto y segundo, útil para una entrada exacta. |
| 38 | Cierra `Equipo`. | R1. | Termina la clase que usan todas las especializaciones. |

## 3.4 `Laptop`

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 40 | Inicia la subclase `Laptop`. | R1: uno de los tres tipos obligatorios. | No se marca `open` porque no se planea heredar nuevamente de ella. |
| 41 | Recibe el código. | R1. | No lleva `val` porque la propiedad ya existe en la superclase. |
| 42 | Recibe marca y modelo. | R1. | Se reenviará a `Equipo`. |
| 43 | Recibe el tipo de usuario. | R1 y R3. | La regla mensual necesita consultar este valor. |
| 44 | Llama al constructor de `Equipo` usando `:`. | Herencia. | Es el equivalente a `extends Equipo(...)` de Java; inicializa los datos compartidos. |
| 45 | Inicia la sobrescritura de la tarifa. | R1 y polimorfismo. | `override` demuestra que cambia un método `open` de la base. |
| 46 | Calcula tarifa de $2.000 por hora de forma proporcional. | R1. | Se usa `2000.0` y `60.0` para forzar aritmética `Double`, necesaria para fracciones de hora. |
| 47 | Aplica 20% de descuento si el usuario es mensual. | R1. | `if` es una expresión Kotlin: devuelve uno de dos valores sin declarar una variable adicional. |
| 48 | Cierra el cálculo. | R1. | El método deja el monto base para que el sistema agregue IVA y posible descuento docente. |
| 50 | Devuelve el nombre de categoría. | R4. | El reporte agrupa todos los tickets Laptop bajo un mismo texto. |
| 51 | Cierra `Laptop`. | R1. | Finaliza esta regla específica. |

## 3.5 `Desktop`

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 53-57 | Declara `Desktop`, recibe datos comunes y los envía a `Equipo`. | R1 y herencia. | Repite el patrón de `Laptop` porque cada subtipo sigue compartiendo identidad, modelo, usuario y hora. |
| 58 | Abre la implementación propia de tarifa. | R1 y polimorfismo. | Esta es la variación de comportamiento que evita un gran `when` de tarifas en el sistema. |
| 59 | Retorna `0.0` cuando el uso es menor de 30 minutos. | R1: excepción de cobro Desktop. | El retorno temprano expresa de forma directa la regla especial y no ejecuta la tarifa normal. |
| 60 | Calcula $1.500 por hora para los demás casos. | R1. | Mantiene el cálculo proporcional mediante `Double`. |
| 61 | Cierra el cálculo. | R1. | Termina la regla Desktop. |
| 63 | Devuelve la categoría `Desktop`. | R4. | Permite consultar la recaudación de este tipo. |
| 64 | Cierra la clase. | R1. | Finaliza el segundo subtipo. |

## 3.6 `EstacionGrafica`

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 66 | Inicia la subclase `EstacionGrafica`. | R1. | Es el tercer tipo obligatorio del caso. |
| 67-69 | Recibe código, modelo y usuario. | R1. | Son los campos heredados comunes a cualquier equipo. |
| 70 | Declara el dato propio `gpuDedicada`. | R1: categoría adicional registrada al ingreso. | Es `val` porque este subtipo debe conservar si tiene GPU dedicada y ese dato no cambia. |
| 71 | Envía los campos comunes al constructor de `Equipo`. | Herencia. | `gpuDedicada` no se envía porque solo pertenece a la estación gráfica. |
| 72 | Abre la regla de costo propia. | R1 y polimorfismo. | Sobrescribe el comportamiento base como las demás hijas. |
| 73 | Calcula $4.000 por hora. | R1. | Se conserva como `Double` para admitir minutos no múltiplos de 60. |
| 74 | Agrega 30% si tiene GPU dedicada. | R1. | `if` modela exactamente el recargo condicional solicitado. |
| 75 | Cierra el cálculo. | R1. | Devuelve el costo base antes de IVA y descuento docente. |
| 77 | Devuelve la categoría legible. | R4. | Este texto aparece en tickets y agrupaciones de ingreso. |
| 79 | Inicia la sobrescritura del detalle para pantalla. | R1. | Esta clase necesita mostrar un dato que los otros tipos no tienen. |
| 80 | Construye el detalle e imprime explícitamente si hay GPU dedicada. | R1. | La interpolación anidada usa `if` dentro de `${...}` para cumplir el requisito sin duplicar la función. |
| 81 | Cierra la clase. | R1. | Termina la tercera especialización. |

## 3.7 Datos de salida

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 83 | Declara `Ticket` como `data class`. | R4: comprobante al liberar un equipo. | Un ticket transporta datos; `data class` genera `toString`, `equals`, `hashCode` y `copy` automáticamente. |
| 84 | Guarda el número correlativo. | R4. | Permite identificar cada salida en el reporte. |
| 85 | Guarda el código del equipo liberado. | R4. | Es una copia del código para conservar el historial aunque el puesto cambie. |
| 86 | Guarda el tipo de equipo. | R4. | Se necesita para agrupar la recaudación por tipo. |
| 87 | Guarda el tipo de usuario. | R4. | Permite filtrar equipos de usuario mensual en el historial. |
| 88 | Guarda los minutos de uso. | R4. | Sirve para mostrar tickets y encontrar el equipo de mayor tiempo. |
| 89 | Guarda el monto pagado como `Double`. | R3 y R4. | Los montos pueden tener decimales antes de formatearse para la consola. |
| 90 | Cierra el constructor de `Ticket`. | R4. | Define por completo el comprobante inmutable. |
| 92 | Declara `SalidaPendiente` como `data class`. | R5. | Es un objeto temporal que conserva la operación mientras la corrutina espera. |
| 93 | Guarda el número de puesto. | R5. | Al terminar la espera se debe actualizar el mismo puesto. |
| 94 | Guarda el equipo asociado. | R5. | Se requiere para calcular la tarifa y restaurar el estado si algo falla. |
| 95 | Cierra `SalidaPendiente`. | R5. | Termina el modelo temporal de salida. |

---

# 4. `Errores.kt`

Ruta: `TechLabPro/src/main/kotlin/Errores.kt`

Este archivo concentra errores esperables del dominio. Así `Main.kt` puede mostrar un mensaje claro y continuar, tal como exige R6.

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 1 | Declara `ReglaNegocioException` como excepción base abierta. | R6 y pauta de `try/catch`. | Agrupa todos los errores de negocio bajo un tipo común para capturarlos con un solo `catch`. Es `open` porque tendrá hijas. |
| 3 | Inicia `CodigoInvalidoException` y recibe el código problemático. | R3 y R6. | Una excepción específica permite dar un mensaje concreto en vez de un error genérico. |
| 4 | Pasa a la excepción base un mensaje que incluye el código y formato esperado. | R3 y R6. | La interpolación `$codigo` ayuda al usuario a corregir exactamente su dato. |
| 6 | Declara el error para datos válidos en tipo pero inválidos en valor. | R3 y R6. | Cubre minutos no positivos, montos negativos y números de puesto fuera de rango. |
| 8 | Declara el error para una acción incompatible con el estado. | R2, R5 y R6. | Distingue entre un dato mal escrito y una operación que no se puede realizar ahora. |
| 10 | Inicia la función de validación del código. | R3. | Centraliza una regla para no repetir el mismo `Regex` en menú y sistema. |
| 11 | Crea una expresión regular de dos letras, dos dígitos y dos letras. | R3. | `^` y `$` exigen que todo el texto cumpla el formato, no solo una parte. |
| 12 | Lanza la excepción cuando el patrón no coincide. | R3 y R6. | `throw` abandona la operación actual; el `catch` superior evita que finalice el programa completo. |
| 13 | Cierra `validarCodigoEquipo`. | R3. | La función no retorna datos: termina bien o lanza una excepción. |
| 15 | Inicia la validación de duración. | R3 y R6. | Impide que un valor negativo produzca una tarifa incorrecta. |
| 16 | Comprueba `<= 0`. | R3. | Cero minutos tampoco representa una sesión o uso válido. |
| 17 | Lanza un error legible si falla la regla. | R6. | La consola puede imprimirlo sin una traza técnica. |
| 18 | Cierra el `if`. | Estructura de validación. | Finaliza el caso inválido. |
| 19 | Cierra `validarMinutosUso`. | R3. | Deja la validación reutilizable. |
| 21-23 | Comentario sobre la excepción Desktop de monto cero. | R1, R3 y R7. | Documenta una aparente contradicción: normalmente monto cero es inválido, salvo la regla explícita de Desktop. |
| 24 | Inicia la validación de monto final. | R3 y R6. | Se ejecuta después de aplicar la lógica de tarifa, IVA y descuentos. |
| 25 | Detecta monto negativo o cero no autorizado. | R3. | La segunda condición mantiene válido el Desktop bajo 30 minutos sin permitir otros ceros accidentales. |
| 26 | Lanza una excepción con el monto calculado. | R6. | Facilita diagnosticar qué resultado originó el problema. |
| 27 | Cierra el `if`. | Estructura de validación. | Finaliza la condición de error. |
| 28 | Cierra `validarMontoFinal`. | R3. | El llamador continúa solo si el monto es aceptable. |

---

# 5. `Sistema.kt`

Ruta: `TechLabPro/src/main/kotlin/Sistema.kt`

Esta es la capa central. Mantiene los puestos y el historial privados, aplica las reglas generales y responde las consultas. El menú solo le pide acciones; no altera listas directamente.

## 5.1 Datos internos e inicialización

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 1 | Importa `Locale`. | R4: montos legibles. | Se usa al formatear la moneda con separadores propios de Chile. |
| 3 | Declara la clase `TechLabProSistema`. | R7. | Centraliza la lógica de negocio en vez de mezclarla en `main`. |
| 4 | Declara `nombre` privado con valor por defecto. | R3 y R4. | El nombre se usa en mensajes y reportes; `private` evita que otro archivo lo modifique accidentalmente. |
| 5 | Declara `capacidad` privada y por defecto en 10. | R3: capacidad de 10 puestos. | Es un parámetro para permitir pruebas con otra capacidad, pero el valor normal cumple el enunciado. |
| 6 | Cierra el constructor y abre la clase. | R7. | Los miembros siguientes usan `nombre` y `capacidad`. |
| 7 | Crea una lista mutable de `capacidad` estados disponibles. | R2. | `MutableList(capacidad) { ... }` inicializa los diez puestos con el mismo estado singleton `Disponible`. |
| 8 | Crea el historial mutable de tickets. | R4. | Solo se almacenan equipos finalizados, que son los datos del turno para consultas y cierre. |
| 9 | Inicializa la recaudación total en `Double`. | R3. | Los cálculos monetarios usan decimales; es mutable porque cambia tras cada salida. |
| 10 | Inicializa el correlativo del ticket. | R4. | Cada salida debe recibir un número único y consecutivo. |
| 12 | Abre el bloque `init`. | R7 y robustez. | `init` se ejecuta al construir la clase, antes de usar el sistema. |
| 13 | Exige una capacidad positiva. | R6. | `require` protege una configuración imposible, como crear un sistema con cero puestos. |
| 14 | Cierra `init`. | Estructura de clase. | La validación se ejecutó una vez al crear el objeto. |

## 5.2 Entrada de un equipo

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 16 | Declara `iniciarEntrada`, que devuelve el número de puesto asignado. | R5. | La corrutina necesita saber qué puesto debe confirmar después de `delay`. |
| 17 | Valida el formato del código antes de tocar el estado. | R3 y R6. | Validar temprano evita ocupar un puesto con un equipo inválido. |
| 18 | Pregunta si el código ya está activo. | R2 y R6. | Evita registrar por segunda vez el mismo equipo en simultáneo. |
| 19 | Lanza una operación no permitida al duplicar código. | R6. | Comunica una regla de negocio, no un error técnico. |
| 20 | Cierra el `if` de código duplicado. | Flujo de entrada. | Si no está activo, se puede seguir buscando un puesto. |
| 22 | Busca el primer `Disponible` y guarda su índice de lista. | R5. | `indexOfFirst` implementa la regla «primer puesto libre». Devuelve `-1` si no encuentra uno. |
| 23 | Comprueba el caso `-1`. | R6: sistema sin capacidad. | Es la forma explícita de detectar que no hay puesto libre. |
| 24 | Lanza un mensaje claro de capacidad agotada. | R6. | La excepción será capturada por `registrarIngreso`; el programa sigue funcionando. |
| 25 | Cierra la validación de capacidad. | R5. | Si hay lugar, se puede cambiar el estado. |
| 27 | Cambia el puesto elegido a `EnProceso` y guarda motivo/equipo. | R2 y R5. | El estado cambia **antes** del `delay`, para que otro ingreso no elija el mismo puesto mientras el sensor responde. |
| 28 | Devuelve el número humano del puesto (`índice + 1`). | R5 y presentación. | Las listas parten en cero, pero los puestos se muestran desde uno al usuario. |
| 29 | Cierra `iniciarEntrada`. | R5. | Esta función solo reserva; todavía no deja el equipo `EnUso`. |

## 5.3 Confirmación de entrada y uso de `when`

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 31 | Declara `confirmarEntrada`. | R5. | Se llama después de esperar la confirmación simulada del sensor. |
| 32 | Convierte el número visible en índice seguro. | R2 y R6. | Reutiliza una sola validación de rango en vez de repetir restas y condiciones. |
| 33 | Evalúa el estado actual y asigna ese valor a `estado`. | R2 y pauta de `sealed class` + `when`. | `when` es exhaustivo porque `EstadoPuesto` es sellada; `val estado` evita volver a leer la lista. |
| 34 | Maneja el caso válido `EnProceso`. | R2 y R5. | Solo un puesto reservado para entrada puede confirmarse. |
| 35 | Compara el código pendiente con el equipo que se intenta confirmar. | R2 y R6. | `?.` evita error si por alguna razón no se guardó un equipo; una comparación con `null` simplemente falla. |
| 36 | Lanza error si la operación pendiente era de otro equipo. | R6. | Previene una transición de estado incoherente. |
| 37 | Cierra la validación del código. | R5. | Al pasarla, el equipo corresponde a la reserva. |
| 38 | Cambia el estado a `EnUso`. | R2 y R5. | Es la transición que confirma la entrada del equipo. |
| 39 | Cierra la rama `EnProceso`. | Estructura de `when`. | Termina el único camino exitoso de confirmación. |
| 41 | Rechaza confirmar una entrada en un puesto disponible. | R2 y R6. | No hubo una reserva previa que confirmar. |
| 42 | Rechaza confirmar en un puesto ya ocupado. | R2 y R6. | Evita reemplazar silenciosamente un equipo activo. |
| 43 | Rechaza confirmar en un puesto fuera de servicio e informa el motivo. | R2 y R6. | El requisito exige considerar explícitamente ese cuarto estado. |
| 44 | Cierra `when`. | R2. | Todos los estados posibles quedaron cubiertos. |
| 45 | Cierra `confirmarEntrada`. | R5. | La entrada queda completamente confirmada solo si se llegó a la línea 38. |

## 5.4 Inicio, cancelación y finalización de salida

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 47 | Declara `iniciarSalida`, que retorna una `SalidaPendiente`. | R5. | La corrutina necesita conservar puesto y equipo mientras espera calcular la tarifa. |
| 48 | Inicia la búsqueda del puesto que contiene el código. | R5. | `indexOfFirst` recorre la lista sin exponerla al menú. |
| 49 | Acepta solo `EnUso` y compara código ignorando mayúsculas. | R2, R5 y R6. | `is EstadoPuesto.EnUso` habilita el *smart cast*: Kotlin sabe que `it.equipo` existe en la segunda parte del `&&`. |
| 50 | Cierra la lambda de búsqueda. | R5. | El resultado es un índice o `-1`. |
| 51 | Detecta que no se encontró equipo activo. | R6. | Cubre el caso explícito «equipo no encontrado». |
| 52 | Lanza un mensaje claro de equipo inexistente. | R6. | El programa puede continuar con la siguiente opción de menú. |
| 53 | Cierra la validación de búsqueda. | R5. | Desde aquí el índice sí apunta a un equipo activo. |
| 55 | Recupera el equipo desde `EnUso`. | R5. | Se usa `as` porque las líneas 48-53 ya garantizaron ese tipo. En otro contexto se preferiría `as?` para no arriesgar una excepción. |
| 56 | Cambia el puesto a `EnProceso` con motivo de salida. | R2 y R5. | Bloquea nuevas operaciones sobre el puesto mientras la tarifa se procesa. |
| 57 | Construye y devuelve `SalidaPendiente`. | R5. | Encapsula los dos datos que la etapa final necesita. |
| 58 | Cierra `iniciarSalida`. | R5. | La salida aún no libera el puesto ni crea un ticket. |
| 60 | Declara `cancelarSalida`. | R5 y R6. | Es una operación de recuperación si falla el cálculo o la validación después de marcar `EnProceso`. |
| 61 | Obtiene el índice validado del puesto pendiente. | R6. | Impide acceder fuera de la lista. |
| 62 | Lee una vez el estado actual. | R2. | Se utilizará para comprobar que la cancelación sigue siendo válida. |
| 63 | Comprueba que sigue `EnProceso` y que corresponde al mismo equipo. | R2 y R6. | Evita deshacer accidentalmente una operación distinta. |
| 64 | Restaura el puesto a `EnUso`. | R2, R5 y R6. | Si la salida no se completó, el equipo no debe desaparecer ni dejar el puesto bloqueado. |
| 65 | Cierra la condición. | R6. | Si no coincide, se omite la restauración para no corromper estados. |
| 66 | Cierra `cancelarSalida`. | R5. | La función no crea ticket ni modifica recaudación. |
| 68 | Declara `finalizarSalida`; recibe el contexto pendiente, minutos y monto. | R3, R4 y R5. | Se separa de `iniciarSalida` para que el `delay` quede entre ambas etapas. |
| 69 | Recupera el índice seguro. | R6. | Reutiliza la validación central de rango. |
| 70 | Lee el estado actual del puesto. | R2. | La salida no debe finalizar si el estado fue alterado. |
| 71 | Detecta si ya no es la salida que estaba pendiente. | R2 y R6. | Combina negación de tipo y comparación de código para proteger la transición. |
| 72 | Lanza error si la operación dejó de ser válida. | R6. | Impide emitir un ticket para un puesto diferente o liberado. |
| 73 | Cierra la protección de estado. | R5. | Solo queda el camino válido de término. |
| 75 | Inicia la construcción del ticket. | R4. | El ticket es el registro histórico exigido al liberar el equipo. |
| 76 | Asigna el correlativo y luego lo incrementa. | R4. | `siguienteTicket++` usa el valor actual y prepara el siguiente número. |
| 77 | Copia el código del equipo al ticket. | R4. | El historial conserva información aunque ya no esté en el puesto. |
| 78 | Guarda la categoría polimórfica. | R1, R4 y polimorfismo. | `categoria()` se despacha según el tipo real de `Equipo`; no se usa `if` por clase. |
| 79 | Copia el usuario al ticket. | R4. | Más tarde permite filtrar usuarios mensuales. |
| 80 | Guarda el tiempo de uso. | R4. | Sirve para tickets y para encontrar el máximo. |
| 81 | Guarda el monto final. | R3 y R4. | El valor ya incluye las reglas aplicadas por el sistema. |
| 82 | Cierra el constructor `Ticket`. | R4. | El comprobante queda listo para persistir en memoria. |
| 83 | Agrega el ticket al historial. | R4 y pauta de colecciones. | `MutableList.add` conserva el orden cronológico de las salidas. |
| 84 | Acumula el monto al total de turno. | R3 y R4. | `+=` es la forma corta de sumar y reasignar una variable `var`. |
| 85 | Libera el puesto. | R2 y R5. | Completa la transición `EnProceso -> Disponible`. |
| 86 | Devuelve el ticket creado. | R4 y presentación. | `Main.kt` puede mostrar su número y monto sin acceder al historial privado. |
| 87 | Cierra `finalizarSalida`. | R5. | En este punto sí se emitió ticket, se actualizó recaudación y se liberó puesto. |

## 5.5 Cálculo general y consultas funcionales

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 89 | Comentario que fija el orden de cálculo. | R3 y R7. | Deja claro que la subclase calcula tarifa; el sistema aplica IVA y descuento docente. |
| 90 | Declara `calcularMontoFinal`. | R3. | La lógica común no se repite en `Laptop`, `Desktop` y `EstacionGrafica`. |
| 91 | Valida minutos antes de calcular. | R3 y R6. | Evita resultados negativos o sin sentido. |
| 92 | Pide el costo polimórfico y aplica IVA 19%. | R1, R3 y polimorfismo. | La llamada elige la regla correcta por subtipo; el IVA se aplica una vez en la capa común. |
| 93 | Aplica descuento docente de 50% solo después de IVA. | R3. | `if` devuelve el total correcto para ambos casos; respeta el orden expresado en el caso. |
| 94 | Cierra la función. | R3. | El resultado se valida y luego se convierte en ticket desde `Main.kt`. |
| 96 | Cuenta puestos disponibles. | R4. | `count` es una función de orden superior que evita un contador manual. |
| 98 | Declara la consulta de equipos mensuales como lista de tickets. | R4. | La consulta devuelve información reutilizable; no solo imprime texto. |
| 99 | Filtra el historial por `MENSUAL`. | R4 y pauta de colecciones. | `filter` expresa la consulta de negocio de forma declarativa. |
| 101 | Inicia la función de ingreso promedio. | R4. | Devuelve `Double` porque un promedio puede tener decimales. |
| 102 | Retorna cero si no hay tickets; de lo contrario suma y divide. | R4 y colecciones. | Evita división por cero y usa `sumOf` para sumar `montoPagado` sin bucle `for`. |
| 104 | Mapea tickets a sus códigos. | R4 y colecciones. | `map` transforma una lista de `Ticket` en una lista de `String`. |
| 106 | Busca el ticket con más minutos o `null`. | R4 y colecciones. | `maxByOrNull` comunica en su nombre que el historial vacío no tiene máximo. |
| 108 | Inicia una consulta que retorna montos por tipo. | R3 y R4. | `Map<String, Double>` relaciona cada categoría con su recaudación. |
| 109 | Agrupa tickets por `tipoEquipo`. | R4 y colecciones. | `groupBy` crea un `Map<String, List<Ticket>>`. |
| 110 | Reemplaza cada lista agrupada por la suma de montos. | R4 y colecciones. | `mapValues` y `sumOf` evitan construir un mapa manual con acumuladores. `_` descarta la clave no usada dentro de la lambda. |
| 112 | Inicia la consulta del tipo con más ingresos. | R4. | Retorna `String?` porque no hay ganador si el historial está vacío. |
| 113 | Busca la entrada de mayor valor y toma su clave con `?.key`. | R4 y colecciones. | El operador seguro devuelve `null` en vez de fallar si el mapa está vacío. |
| 115 | Expone el total calculado. | R3 y R4. | Es un getter de solo lectura; no permite que `Main.kt` altere `recaudacionTotal`. |
| 117 | Expone la cantidad de tickets. | R4. | `size` responde cuántos equipos se atendieron. |

## 5.6 Puestos fuera de servicio

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 119 | Declara la operación para deshabilitar un puesto. | R2. | El caso no solo pide representar `FueraDeServicio`; también exige tratar su motivo. |
| 120 | Convierte número visible a índice validado. | R6. | Centraliza el control de rango. |
| 121 | Evalúa todos los estados actuales. | R2 y `sealed class`. | No se asume que un puesto está libre u ocupado. |
| 122 | Solo desde `Disponible` cambia a `FueraDeServicio`. | R2. | Es la transición segura: no se corta una operación activa. |
| 123 | Rechaza deshabilitar un puesto ocupado e identifica el equipo. | R2 y R6. | Protege el equipo que todavía está en uso. |
| 124 | Rechaza deshabilitar una operación en proceso. | R2 y R6. | No se interrumpe el sensor o cálculo simulado. |
| 125 | Rechaza repetir la deshabilitación. | R2 y R6. | Evita un cambio sin sentido y entrega retroalimentación clara. |
| 126 | Cierra el `when`. | R2. | Los cuatro estados quedaron tratados. |
| 127 | Cierra `deshabilitarPuesto`. | R2. | El cambio solo es válido desde disponible. |
| 129 | Declara la operación inversa: habilitar puesto. | R2. | Permite volver a usar un recurso reparado. |
| 130 | Calcula el índice seguro. | R6. | Usa la misma regla de rango que las demás operaciones. |
| 131 | Evalúa el estado actual. | R2. | Se necesita para decidir si la rehabilitación es legal. |
| 132 | Cambia `FueraDeServicio` a `Disponible`. | R2. | Es la única transición normal de habilitación. |
| 133 | Rechaza habilitar algo que ya estaba disponible. | R6. | Informa una solicitud redundante. |
| 134 | Rechaza habilitar un puesto ocupado. | R2 y R6. | No se altera un estado activo. |
| 135 | Rechaza habilitar mientras hay proceso en curso. | R2 y R6. | Mantiene coherente la transición de la corrutina. |
| 136 | Cierra el `when`. | R2. | Nuevamente se cubren todos los estados. |
| 137 | Cierra `habilitarPuesto`. | R2. | Finaliza la operación de recuperación. |

## 5.7 Presentación de estado, consultas y reporte

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 139 | Declara la impresión del estado de puestos. | R2 y R4: mostrar resultados en consola. | La visualización se concentra aquí, sin exponer la lista privada. |
| 140 | Imprime un encabezado con salto de línea. | R4. | Separa visualmente cada salida del menú. |
| 141 | Recorre puestos con índice y estado. | R4 y pauta de iteración. | `forEachIndexed` equivale a un `for` con contador, pero expresa mejor que se necesita índice y elemento. |
| 142 | Calcula una descripción mediante `when`. | R2 y clases selladas. | Cada estado requiere mostrar información distinta. |
| 143 | Describe `Disponible`. | R2. | Es el estado simple sin datos adicionales. |
| 144 | Muestra el detalle del equipo en uso. | R1 y R2. | Llama polimórficamente a `detalle()`, por lo que una estación gráfica incluye su GPU. |
| 145 | Muestra el motivo de procesamiento. | R2 y R5. | Cumple que el sistema indique por qué está en proceso. |
| 146 | Muestra el motivo fuera de servicio. | R2. | Cumple que dicho estado registre y exhiba una razón. |
| 147 | Cierra el `when`. | R2. | La variable `descripcion` tiene valor para todo estado. |
| 148 | Imprime número humano y descripción. | R4. | Suma uno al índice porque el usuario no debe ver posiciones desde cero. |
| 149 | Cierra la iteración. | R4. | Se imprime una línea por cada puesto. |
| 150 | Cierra la función. | R4. | No devuelve datos; su responsabilidad es la presentación. |
| 152 | Declara la impresión de consultas de negocio. | R4. | Agrupa las respuestas solicitadas por el caso. |
| 153 | Imprime encabezado. | R4. | Distingue este bloque del reporte de cierre. |
| 154 | Muestra puestos disponibles. | R4. | Reusa la función de consulta, no recalcula desde la impresión. |
| 155 | Obtiene tickets mensuales, mapea códigos, usa `ninguno` si queda vacío y los une. | R4 y colecciones. | Encadena `filter`, `map`, `ifEmpty` y `joinToString` para convertir datos de negocio en texto legible. |
| 156 | Muestra el promedio formateado. | R4. | Separa el cálculo (`ingresoPromedio`) del formato de moneda (`formatearMonto`). |
| 157 | Muestra códigos finalizados o `ninguno`. | R4. | `ifEmpty` evita una línea vacía poco clara. |
| 158 | Guarda el ticket de mayor tiempo. | R4. | Leerlo una vez mejora la claridad antes de imprimirlo. |
| 159 | Imprime código/minutos o `ninguno` si es `null`. | R4 y null safety. | `?.let` usa el ticket solo si existe; `?:` es el operador Elvis que da alternativa al `null`. |
| 160 | Cierra la función. | R4. | Termina las consultas previas al cierre. |
| 162 | Declara el reporte de cierre. | R4. | Es el resumen final exigido por el enunciado. |
| 163 | Imprime encabezado con nombre del sistema. | R4. | Usa la propiedad privada configurada al crear el sistema. |
| 164 | Pregunta si no hay tickets. | R4. | El reporte también debe ser válido para un turno sin salidas. |
| 165 | Informa explícitamente que no hubo equipos finalizados. | R4 y R6. | Evita una salida vacía ambigua. |
| 166 | Abre el caso alternativo con historial. | R4. | Solo se itera si hay elementos. |
| 167 | Recorre cada ticket. | R4 y pauta de iteración. | `forEach` muestra ordenadamente todos los registros. |
| 168 | Abre un `println` multilínea. | R4. | Divide una cadena larga sin perder claridad. |
| 169 | Imprime número, tipo y código del ticket. | R4. | Son campos obligatorios del resumen. |
| 170 | Agrega minutos y monto ya formateado. | R4. | Completa los otros dos datos exigidos del ticket. |
| 171 | Cierra la llamada `println`. | R4. | Finaliza una fila del reporte. |
| 172 | Cierra el recorrido de tickets. | R4. | Cada ticket se imprimió exactamente una vez. |
| 173 | Cierra el `else`. | R4. | Termina el tratamiento de historial vacío/no vacío. |
| 174 | Imprime separador visual. | R4. | No afecta cálculos; mejora legibilidad de la consola. |
| 175 | Imprime recaudación total. | R3 y R4. | Usa getter y formato, no modifica el total. |
| 176 | Imprime cantidad de atendidos. | R4. | Responde otra métrica obligatoria. |
| 177 | Imprime promedio. | R4. | Reutiliza la consulta y evita duplicar la fórmula. |
| 178 | Imprime tipo con mayor ingreso o `N/A`. | R4. | El operador Elvis cubre un reporte sin tickets. |
| 179 | Imprime puestos disponibles al cierre. | R4. | Confirma que las salidas liberaron correctamente los puestos. |
| 180 | Imprime cada par tipo/monto o `sin cobros`. | R3 y R4. | `entries.joinToString` transforma el mapa de ingresos por tipo a una sola línea legible. |
| 181 | Cierra el reporte. | R4. | Finaliza la presentación del cierre. |

## 5.8 Ayudantes privados y formato de dinero

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 183 | Declara `codigoEstaActivo` como helper privado. | R2 y R6. | Es detalle interno de entrada; el menú no necesita ni debe llamarlo. |
| 184 | Evalúa cada estado de puesto. | R2 y `sealed class`. | La forma de saber si un código está activo depende del tipo de estado. |
| 185 | Compara código cuando está `EnUso`. | R2. | `equals(..., ignoreCase = true)` acepta la misma identificación aunque el usuario cambie mayúsculas. |
| 186 | Compara código opcional cuando está `EnProceso`. | R2 y R5. | La cadena `?.` evita una excepción si el estado en proceso no tiene equipo asociado. `== true` convierte el posible `null` en falso. |
| 187 | Devuelve falso para disponible y fuera de servicio. | R2. | En esos estados no hay un equipo activo. Dos casos se pueden unir con coma. |
| 188 | Cierra el `when`. | R2. | La función obtiene un booleano para todo estado. |
| 189 | Cierra `codigoEstaActivo`. | R6. | Mantiene privada esta validación de consistencia. |
| 191 | Declara `indicePuesto`. | R6. | Convierte un número de usuario a índice de lista de forma segura y reutilizable. |
| 192 | Comprueba que el número está entre 1 y capacidad. | R6. | `!in 1..capacidad` es una forma Kotlin concisa de validar rango inclusivo. |
| 193 | Lanza un error descriptivo si está fuera del rango. | R6. | Evita un `IndexOutOfBoundsException` técnico. |
| 194 | Cierra el `if`. | R6. | Si pasa, el índice es válido. |
| 195 | Resta uno para obtener índice interno. | R2 y presentación. | Las personas usan puestos 1-10; `MutableList` usa índices 0-9. |
| 196 | Cierra `indicePuesto`. | R6. | Todos los métodos públicos reutilizan este control. |
| 197 | Cierra `TechLabProSistema`. | R7. | Finaliza la capa de negocio y encapsulación. |
| 199 | Declara una función de formato monetario de nivel superior. | R4. | Evita repetir `String.format` en consultas y reportes. `Locale.forLanguageTag("es-CL")` elige formato chileno y `\$` imprime el signo peso literal. |

---

# 6. `Main.kt`

Ruta: `TechLabPro/src/main/kotlin/Main.kt`

Este archivo es la capa de consola. Coordina al usuario y a las corrutinas, pero no guarda puestos ni aplica tarifas directamente: delega esa responsabilidad a `TechLabProSistema`.

## 6.1 Importaciones y constantes de tiempo

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 1 | Importa `async`. | R5. | Permite iniciar una corrutina que devuelve un resultado futuro (`Deferred`). |
| 2 | Importa `awaitAll`. | R5. | Espera que finalicen todos los `Deferred` de una demostración. |
| 3 | Importa `coroutineScope`. | R5. | Crea un alcance estructurado: no termina hasta que sus corrutinas hijas terminan. |
| 4 | Importa `delay`. | R5. | Simula la espera del sensor sin bloquear el hilo como haría `Thread.sleep`. |
| 5 | Importa `runBlocking`. | R5. | Permite llamar funciones `suspend` desde el punto de entrada de una aplicación de consola. |
| 7 | Declara la demora de entrada: 3.000 milisegundos. | R5: espera de 3 segundos. | `const val` se calcula en compilación, es inmutable y evita repetir un número mágico. La `L` implícita por el tipo inferido de `3_000L` es necesaria porque `delay` espera `Long`. |
| 8 | Declara la demora de salida: 6.500 milisegundos. | R5: espera de 6,5 segundos. | El guion bajo es solo separador visual para humanos; el valor sigue siendo 6500. |

## 6.2 Corrutina de ingreso

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 10 | Comentario que explica el objetivo de la función. | R7. | Documenta que `delay` no bloquea otras corrutinas. |
| 11 | Declara `registrarIngreso` como `suspend` y devuelve `Boolean`. | R5 y R6. | `suspend` autoriza usar `delay`. El booleano comunica éxito (`true`) o error controlado (`false`) a quien la ejecute. |
| 12 | Inicia un `try` y lo devuelve como resultado de la función. | R6. | En Kotlin `try` es una expresión: cada camino puede producir el `Boolean` que retorna la función. |
| 13 | Pide al sistema reservar el primer puesto disponible. | R2, R3, R5 y R6. | Delega validación de código, duplicados, capacidad y cambio inicial de estado a la clase de negocio. |
| 14 | Informa que el puesto está en proceso. | R2 y R5. | Muestra el motivo de procesamiento y el detalle polimórfico del equipo. |
| 15 | Suspende esta corrutina por tres segundos. | R5. | `delay` permite que otras corrutinas avancen mientras esta espera la simulación del sensor. |
| 16 | Confirma la entrada en el mismo puesto. | R2 y R5. | El sistema verifica que todavía sea la misma operación pendiente y luego cambia a `EnUso`. |
| 17 | Informa el éxito al usuario. | R4: salida en consola clara. | Usa interpolación para mostrar puesto y código. |
| 18 | Devuelve `true`. | R6. | Señala que toda la secuencia se completó sin excepción de negocio. |
| 19 | Captura cualquier `ReglaNegocioException`. | R6 y pauta de `try/catch`. | Un solo tipo base cubre código inválido, capacidad llena y operaciones no permitidas. |
| 20 | Delega la impresión de un error legible. | R6. | Evita repetir la misma forma de mostrar errores en cada función. |
| 21 | Devuelve `false`. | R6. | La corrutina falla de forma controlada, sin cerrar el menú ni lanzar una traza. |
| 22 | Cierra `catch`. | R6. | Termina el camino alternativo de error. |
| 23 | Cierra `registrarIngreso`. | R5. | El flujo de entrada tiene reserva, espera, confirmación y recuperación ante error. |

## 6.3 Corrutina de salida

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 25 | Comentario que documenta la salida asíncrona. | R7. | Resume las dos responsabilidades: calcular y emitir ticket. |
| 26 | Declara `registrarSalida` como `suspend` y retorna `Boolean`. | R5 y R6. | Sigue el mismo contrato que el ingreso: puede esperar y comunicar éxito o error. |
| 27 | Declara `salidaPendiente` mutable y nullable, inicialmente `null`. | R5 y R6. | Debe existir fuera del `try` para que el `catch` pueda restaurar el puesto si falla una etapa posterior. Es nullable porque todavía no se inició la salida. |
| 28 | Abre el `try` que también será el retorno. | R6. | Agrupa búsqueda, espera, cálculo, validación y emisión de ticket. |
| 29 | Pide al sistema iniciar la salida y guarda su contexto. | R2 y R5. | El sistema encuentra el equipo, marca `EnProceso` y devuelve el puesto/equipo asociado. |
| 30 | Obtiene el valor no nulo de la salida. | R5. | `requireNotNull` expresa al compilador que la línea anterior debió asignarlo. Si esa premisa fuera falsa, fallaría rápido por un error de programación, no de negocio. |
| 31 | Muestra que la tarifa está siendo calculada. | R2 y R5. | Expone el motivo requerido para el estado `EnProceso`. |
| 32 | Suspende la corrutina por 6,5 segundos. | R5. | Simula la operación de salida sin congelar las otras corrutinas. |
| 34 | Calcula el monto final del equipo. | R1 y R3. | El sistema combina polimorfismo, IVA y descuento docente; `Main.kt` no conoce tarifas concretas. |
| 35 | Detecta la excepción legítima de Desktop menor a 30 minutos. | R1 y R3. | El tipo se comprueba con `is Desktop`; el monto cero solo será permitido en este caso específico. |
| 36 | Valida que el resultado monetario sea aceptable. | R3 y R6. | Impide cerrar una salida con monto negativo o cero no permitido. |
| 38 | Finaliza la salida y recibe el ticket. | R2, R3, R4 y R5. | Esta operación registra historial, suma recaudación y libera el puesto de manera centralizada. |
| 39 | Crea el mensaje de cobro mediante `if`. | R1 y R4. | Muestra una explicación especial para el Desktop gratuito; en los demás casos usa formato de moneda. |
| 40 | Imprime confirmación, número de ticket y cobro. | R4. | El usuario ve el resultado final de la operación asíncrona. |
| 41 | Devuelve `true`. | R6. | Indica que la salida terminó y el estado quedó consistente. |
| 42 | Captura errores de negocio durante cualquier etapa. | R6. | Atiende, por ejemplo, equipo no encontrado, minutos inválidos o tarifa no permitida. |
| 43 | Si existe una salida pendiente, intenta restaurarla a `EnUso`. | R2, R5 y R6. | `?.let` ejecuta la restauración solo cuando `salidaPendiente` no es `null`; evita dejar un puesto bloqueado en `EnProceso`. |
| 44 | Muestra el mensaje de error. | R6. | La consola entrega una respuesta entendible en vez de interrumpir la aplicación. |
| 45 | Devuelve `false`. | R6. | El menú puede seguir operativo y ejecutar otra opción. |
| 46 | Cierra el `catch`. | R6. | Termina el manejo controlado de fallos. |
| 47 | Cierra `registrarSalida`. | R5. | La función implementa la operación asíncrona completa del caso. |

## 6.4 Función común de error y demostración automática

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 49 | Declara `mostrarError` privada. | R6 y R7. | Centraliza la presentación de errores; solo la capa de consola debe llamarla. |
| 50 | Imprime el mensaje de la excepción o un texto de respaldo. | R6. | `?:` es el operador Elvis: si `message` fuera `null`, evita imprimir `null` al usuario. |
| 51 | Cierra `mostrarError`. | R6. | Finaliza la responsabilidad de presentación de errores. |
| 53 | Declara una demostración suspendida. | R5 y pruebas. | Se puede llamar desde `main` o desde el menú porque ambos están dentro de un contexto de corrutinas. |
| 54 | Crea una lista inmutable de casos. | R1 y R4. | `listOf` modela los datos de prueba sugeridos sin permitir que se agreguen accidentalmente. |
| 55 | Agrega Laptop mensual por 75 minutos. | R1. | Prueba descuento de 20% en Laptop. `to 75` crea un `Pair<Equipo, Int>`. |
| 56 | Agrega Laptop estudiante por 180 minutos. | R1. | Prueba tarifa Laptop sin descuento mensual. |
| 57 | Agrega Desktop por 25 minutos. | R1 y R3. | Prueba el único caso válido de tarifa cero. |
| 58 | Agrega Estación gráfica con GPU y usuario docente por 120 minutos. | R1 y R3. | Prueba recargo de GPU, IVA y descuento docente. El argumento nombrado mejora legibilidad. |
| 59 | Agrega Estación gráfica sin GPU por 45 minutos. | R1. | Prueba la rama de no aplicar recargo. |
| 60 | Cierra `listOf`. | R1. | Los cinco casos quedan disponibles para entradas y salidas. |
| 62 | Imprime título de prueba de error. | R6. | Hace visible que el programa continúa frente a un dato inválido. |
| 63 | Intenta registrar un código inválido. | R3 y R6. | Demuestra `Regex`, excepción, `try/catch` y continuidad. No se ocupa un puesto porque la validación ocurre primero. |
| 65 | Imprime título de ingresos concurrentes. | R5. | Ayuda a distinguir las etapas de demostración. |
| 66 | Abre un `coroutineScope`. | R5. | Asegura que la demostración no avance a estados o salidas hasta que todos los ingresos terminen. |
| 67 | Mapea cada caso a una corrutina `async`, inicia el ingreso y espera todas. | R5 y colecciones. | `map` crea una tarea por caso, `async` permite simultaneidad y `awaitAll` sincroniza. Cinco esperas de 3 segundos ocurren aproximadamente en paralelo. |
| 68 | Cierra el alcance de corrutinas de ingreso. | R5. | Todas las entradas están confirmadas cuando se llega a la línea siguiente. |
| 69 | Muestra estados después de los ingresos. | R2 y R4. | Evidencia que los primeros cinco puestos están `EnUso`. |
| 71 | Imprime título de salidas concurrentes. | R5. | Inicia la segunda fase demostrativa. |
| 72 | Abre un nuevo `coroutineScope`. | R5. | Agrupa las salidas y espera su finalización antes de consultar historial. |
| 73 | Ejecuta una salida asíncrona para cada par equipo/minutos y espera todas. | R5, R4 y colecciones. | Reutiliza cada código y duración de prueba; al terminar deben existir cinco tickets. |
| 74 | Cierra el alcance de salidas. | R5. | Ya se actualizaron recaudación, historial y puestos disponibles. |
| 75 | Imprime las consultas de negocio. | R4. | Verifica disponibilidad, mensual, promedio, códigos y máximo tiempo. |
| 76 | Imprime el reporte de cierre. | R4. | Comprueba tickets, total, cantidad, promedio, tipo ganador y puestos al cierre. |
| 77 | Cierra `ejecutarDemostracion`. | Pruebas y R5. | La demostración cubre reglas principales sin interacción manual. |

## 6.5 Menú de consola

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 79 | Declara `mostrarMenu`. | R4 y R7. | La impresión del menú tiene una función separada y reutilizable. |
| 80 | Inicia el `println` del menú. | R4. | Todo el menú se emite como un bloque. |
| 81 | Abre un string multilínea. | Presentación de consola. | `"""` evita concatenar muchas cadenas con `\n`. |
| 83 | Imprime título de la aplicación. | R4. | Orienta al usuario en la consola. |
| 84 | Describe opción de ingreso. | R5. | Conduce a la operación de entrada asíncrona. |
| 85 | Describe opción de salida. | R5. | Conduce a la operación de salida asíncrona. |
| 86 | Describe consulta de estados. | R2 y R4. | Hace visible todos los puestos y motivos. |
| 87 | Describe consultas de negocio. | R4. | Agrupa respuestas de la sección de catálogo. |
| 88 | Describe reporte de cierre. | R4. | Permite ver resumen del turno en cualquier momento. |
| 89 | Describe deshabilitar puesto. | R2. | Permite demostrar `FueraDeServicio`. |
| 90 | Describe habilitar puesto. | R2. | Permite recuperar un puesto fuera de servicio. |
| 91 | Describe demostración automática. | R1-R6. | Ejecuta datos sugeridos para verificar el proyecto. |
| 92 | Describe salida del programa. | Flujo de consola. | Da una opción explícita para terminar el ciclo. |
| 93 | Cierra el string y elimina sangría común. | Presentación de consola. | `trimIndent()` evita que los espacios de indentación del código aparezcan en pantalla. |
| 94 | Cierra la llamada `println`. | Presentación de consola. | Termina el bloque visual. |
| 95 | Cierra `mostrarMenu`. | R7. | La función solo presenta opciones; no ejecuta lógica de negocio. |

## 6.6 Ejecución de opciones del menú

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 97 | Declara `ejecutarMenu` como suspendida. | R5. | Algunas opciones llaman a `registrarIngreso`, `registrarSalida` o demostración, que son suspendidas. |
| 98 | Abre un ciclo infinito. | Aplicación de consola funcional. | El menú se repite hasta que la opción 0 haga `return`. |
| 99 | Muestra el menú en cada iteración. | R4. | El usuario siempre ve opciones antes de escribir. |
| 100 | Lee una opción y la evalúa con `when`. | Pauta de condicionales y R4. | `when` es más legible que una cadena extensa de `if/else if`. |
| 101 | Para opción 1, lee un equipo y registra ingreso solo si no es `null`. | R5 y R6. | `?.let` evita invocar una corrutina cuando hubo error al leer tipo de usuario o equipo. |
| 102 | Abre la rama de opción 2. | R5. | Permite ejecutar más de una instrucción antes de salida. |
| 103 | Solicita el código de equipo. | R5. | La salida busca el equipo activo por este identificador. |
| 104 | Lee minutos o reinicia el ciclo si no son enteros. | R3 y R6. | `?: continue` evita llamar a la salida con `null` y vuelve al menú de forma segura. |
| 105 | Ejecuta la salida asíncrona. | R5. | La función maneja internamente cálculo, validación, ticket y errores. |
| 106 | Cierra la rama de salida. | Flujo de menú. | Finaliza la opción 2. |
| 108 | Opción 3: muestra puestos. | R2 y R4. | Delegación directa a la capa de sistema. |
| 109 | Opción 4: muestra consultas. | R4. | Evita duplicar las fórmulas en el menú. |
| 110 | Opción 5: muestra cierre. | R4. | Permite inspeccionar el resumen sin salir. |
| 111 | Opción 6: solicita deshabilitar. | R2. | El argumento nombrado explica qué modo de cambio se pide. |
| 112 | Opción 7: solicita habilitar. | R2. | Reutiliza la misma función con el modo contrario. |
| 113 | Opción 8: ejecuta la demostración. | R1-R6. | Sirve como prueba manual completa desde la consola. |
| 114 | Abre la rama de salida. | Flujo de menú. | La opción 0 necesita dos instrucciones. |
| 115 | Informa fin de turno. | Presentación de consola. | Confirma que la opción fue entendida. |
| 116 | Sale de `ejecutarMenu`. | Flujo de consola. | Este `return` rompe el `while(true)` y permite terminar `main`. |
| 117 | Cierra la rama de salida. | Estructura de `when`. | Finaliza el caso 0. |
| 119 | Atiende opciones no reconocidas. | R6. | El menú continúa en vez de terminar por una entrada incorrecta. |
| 120 | Cierra `when`. | Pauta de condicionales. | Toda entrada de opción tiene un resultado definido. |
| 121 | Cierra el `while`. | Aplicación de consola. | Se repite solo mientras no se ejecute el `return` de opción 0. |
| 122 | Cierra `ejecutarMenu`. | R7. | Termina el orquestador interactivo. |

## 6.7 Lectura y creación de equipos

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 124 | Declara el lector de equipo y permite retorno nullable. | R1 y R6. | Devuelve `null` ante una elección inválida, para que el menú no intente un ingreso incompleto. |
| 125 | Muestra los tres tipos de equipo. | R1. | Traduce las subclases del modelo a opciones simples de consola. |
| 126 | Lee la opción de tipo. | R1. | Se usará en un `when` para instanciar la subclase correcta. |
| 127 | Lee código. | R1 y R3. | La validación formal se realiza al iniciar el ingreso. |
| 128 | Lee marca y modelo. | R1. | Es información obligatoria y común a todo equipo. |
| 129 | Lee usuario o retorna `null` si no es válido. | R1, R3 y R6. | El operador Elvis detiene de forma segura la creación si falla el enum. |
| 131 | Retorna una expresión `when` según el tipo elegido. | Pauta de condicionales y R1. | `when` construye directamente el subtipo correcto; no se crea una clase base genérica. |
| 132 | Crea un `Laptop`. | R1 y polimorfismo. | Este objeto ejecutará su propia regla de descuento mensual. |
| 133 | Crea un `Desktop`. | R1 y polimorfismo. | Este objeto aplicará el mínimo de 30 minutos. |
| 134 | Abre la rama de estación gráfica. | R1. | Requiere un dato adicional que los otros tipos no tienen. |
| 135 | Lee y convierte la respuesta de GPU a booleano. | R1. | `equals("s", ignoreCase = true)` acepta `s` o `S`; cualquier otra entrada se interpreta como `false`. |
| 136 | Crea la estación gráfica con GPU indicada. | R1 y polimorfismo. | El booleano queda inmutable en el objeto y condiciona su tarifa/detalle. |
| 137 | Cierra la rama de estación. | R1. | Ya se construyó el subtipo con su dato propio. |
| 139 | Abre el caso para tipo no válido. | R6. | Cubre cualquier texto distinto de 1, 2 o 3. |
| 140 | Muestra el error de selección. | R6. | Da una instrucción de corrección específica. |
| 141 | Devuelve `null`. | R6. | El menú usa ese `null` para no llamar a `registrarIngreso`. |
| 142 | Cierra la rama alternativa. | Pauta de condicionales. | Termina el manejo de tipo inválido. |
| 143 | Cierra `when`. | R1. | La función retorna un equipo válido o `null`. |
| 144 | Cierra el lector de equipo. | R7. | Separar lectura de creación simplifica el menú. |
| 146 | Declara el lector de `TipoUsuario`. | R1 y R3. | Convierte texto de consola a un enum seguro. |
| 147 | Muestra las tres opciones de usuario. | R1. | Corresponden exactamente al glosario del caso. |
| 148 | Abre `when` sobre la elección. | Pauta de condicionales. | Cada texto válido se transforma en una constante del enum. |
| 149 | Devuelve `ESTUDIANTE`. | R1. | Es el primer tipo permitido. |
| 150 | Devuelve `MENSUAL`. | R1. | Habilita descuento solo en Laptop. |
| 151 | Devuelve `DOCENTE`. | R3. | Habilita descuento general luego de IVA. |
| 152 | Abre el caso inválido. | R6. | Protege el programa de usuarios no definidos por el enunciado. |
| 153 | Muestra error de tipo. | R6. | El usuario sabe que debe reintentar. |
| 154 | Devuelve `null`. | R6. | El lector de equipo detendrá su creación. |
| 155 | Cierra la rama alternativa. | Estructura de `when`. | Finaliza el tratamiento de tipo inválido. |
| 156 | Cierra `when`. | R1. | La función devuelve enum válido o nulo. |
| 157 | Cierra `leerTipoUsuario`. | R7. | Mantiene la conversión en una función especializada. |

## 6.8 Cambio de estado y lectura segura de consola

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 159 | Declara una función compartida para habilitar/deshabilitar. | R2 y R7. | El booleano evita duplicar lectura de puesto y `try/catch` en dos funciones casi iguales. |
| 160 | Lee número de puesto o retorna si no es entero. | R6. | `?: return` corta esta operación, pero no el menú completo. |
| 161 | Abre `try`. | R6. | Las transiciones de estado pueden rechazar puesto ocupado, en proceso o fuera de rango. |
| 162 | Pregunta qué operación se pidió. | Pauta de condicionales y R2. | El booleano usa una condición simple para bifurcar dos flujos relacionados. |
| 163 | Lee el motivo de deshabilitación. | R2. | El estado `FueraDeServicio` exige registrar esa información. |
| 164 | Solicita al sistema deshabilitar el puesto. | R2. | La transición y sus validaciones se mantienen en la capa de negocio. |
| 165 | Informa éxito. | R4. | Da retroalimentación inmediata en consola. |
| 166 | Abre la alternativa de habilitación. | R2. | No pide motivo porque recuperar el puesto solo cambia a disponible. |
| 167 | Solicita habilitar al sistema. | R2. | El sistema valida que realmente esté fuera de servicio. |
| 168 | Informa éxito de habilitación. | R4. | Confirma la transición al usuario. |
| 169 | Cierra `if/else`. | Pauta de condicionales. | Concluye ambos modos de cambio. |
| 170 | Captura errores de negocio. | R6. | Mantiene el menú abierto ante cambios no permitidos. |
| 171 | Muestra el error. | R6. | Unifica formato de mensajes. |
| 172 | Cierra `catch`. | R6. | Finaliza la recuperación de la operación. |
| 173 | Cierra `cambiarEstadoPuesto`. | R2. | La función solo coordina entrada/salida de consola. |
| 175 | Declara lector de texto. | R7. | Centraliza la interacción de texto con la consola. |
| 176 | Muestra el mensaje sin salto de línea. | Presentación de consola. | `print` deja el cursor junto al prompt para que la entrada se vea natural. |
| 177 | Lee, recorta espacios y entrega cadena no nula. | R6. | `readlnOrNull()` maneja fin de entrada; `?.trim().orEmpty()` convierte `null` en `""` para no lanzar excepción. |
| 178 | Cierra `leerTexto`. | R7. | Otras funciones reutilizan una lectura consistente. |
| 180 | Declara lector de entero nullable. | R3 y R6. | La duración y el puesto deben ser enteros; `Int?` representa conversión fallida sin excepción. |
| 181 | Imprime error cuando la conversión devolvió `null`. | R6. | `also` ejecuta el bloque como efecto lateral y luego devuelve el mismo `Int?`. |
| 182 | Cierra el bloque `also`. | R6. | El resultado final de la función sigue siendo el entero o `null`. |

## 6.9 Punto de entrada

| Línea(s) | Qué hace | Requisito que apoya | Por qué se usa así |
|---|---|---|---|
| 184 | Declara `main` y abre `runBlocking`. | R5. | En Kotlin una función de nivel superior puede ser punto de entrada. `runBlocking` permite llamar suspendidas desde consola y produce `MainKt` para Gradle. |
| 185 | Crea el sistema con nombre/capacidad por defecto. | R2 y R3. | Inicializa los 10 puestos disponibles y contadores para un turno nuevo. |
| 186 | Revisa si se recibió `--demo` como argumento. | Pruebas y flujo de consola. | `args.contains` permite ejecutar un recorrido automático sin reemplazar el menú normal. |
| 187 | Ejecuta la demostración asíncrona si se pidió. | R1-R6. | Es útil para verificar rápidamente todos los requisitos del caso. |
| 188 | Abre el camino normal. | Aplicación de consola. | Si no hay argumento especial, el usuario controla el sistema. |
| 189 | Ejecuta el menú suspendido. | R4 y R5. | Funciona porque sigue dentro del bloque `runBlocking`. |
| 190 | Cierra el `if/else`. | Flujo principal. | Solo se ejecuta una de las dos modalidades. |
| 191 | Cierra `runBlocking` y `main`. | R5. | Al terminar menú o demo, la aplicación de consola finaliza ordenadamente. |

---

# 7. Flujo completo de una operación

## Entrada

```text
Main.registrarIngreso
  -> Sistema.iniciarEntrada
      -> valida código, duplicados y capacidad
      -> Puesto: Disponible -> EnProceso
  -> delay(3 segundos)
  -> Sistema.confirmarEntrada
      -> Puesto: EnProceso -> EnUso
```

Esto cumple R5 porque el puesto queda reservado mientras el sensor simulado responde. Gracias a `delay`, otra corrutina puede reservar otro puesto en ese mismo tiempo.

## Salida

```text
Main.registrarSalida
  -> Sistema.iniciarSalida
      -> busca equipo activo
      -> Puesto: EnUso -> EnProceso
  -> delay(6,5 segundos)
  -> Sistema.calcularMontoFinal
      -> tarifa polimórfica -> IVA -> descuento docente
  -> validarMontoFinal
  -> Sistema.finalizarSalida
      -> crea Ticket, agrega historial, suma recaudación
      -> Puesto: EnProceso -> Disponible
```

Si una validación falla después de iniciar la salida, el `catch` llama `cancelarSalida` y restaura `EnUso`. Esa restauración es la parte que evita que el programa quede en un estado inconsistente.

# 8. Checklist para explicar el proyecto

- [ ] `Equipo` es `open` porque sus hijas necesitan sobrescribir la tarifa.
- [ ] Cada hija calcula solo la regla específica de su tipo; IVA y descuento docente se aplican una sola vez en `TechLabProSistema`.
- [ ] `EstadoPuesto` es `sealed` porque los cuatro estados son conocidos y `when` debe atenderlos todos.
- [ ] Los puestos y el historial son `private` para que nadie los modifique saltándose las reglas.
- [ ] Las consultas de negocio usan `filter`, `map`, `sumOf`, `groupBy` y `maxByOrNull` sobre `historial`.
- [ ] `delay` suspende una corrutina; no bloquea el hilo como `Thread.sleep`.
- [ ] `try/catch` captura `ReglaNegocioException` y mantiene el programa funcionando ante errores de usuario o negocio.
- [ ] `Ticket` guarda el resultado final, por eso las consultas se hacen sobre el historial y no sobre puestos activos.
