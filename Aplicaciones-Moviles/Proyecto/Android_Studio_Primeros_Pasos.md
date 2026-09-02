# Android Studio + Kotlin --- Primeros pasos

## Objetivo de esta etapa

Esta etapa tiene como objetivo familiarizarnos con el entorno de
desarrollo Android antes de comenzar a construir funcionalidades de
nuestra aplicación de hábitos.

El flujo de aprendizaje será:

``` text
Java
  ↓
Kotlin
  ↓
Android Studio
  ↓
Android + Kotlin
  ↓
Aplicación de hábitos
```

La idea no es memorizar código generado por Android Studio, sino
entender progresivamente qué hace cada parte y relacionarla con los
conocimientos que ya tenemos de Java y desarrollo web.

------------------------------------------------------------------------

# 1. Conectar el celular a Android Studio

## 1.1. ¿Por qué usar un celular físico?

Android Studio permite ejecutar una aplicación en un emulador, pero
también podemos instalarla directamente en un teléfono Android.

Esto permite comprobar cómo funciona nuestra aplicación en un
dispositivo real.

El flujo general es:

``` text
Código
  ↓
Android Studio
  ↓
Gradle compila
  ↓
Aplicación Android
  ↓
ADB
  ↓
Celular
```

------------------------------------------------------------------------

## 1.2. Activar opciones de desarrollador

En Android, normalmente se deben activar las opciones de desarrollador
desde:

``` text
Ajustes
→ Acerca del teléfono
→ Número de compilación
```

Al tocar varias veces el número de compilación, Android habilita las
opciones de desarrollador.

La ubicación exacta puede variar según la marca y versión de Android.

------------------------------------------------------------------------

## 1.3. Activar depuración USB

Dentro de:

``` text
Opciones de desarrollador
```

se puede activar:

``` text
Depuración USB
```

Esto permite que el computador se comunique con el teléfono mediante
ADB.

------------------------------------------------------------------------

## 1.4. Conexión mediante USB

Al conectar el teléfono por USB pueden aparecer diferentes opciones:

``` text
Transferir archivos
Android Auto
Anclaje de red
MIDI
Transferir imágenes
Solo cargar el teléfono
```

Para una conexión tradicional mediante USB, una opción habitual es:

``` text
Transferir archivos
```

También es importante utilizar un cable que permita transferencia de
datos, no solamente carga.

------------------------------------------------------------------------

# 2. Conectar el celular mediante Wi-Fi

También es posible conectar Android Studio al teléfono mediante Wi-Fi.

En este caso se realiza el emparejamiento desde las opciones de
desarrollador del teléfono y Android Studio.

Una vez realizado el pairing, el teléfono aparece como dispositivo
disponible en Android Studio.

Esto permite ejecutar la aplicación sin mantener conectado el cable USB.

En nuestro caso, la conexión mediante Wi-Fi funcionó correctamente.

------------------------------------------------------------------------

# 3. Ejecutar una aplicación en el celular

Una vez conectado el dispositivo:

1.  Abrir el proyecto en Android Studio.
2.  Seleccionar el teléfono en el selector de dispositivos.
3.  Presionar:

``` text
▶ Run
```

Android Studio realiza aproximadamente este proceso:

``` text
Código Kotlin
      ↓
Gradle
      ↓
Compilación
      ↓
APK
      ↓
ADB / Wi-Fi
      ↓
Instalación en el celular
      ↓
Aplicación ejecutándose
```

La primera compilación puede tardar más porque Gradle necesita preparar
dependencias y otros elementos del proyecto.

------------------------------------------------------------------------

# 4. Primer resultado: Hello World

Se comprobó que el proyecto podía:

-   Compilar correctamente.
-   Conectarse al teléfono.
-   Instalarse mediante Wi-Fi.
-   Ejecutarse en el dispositivo físico.
-   Mostrar una interfaz.
-   Recibir cambios realizados en Android Studio.

También se modificó el texto inicial de la aplicación y se comprobó que
el cambio apareció correctamente en el celular.

Esto confirma que el entorno básico de desarrollo está funcionando.

------------------------------------------------------------------------

# 5. Estructura básica de un proyecto Android

