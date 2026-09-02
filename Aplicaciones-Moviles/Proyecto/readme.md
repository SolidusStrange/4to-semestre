# Roadmap — Desarrollo de Aplicaciones Móviles con Kotlin

## Objetivo general

Aprender Kotlin y desarrollo de aplicaciones Android desde una base de Java, avanzando progresivamente desde programas de consola hasta una aplicación Android funcional.

El proyecto principal y de largo plazo será una **aplicación de hábitos con gamificación**, que se irá construyendo incrementalmente a medida que se incorporen nuevos conocimientos.

La ruta tendrá dos objetivos paralelos:

1. Cumplir y comprender los contenidos de la asignatura.
2. Construir conocimientos sólidos que permitan desarrollar una aplicación Android propia.

---

## Ruta general

```text
JAVA
  │
  ▼
KOTLIN FUNDAMENTALS
  │
  ├── Variables y tipos
  ├── Null Safety
  ├── Operadores
  ├── Condicionales
  ├── Funciones
  ├── Colecciones
  └── map / filter / lambdas
  │
  ▼
KOTLIN INTERMEDIO
  │
  ├── Programación Orientada a Objetos
  ├── data class
  ├── sealed class
  ├── try-catch
  ├── Result<T>
  └── Scope Functions
  │
  ▼
KOTLIN ASÍNCRONO
  │
  └── Corrutinas
  │
  ▼
ANDROID STUDIO
  │
  ├── Estructura del proyecto
  ├── Activity
  ├── Emulador / dispositivo físico
  ├── XML
  └── Jetpack Compose
  │
  ▼
APLICACIÓN DE HÁBITOS
  │
  ├── UI
  ├── Navegación
  ├── Estado
  ├── Persistencia
  ├── Gamificación
  └── Arquitectura
  │
  ▼
PROYECTO FINAL
  └── App de hábitos completa
```

---

# Etapa 0 — Preparación del entorno

Antes de profundizar en Kotlin y Android, se preparará el entorno de desarrollo.

### Objetivos

- Configurar Android Studio.
- Configurar el JDK y Kotlin.
- Crear el primer proyecto Android.
- Ejecutar una aplicación.
- Conectar Android Studio con un teléfono Android físico.
- Conocer el emulador.
- Comprender las funciones básicas de `Run`, `Build`, `Debug` y `Logcat`.
- Familiarizarse progresivamente con la estructura de un proyecto Android.

No es necesario aprender todo Android Studio de inmediato. Se incorporarán sus herramientas a medida que sean necesarias.

---

# Etapa 1 — Kotlin desde la perspectiva de Java

Como ya existe una base de Java, Kotlin se estudiará haciendo comparaciones directas entre ambos lenguajes.

La metodología será:

> Java → Kotlin → ¿Qué cambió? → ¿Por qué? → Ejercicio

## Contenidos

- Variables.
- `val` y `var`.
- Inferencia de tipos.
- Tipos básicos.
- `Int`.
- `Double`.
- `Float`.
- `Boolean`.
- `Char`.
- `String`.
- Operadores.
- Conversiones de tipos.
- Interpolación de strings.
- Null Safety.

### Ejemplo

Java:

```java
String nombre = "Jose";
int edad = 25;
```

Kotlin:

```kotlin
val nombre = "Jose"
var edad = 25
```

### Null Safety

```kotlin
var nombre: String? = null
```

Conceptos asociados:

```kotlin
?.
!!
?:
```

El objetivo no será memorizar la sintaxis, sino comprender qué problema intenta solucionar el sistema de nulabilidad de Kotlin.

---

# Etapa 2 — Control de flujo y funciones

## Contenidos

- `if`
- `else`
- `when`
- `for`
- `while`
- `do while`
- Rangos.
- Funciones.
- Parámetros.
- Valores por defecto.
- Funciones de expresión.
- `Unit`.
- Lambdas.

### Ejemplo