Un proyecto puede tener una estructura similar a:

``` text
MiProyecto/
│
├── app/
│   ├── build.gradle.kts
│   │
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           │
│           ├── java/
│           │   └── ...
│           │
│           └── res/
│               ├── drawable/
│               ├── layout/
│               ├── mipmap/
│               └── values/
│
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
```

La estructura exacta puede variar según la versión de Android Studio y
la plantilla utilizada.

------------------------------------------------------------------------

# 6. La carpeta `app`

``` text
app/
```

Es el módulo que contiene nuestra aplicación.

En proyectos sencillos será uno de los lugares principales donde
trabajaremos.

Dentro de `app` encontramos el código, recursos, configuración y otros
elementos necesarios para construir la aplicación.

------------------------------------------------------------------------

# 7. `AndroidManifest.xml`

Ruta habitual:

``` text
app/
└── src/
    └── main/
        └── AndroidManifest.xml
```

El `AndroidManifest.xml` es un archivo fundamental de Android.

Sirve para declarar información que el sistema operativo necesita
conocer sobre la aplicación.

Entre otras cosas puede declarar:

-   Componentes de la aplicación.
-   Activities.
-   Activity de entrada.
-   Permisos.
-   Características y configuraciones de la aplicación.

No es necesario memorizar todavía su contenido.

Lo importante es entender que funciona como una declaración de
información importante para Android.

------------------------------------------------------------------------

# 8. Código Kotlin

El código de la aplicación se encuentra normalmente dentro de una ruta
similar a:

``` text
app/
└── src/
    └── main/
        └── java/
```

Dependiendo de la versión de Android Studio, también puede aparecer
agrupado visualmente como:

``` text
kotlin+java/
```

Dentro encontramos archivos Kotlin como:

``` text
MainActivity.kt
```

------------------------------------------------------------------------

# 9. `MainActivity.kt`

Este es el archivo que analizamos en nuestra primera revisión.

Nuestro archivo actual es:

``` kotlin
package com.example.ejercicio1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
```

Este archivo utiliza el enfoque tradicional de Android:

``` text
Kotlin + XML
```

No estamos utilizando Jetpack Compose en este proyecto.

Esto es especialmente útil para nuestro aprendizaje porque permite
establecer conexiones con Java y con desarrollo web.

------------------------------------------------------------------------

# 10. `package`

``` kotlin
package com.example.ejercicio1
```

Indica a qué paquete pertenece la clase.

En Java encontramos una declaración equivalente:

``` java
package com.example.ejercicio1;
```

Una diferencia sintáctica es que Kotlin no necesita `;`.

------------------------------------------------------------------------

# 11. `import`

Nuestro archivo comienza con varios imports:

``` kotlin
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
```

Los imports permiten utilizar clases y funciones definidas en otros
paquetes sin tener que escribir su nombre completo.

Esto es similar a Java:

``` java
import java.util.ArrayList;
```

------------------------------------------------------------------------

# 12. Clase `MainActivity`

``` kotlin
class MainActivity : AppCompatActivity() {
```

Aquí aparece herencia.

En Java escribiríamos conceptualmente:

``` java
public class MainActivity extends AppCompatActivity {
}
```

En Kotlin:

``` kotlin
class MainActivity : AppCompatActivity() {
}
```

La equivalencia principal es:

``` text
Java                         Kotlin

class                        class
extends                      :
implements                   :
```

Por lo tanto:

``` kotlin
MainActivity : AppCompatActivity()
```

significa que `MainActivity` hereda de `AppCompatActivity`.

------------------------------------------------------------------------

# 13. `override`

``` kotlin
override fun onCreate(savedInstanceState: Bundle?) {
```

`override` indica que estamos sobrescribiendo un método heredado de una
clase padre.

En Java:

``` java
@Override
protected void onCreate(Bundle savedInstanceState) {
}
```

En Kotlin:

``` kotlin
override fun onCreate(...) {
}
```

Una diferencia sintáctica importante es que Kotlin utiliza directamente
la palabra:

``` kotlin
override
```

------------------------------------------------------------------------

# 14. `fun`

``` kotlin
fun onCreate(...)
```

`fun` es la palabra reservada de Kotlin para declarar una función.

Ejemplo:

``` kotlin
fun saludar() {
}
```

En Java:

``` java
void saludar() {
}
```

Por ahora podemos recordar:

``` text
Java                 Kotlin

void saludar()       fun saludar()
```

Más adelante estudiaremos con detalle los tipos de retorno de Kotlin.

------------------------------------------------------------------------

# 15. Parámetros en Kotlin

Tenemos:

``` kotlin
savedInstanceState: Bundle?
```

Kotlin normalmente utiliza el formato:

``` kotlin
nombre: Tipo
```

Ejemplos:

``` kotlin
val edad: Int = 25
val nombre: String = "José"
```

Esto contrasta con Java:

``` java
int edad = 25;
String nombre = "José";
```

------------------------------------------------------------------------

# 16. Nullability y `?`

En:

``` kotlin
savedInstanceState: Bundle?
```

aparece:

``` kotlin
?
```

El signo `?` indica que el valor puede ser `null`.

Por ejemplo:

``` kotlin
String?
```

significa:

``` text
Puede contener un String
o
puede contener null
```

Este concepto forma parte del sistema de null safety de Kotlin.

Es uno de los temas que estudiaremos con más profundidad.

------------------------------------------------------------------------

# 17. `super.onCreate()`

Tenemos:

``` kotlin
super.onCreate(savedInstanceState)
```

`super` hace referencia a la clase padre.

Como:

``` kotlin
MainActivity : AppCompatActivity()
```

`MainActivity` hereda de `AppCompatActivity`.

Por eso:

``` kotlin
super.onCreate(...)
```

ejecuta la implementación correspondiente de `onCreate()` de la clase
padre.

------------------------------------------------------------------------

# 18. `enableEdgeToEdge()`

``` kotlin
enableEdgeToEdge()
```

Esta función configura la aplicación para trabajar utilizando el espacio
completo de la pantalla, incluyendo las áreas cercanas a las barras del
sistema.

La configuración posterior relacionada con `WindowInsetsCompat` ayuda a
manejar correctamente esas áreas.

No es necesario memorizar este código todavía.

------------------------------------------------------------------------

# 19. `setContentView()`

``` kotlin
setContentView(R.layout.activity_main)
```

Esta es una línea especialmente importante.

Indica que la Activity utilizará como interfaz el layout:

``` text
activity_main.xml
```

La relación es:

``` text
MainActivity.kt
       │
       │ setContentView()
       ↓
activity_main.xml
       ↓
Interfaz de usuario
```

------------------------------------------------------------------------

# 20. Android + Kotlin + XML

Nuestro proyecto utiliza una separación básica entre comportamiento e
interfaz:

``` text
MainActivity.kt
        │
        │ Kotlin
        │ comportamiento
        ↓
activity_main.xml
        │
        │ XML
        │ interfaz
        ↓
      UI
```

Esto puede compararse conceptualmente con desarrollo web:

``` text
HTML
  ↓
estructura

CSS
  ↓
apariencia

JavaScript
  ↓
comportamiento
```

No son sistemas equivalentes, pero la separación de responsabilidades
sirve como referencia inicial.

------------------------------------------------------------------------

# 21. `R`

En:

``` kotlin
R.layout.activity_main
```

aparece `R`.

`R` es una clase generada automáticamente por Android que contiene
referencias a los recursos de la aplicación.

Por ejemplo:

``` kotlin
R.layout.activity_main
```

hace referencia al recurso:

``` text
res/layout/activity_main.xml
```

También encontraremos referencias como:

``` kotlin
R.id.main
```

que hacen referencia a elementos identificados dentro de los recursos de
la aplicación.

No debemos crear manualmente la clase `R`.

------------------------------------------------------------------------

# 22. `findViewById()`

Tenemos:

``` kotlin
findViewById(R.id.main)
```

Esto busca dentro de la interfaz un elemento cuyo ID sea:

``` text
main
```

La idea general puede compararse con JavaScript:

``` javascript
document.getElementById("main")
```

No funcionan de la misma manera internamente, pero ambos conceptos
implican localizar un elemento de la interfaz/documento mediante un
identificador.

------------------------------------------------------------------------

# 23. Lambdas