```kotlin
fun saludar(nombre: String): String {
    return "Hola $nombre"
}
```

Posteriormente:

```kotlin
fun saludar(nombre: String) = "Hola $nombre"
```

Se realizarán ejercicios pequeños para consolidar estos conceptos antes de utilizarlos dentro de Android.

---

# Etapa 3 — Colecciones y programación funcional

Esta etapa será especialmente importante porque las colecciones forman parte directa de los contenidos de la asignatura.

## Contenidos

- `List`
- `MutableList`
- `Set`
- `Map`
- `MutableMap`
- Iteraciones.
- `forEach`
- `map`
- `filter`
- `find`
- `first`
- `any`
- `all`
- `sorted`
- Lambdas.

### Ejemplo

```kotlin
val habitos = listOf(
    "Leer",
    "Ejercicio",
    "Programar"
)

val habitosLargos = habitos.filter {
    it.length > 5
}
```

Estos conceptos comenzarán a conectarse directamente con el proyecto de hábitos.

---

# Etapa 4 — Programación Orientada a Objetos en Kotlin

Esta etapa aprovechará los conocimientos existentes de Java.

## Contenidos

- Clases.
- Objetos.
- Constructores.
- Propiedades.
- Métodos.
- Encapsulamiento.
- Herencia.
- Polimorfismo.
- Interfaces.
- Clases abstractas.
- `data class`.
- `object`.
- `enum class`.
- `sealed class`.

### Ejemplo

```kotlin
data class Habito(
    val nombre: String,
    var completado: Boolean
)
```

Posteriormente, el modelo podrá evolucionar:

```kotlin
data class Habito(
    val id: Int,
    val nombre: String,
    val experiencia: Int,
    var completadoHoy: Boolean
)
```

Aquí comenzará a construirse el modelo de datos real de la aplicación.

---

# Etapa 5 — Manejo de errores y corrutinas

## Manejo de excepciones

```kotlin
try {
    // código
} catch (e: Exception) {
    // manejo
}
```

Además de aprender la sintaxis, se estudiará cuándo utilizar excepciones y cuándo utilizar otros mecanismos para representar errores.

## Result<T>

Ejemplo:

```kotlin
fun obtenerUsuario(): Result<Usuario>
```

Se estudiará cómo representar y manejar operaciones exitosas o fallidas.

## Corrutinas

Conceptos:

- `suspend`
- `launch`
- `async`
- `await`
- `CoroutineScope`
- `Dispatchers`

El objetivo será comprender por qué Android necesita operaciones asíncronas y cómo las corrutinas permiten ejecutar tareas sin bloquear la interfaz.

---

# Etapa 6 — Android Studio y desarrollo Android

Aquí comienza la transición desde Kotlin hacia el desarrollo específico para Android.

## Entorno

- Android Studio.
- Android SDK.
- Gradle.
- Dispositivo físico.
- Emulador.
- Logcat.
- Debugger.

## Estructura de una aplicación

Se estudiará el propósito de cada parte del proyecto en lugar de simplemente memorizar dónde colocar código.

Ejemplo:

```text
app/
├── manifests/
├── kotlin/
├── res/
│   ├── drawable/
│   ├── mipmap/
│   └── values/
└── Gradle Scripts
```

## Conceptos Android

- `Activity`.
- Ciclo de vida.
- `Context`.
- Recursos.
- `AndroidManifest`.
- Gradle.

---

# Etapa 7 — Interfaces Android

La asignatura contempla XML y Jetpack Compose.

La estrategia será:

```text
XML
↓
Comprender qué es y cómo funciona
↓
Jetpack Compose
↓
Utilizar Compose como tecnología principal
```

No se busca ignorar XML, sino conocerlo lo suficiente para comprender proyectos y tutoriales que todavía lo utilicen.

## Jetpack Compose

Contenidos progresivos:

- `@Composable`
- `Text`
- `Button`
- `Column`
- `Row`
- `Box`
- `Modifier`
- `remember`
- Estado.
- Eventos.
- Listas.
- Formularios.