Esta parte utiliza una lambda:

``` kotlin
{ v, insets ->
    ...
}
```

Una lambda es una función que puede utilizarse como valor y pasarse como
argumento.

En este caso:

``` kotlin
ViewCompat.setOnApplyWindowInsetsListener(
    findViewById(R.id.main)
) { v, insets ->
    ...
}
```

La idea inicial puede entenderse como:

``` text
recibe v e insets
        ↓
ejecuta este bloque de código
```

Las lambdas son una característica importante de Kotlin y serán
estudiadas posteriormente.

------------------------------------------------------------------------

# 24. `val`

Dentro de la lambda encontramos:

``` kotlin
val systemBars = insets.getInsets(
    WindowInsetsCompat.Type.systemBars()
)
```

`val` declara una variable cuyo valor no puede ser reasignado después de
su inicialización.

Ejemplo:

``` kotlin
val nombre = "José"
```

No podemos hacer posteriormente:

``` kotlin
nombre = "Pedro"
```

Para una variable cuyo valor puede cambiar utilizamos:

``` kotlin
var
```

Por ejemplo:

``` kotlin
var edad = 20
edad = 21
```

La diferencia entre `val` y `var` será uno de los primeros temas
formales de Kotlin.

------------------------------------------------------------------------

# 25. `setPadding()`

Tenemos:

``` kotlin
v.setPadding(
    systemBars.left,
    systemBars.top,
    systemBars.right,
    systemBars.bottom
)
```

Esto agrega espacio interno al elemento para evitar que el contenido
quede oculto debajo de las barras del sistema.

La lógica utiliza los valores obtenidos desde:

``` kotlin
WindowInsetsCompat
```

Por ahora podemos considerar esta sección como código de configuración
generado por Android Studio.

------------------------------------------------------------------------

# 26. Lo que debemos aprender de este primer archivo

No necesitamos memorizar `MainActivity.kt`.

El objetivo de esta primera revisión es reconocer los conceptos que
aparecen dentro del archivo.

Los principales son:

``` kotlin
class MainActivity : AppCompatActivity()
```

Herencia.

``` kotlin
override
```

Sobrescritura.

``` kotlin
fun
```

Funciones.

``` kotlin
val
```

Variables no reasignables.

``` kotlin
var
```

Variables reasignables.

``` kotlin
Bundle?
```

Nullability.

``` kotlin
{ v, insets ->
}
```

Lambdas.

``` kotlin
setContentView(...)
```

Conexión entre la Activity y el layout XML.

``` kotlin
R.layout.activity_main
```

Referencia a un recurso de la aplicación.

------------------------------------------------------------------------

# 27. Orden recomendado de estudio

Para no intentar aprender todo al mismo tiempo, podemos organizar esta
etapa en bloques.

## Bloque 1 --- Entorno Android

Objetivo: entender las herramientas.

-   Android Studio.
-   Proyecto Android.
-   Gradle.
-   ADB.
-   Dispositivo físico.
-   Conexión mediante Wi-Fi.
-   Ejecutar una aplicación.
-   Compilar e instalar una aplicación.

Estado:

``` text
COMPLETADO
```

------------------------------------------------------------------------

## Bloque 2 --- Estructura de un proyecto Android

Objetivo: saber dónde está cada cosa.

Estudiar:

-   `app/`
-   `src/main/`
-   `AndroidManifest.xml`
-   `java/` o `kotlin+java/`
-   `res/`
-   `layout/`
-   `drawable/`
-   `mipmap/`
-   `values/`
-   archivos Gradle.

Estado:

``` text
INICIADO
```

------------------------------------------------------------------------

## Bloque 3 --- Kotlin para Android

Antes de avanzar mucho con Android, reforzar los conceptos de Kotlin que
ya aparecieron.

Orden sugerido:

1.  `val` y `var`
2.  Tipos de datos
3.  Inferencia de tipos
4.  Nullability (`?`)
5.  Operador seguro (`?.`)
6.  Elvis (`?:`)
7.  Funciones
8.  Parámetros
9.  Valores de retorno
10. Clases y objetos
11. Herencia
12. `override`
13. Lambdas
14. Colecciones
15. Funciones de orden superior.