A partir de aquí comenzaremos a construir pantallas reales para la aplicación.

---

# Proyecto longitudinal — Aplicación de hábitos con gamificación

La aplicación será construida progresivamente. No se intentará desarrollar toda la aplicación desde el comienzo.

Cada nueva funcionalidad estará asociada a un conocimiento aprendido.

## Versión 0.1 — Esqueleto

Primera pantalla:

```text
Mis hábitos

☐ Leer
☐ Ejercicio
☐ Programar
```

Funcionalidad inicial:

- Mostrar hábitos.
- Marcar un hábito como completado.

---

## Versión 0.2 — Modelos

Introducción de:

```kotlin
data class Habito(...)
```

Los hábitos dejan de ser simples strings y pasan a ser objetos con propiedades.

---

## Versión 0.3 — Estado

La aplicación será capaz de mantener el estado de los hábitos durante la ejecución.

---

## Versión 0.4 — Persistencia

Los datos deberán sobrevivir al cierre de la aplicación.

Se incorporará una base de datos local cuando los conocimientos necesarios estén disponibles.

---

## Versión 0.5 — Sistema de experiencia

Ejemplo:

```text
Programación
Nivel 7
████████░░ 720 / 1000 XP
```

Se definirá un sistema de experiencia asociado a las actividades.

---

## Versión 0.6 — Rachas

Ejemplo:

```text
Ejercicio
🔥 12 días
```

La aplicación comenzará a registrar días consecutivos de cumplimiento.

---

## Versión 0.7 — Estadísticas

Ejemplo:

```text
Hábitos completados esta semana: 27
Mejor racha: 18 días
XP obtenida: 850
```

---

## Versión 0.8 — Sistema de niveles

Se incorporarán:

- Categorías.
- Experiencia.
- Niveles.
- Progresión.
- Reglas de recompensa.

---

## Versión 0.9 — Arquitectura

Cuando el proyecto tenga suficiente complejidad, se introducirán conceptos de arquitectura Android moderna.

Modelo conceptual:

```text
UI
↓
ViewModel
↓
Repository
↓
Database / API
```

La arquitectura se estudiará cuando exista una necesidad real dentro del proyecto, evitando introducir complejidad demasiado pronto.

---

## Versión 1.0 — Aplicación funcional

Objetivo:

Una aplicación Android de hábitos funcional que combine los conocimientos adquiridos durante el proceso.

Posteriormente podrá evolucionar hacia:

```text
Android
   ↕
Backend / API
   ↕
Base de datos
   ↕
Windows / otras plataformas
```

La sincronización con Windows, backend y servicios externos queda deliberadamente para una etapa posterior.

---

# Relación con la asignatura

La planificación oficial contempla:

## Resultado de aprendizaje

**RA1:** Aplicar un lenguaje de programación en el desarrollo de componentes básicos de una aplicación móvil, utilizando las herramientas disponibles en el mercado.

## Indicadores de logro

### IL1.1

Comparar tipos de datos, operadores aritméticos y condicionales para resolver problemas.

Se trabajará principalmente en:

- Etapa 1.
- Etapa 2.

### IL1.2

Desarrollar funciones y colecciones mediante ciclos e iteraciones.

Se trabajará principalmente en:

- Etapa 2.
- Etapa 3.

### IL1.3

Desarrollar programas orientados a objetos utilizando clases, herencia, polimorfismo y manejo de excepciones.

Se trabajará principalmente en:

- Etapa 4.
- Etapa 5.

---

# Evaluaciones

La asignatura contempla una evaluación formativa y una evaluación parcial.

## Formativa 1

Ejecución práctica.

Se utilizará como oportunidad para comprobar la comprensión de:

- Kotlin.
- Colecciones.
- Funciones.
- Corrutinas.

## Parcial 1

Ejecución práctica con una ponderación del **30%**.