Como ya existe una base de Java, estos conceptos pueden estudiarse
comparándolos con Java cuando sea útil.

------------------------------------------------------------------------

## Bloque 4 --- Android tradicional con XML

Después de reforzar Kotlin:

-   `Activity`
-   ciclo de vida básico
-   `onCreate()`
-   `setContentView()`
-   XML
-   Views
-   `id`
-   `findViewById()`
-   `TextView`
-   `EditText`
-   `Button`
-   `LinearLayout`
-   `ConstraintLayout`
-   eventos y listeners
-   recursos.

------------------------------------------------------------------------

## Bloque 5 --- Primera aplicación pequeña

Antes de comenzar la aplicación de hábitos completa, crear pequeños
ejercicios para practicar:

``` text
Texto
  ↓
Botón
  ↓
Entrada de datos
  ↓
Eventos
  ↓
Cambiar interfaz
  ↓
Guardar información
```

Cada ejercicio debería introducir solamente algunos conceptos nuevos.

------------------------------------------------------------------------

# 28. Proyecto principal: aplicación de hábitos

La aplicación de hábitos será nuestro proyecto práctico principal.

La construiremos progresivamente en lugar de copiar una aplicación
completa desde el principio.

Una posible evolución será:

``` text
Versión 1
Mostrar hábitos
       ↓
Versión 2
Agregar hábito
       ↓
Versión 3
Marcar hábito como completado
       ↓
Versión 4
Eliminar hábito
       ↓
Versión 5
Guardar hábitos
       ↓
Versión 6
Persistencia de datos
       ↓
Versión 7
Mejorar interfaz
       ↓
Versión 8
Arquitectura
       ↓
Versión 9
Funciones Android más avanzadas
```

Cada versión deberá servir como práctica de los conceptos estudiados.

------------------------------------------------------------------------

# 29. Relación con los conocimientos anteriores

El aprendizaje no parte desde cero.

Tenemos tres áreas que podemos conectar:

``` text
Java
 │
 ├── clases
 ├── objetos
 ├── herencia
 ├── colecciones
 └── métodos
        │
        ↓
      Kotlin
        │
        ├── sintaxis más concisa
        ├── null safety
        ├── funciones
        ├── lambdas
        └── colecciones
                │
                ↓
        Android Studio
                │
                ├── Activity
                ├── XML
                ├── Views
                ├── recursos
                └── Gradle
                        │
                        ↓
              Aplicación de hábitos
```

También podemos aprovechar la experiencia previa con desarrollo web:

``` text
HTML / XML
JavaScript / Kotlin
CSS / recursos y componentes visuales
LocalStorage / persistencia Android
```

Estas comparaciones sirven para entender conceptos, pero no debemos
asumir que las tecnologías funcionan exactamente igual.

------------------------------------------------------------------------

# 30. Estado actual

Hasta ahora ya logramos:

``` text
[✓] Instalar/configurar Android Studio
[✓] Crear un proyecto Android
[✓] Activar opciones de desarrollador
[✓] Conectar el teléfono
[✓] Emparejar mediante Wi-Fi
[✓] Hacer que Android Studio detecte el dispositivo
[✓] Ejecutar la aplicación
[✓] Ver Hello World en el celular
[✓] Modificar el texto
[✓] Ver el cambio en el celular
[✓] Identificar la estructura general del proyecto
[✓] Revisar MainActivity.kt
```

Siguiente objetivo:

``` text
Kotlin aplicado a Android
        ↓
entender MainActivity.kt
        ↓
crear pequeñas modificaciones
        ↓
trabajar con XML y Views
        ↓
comenzar aplicación de hábitos
```

------------------------------------------------------------------------

# 31. Regla de aprendizaje para esta etapa

La prioridad será:

``` text
Entender → Practicar → Modificar → Crear
```

y no:

``` text
Copiar → Pegar → Ejecutar
```

Cuando aparezca código generado automáticamente por Android Studio,
primero identificaremos qué función cumple y qué partes realmente
necesitamos modificar.

La aplicación de hábitos será el proyecto conductor del aprendizaje,
pero cada funcionalidad se incorporará después de estudiar los conceptos
necesarios para construirla.