Consistirá en resolver un caso de negocio mediante una aplicación de consola que integre los aprendizajes de las primeras semanas.

Por esta razón, no se saltará directamente desde Kotlin hacia Android.

La ruta inicial será:

```text
Kotlin
↓
Programas de consola
↓
Evaluación parcial
↓
Android
```

Los programas de consola serán también una forma de aprender Kotlin sin agregar todavía la complejidad de Android.

---

# Método de estudio para cada clase

Cada clase de la asignatura se trabajará siguiendo cuatro etapas.

## 1. Entender

Identificar los conceptos nuevos y explicarlos.

Cuando sea útil, se establecerá una comparación directa con Java.

```text
Java
↓
Kotlin
↓
Diferencias
↓
Motivo del cambio
```

## 2. Practicar

Resolver ejercicios pequeños y aislados.

El objetivo es comprobar que el concepto se entiende antes de incorporarlo al proyecto.

## 3. Aplicar

Determinar cómo puede utilizarse el concepto dentro de la aplicación de hábitos.

Esto permitirá que el proyecto avance junto con el aprendizaje.

## 4. Documentar

Al finalizar cada clase se preparará documentación en Markdown para Obsidian y GitHub.

Los apuntes podrán incluir:

- Conceptos.
- Sintaxis.
- Ejemplos.
- Comparaciones con Java.
- Errores comunes.
- Ejercicios.
- Aplicación práctica dentro del proyecto.

---

# Estructura propuesta del repositorio

```text
Android-Kotlin/
│
├── README.md
│
├── 01-Kotlin/
│   ├── 01-Variables.md
│   ├── 02-Null-Safety.md
│   ├── 03-Funciones.md
│   └── 04-Colecciones.md
│
├── 02-Android-Studio/
│   ├── 01-Instalacion.md
│   ├── 02-Estructura-Proyecto.md
│   └── 03-Dispositivo-Fisico.md
│
├── 03-Android/
│   └── ...
│
└── HabitsApp/
    └── ...
```

La estructura puede cambiar a medida que el proyecto crezca. Lo importante es mantener separados los apuntes de estudio y el código de la aplicación.

---

# Principios del roadmap

1. **Java será el punto de referencia inicial.**  
   Los conocimientos existentes se aprovecharán para comprender Kotlin más rápidamente.

2. **Primero comprender, después memorizar.**  
   La sintaxis será secundaria frente a entender qué problema resuelve cada herramienta.

3. **No saltar etapas innecesariamente.**  
   Android agrega muchas capas de complejidad; primero se consolidará Kotlin.

4. **Aprender mediante proyectos.**  
   Los ejercicios pequeños servirán para practicar y la aplicación de hábitos servirá como proyecto longitudinal.

5. **La aplicación crecerá con el conocimiento.**  
   No se implementarán bases de datos, arquitectura o sincronización antes de comprender los fundamentos necesarios.

6. **La documentación forma parte del aprendizaje.**  
   Cada contenido importante quedará registrado en Obsidian y podrá reflejarse en GitHub.

7. **La asignatura y el proyecto se complementarán.**  
   El contenido académico marca las prioridades inmediatas, mientras que la aplicación proporciona un contexto práctico de largo plazo.

8. **Android moderno como objetivo.**  
   Se conocerá XML, pero Jetpack Compose será el enfoque principal para la interfaz cuando lleguemos a esa etapa.

---

# Meta final

Al completar el roadmap, el objetivo no es solamente haber aprendido Kotlin o haber creado una aplicación.

La meta es poder pasar de:

```text
Conocimiento de Java
        ↓
Kotlin
        ↓
Android Studio
        ↓
Jetpack Compose
        ↓
Arquitectura Android
        ↓
Persistencia de datos
        ↓
Aplicación funcional
```

y comprender qué ocurre en cada etapa.

El proyecto final será una aplicación Android de hábitos con un sistema progresivo de gamificación, construida desde cero y documentada durante todo el proceso.
