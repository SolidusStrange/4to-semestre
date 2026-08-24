# Guía 02 — Ejercicios de Kotlin

## Antes de empezar

Esta guía reúne **6 ejercicios cortos** para practicar los contenidos vistos en la Guía 02:

* Variables
* Tipos de datos
* Conversión de tipos
* Manejo de nulos
* Operadores
* Entrada y salida por consola
* Condicionales
* Ciclos

No se necesitan funciones propias ni colecciones. Cada ejercicio debe resolverse utilizando únicamente los contenidos vistos hasta este punto.

Los ejercicios deben realizarse en **IntelliJ IDEA**.

Cada ejercicio puede estar:

* En su propio archivo `.kt`.
* O dentro del mismo `main`, separados mediante comentarios indicando el número del ejercicio.

Se recomienda resolverlos en el orden presentado.

---

# Ejercicio 1 — Variables, tipos y conversión

## Objetivo

Practicar:

* Declaración de variables.
* Tipos de datos.
* Conversión de `String` a `Double`.
* Plantillas de texto.

## Enunciado

Se registrará el precio de un producto tal como llegaría desde la consola: como texto y no como número.

El programa debe:

1. Declarar:

```kotlin
val nombreProducto: String
```

con el nombre de un producto.

2. Declarar:

```kotlin
val precioTexto: String
```

con su precio escrito como texto.

Ejemplo:

```kotlin
val precioTexto: String = "8990"
```

3. Convertir `precioTexto` a `Double` utilizando:

```kotlin
toDouble()
```

4. Calcular el precio final agregando un **19% de IVA**.

5. Mostrar el nombre del producto y el precio final utilizando plantillas de texto:

```kotlin
"$variable"
```

No utilizar concatenación con `+`.

---

# Ejercicio 2 — Operadores

## Objetivo

Practicar:

* `Double`
* `Int`
* Multiplicación
* Comparaciones
* Operador lógico `&&`
* `Boolean`
* Plantillas de texto

## Enunciado

Se calculará si un pedido de dos o más productos alcanza para obtener despacho gratis.

El programa debe:

1. Declarar:

```kotlin
val precioUnitario: Double
val cantidad: Int
```

con los datos de un pedido.

Ejemplo:

```kotlin
val precioUnitario: Double = 8990.0
val cantidad: Int = 2
```

2. Calcular el total del pedido utilizando el operador `*`.

```kotlin
total = precioUnitario * cantidad
```

3. Crear una variable `Boolean` que indique si el pedido tiene despacho gratis.

Deben cumplirse **ambas condiciones**:

```text
total >= 15000
cantidad >= 2
```

Para esto se debe utilizar el operador lógico:

```kotlin
&&
```

4. Mostrar el total y si aplica despacho gratis utilizando una plantilla de texto.

---

# Ejercicio 3 — Condicionales, `if` como expresión y `when`

## Objetivo

Practicar:

* `if` como expresión.
* `when`.
* Comparaciones.
* Rangos y condiciones.
* Plantillas de texto.

## Enunciado

A partir del total de un pedido, se debe clasificar la compra de dos formas diferentes.

### Paso 1 — Declarar el total

Declarar:

```kotlin
val total: Double
```

Ejemplo:

```kotlin
val total: Double = 25000.0
```

### Paso 2 — Clasificar utilizando `if`

Utilizar `if` como expresión para guardar en una variable:

```text
"Compra grande"
```

si el total es mayor a `$20.000`.

En caso contrario:

```text
"Compra regular"
```

Ejemplo:

```kotlin
val tipoCompra = if (total > 20000) {
    "Compra grande"
} else {
    "Compra regular"
}
```

### Paso 3 — Clasificar utilizando `when`

Utilizar `when` para clasificar el mismo total:

| Total                   | Categoría |
| ----------------------- | --------- |
| Menor a $10.000         | Bajo      |
| Entre $10.000 y $30.000 | Medio     |
| Mayor a $30.000         | Alto      |

### Paso 4 — Mostrar los resultados

Mostrar ambas clasificaciones utilizando plantillas de texto.

---

# Ejercicio 4 — Ciclos, `for` y `while`

## Objetivo

Practicar:

* `for`
* Rangos
* `downTo`
* `while`
* Contadores
* Acumuladores

## Enunciado

Se simulará el empaquetado de varios pedidos antes de despacharlos.

### Parte 1 — Cuenta regresiva

Utilizar un `for` y un rango en reversa mediante `downTo` para imprimir:

```text
5
4
3
2
1
```

antes de despachar el pedido.

Ejemplo:

```kotlin
for (i in 5 downTo 1) {
    println(i)
}
```

### Parte 2 — Simular pedidos

Utilizar un `while` para simular el ingreso de **4 pedidos**.

Se debe comenzar con:

```kotlin
var contador = 0
var totalAcumulado = 0.0
```

En cada vuelta del ciclo:

* Aumentar el contador.
* Sumar un monto fijo al total acumulado.

Por ejemplo:

```text
$5.000 por pedido
```

El ciclo debe continuar hasta completar los 4 pedidos.

### Resultado

Al terminar el `while`, mostrar el total acumulado.

Con cuatro pedidos de $5.000:

```text
Total acumulado: $20.000
```

---

# Ejercicio 5 — Verificación de nulos y operador Elvis

## Objetivo

Practicar:

* Tipos nullable.
* `String?`
* `null`
* Verificación con `if`.
* Operador Elvis `?:`

## Enunciado

Se simulará un código de descuento que el cliente puede dejar en blanco.

### Paso 1 — Crear una variable nullable

Declarar:

```kotlin
var codigoDescuento: String?
```

y asignarle inicialmente:

```kotlin
null
```

Ejemplo:

```kotlin
var codigoDescuento: String? = null
```

El `?` indica que la variable puede contener un `String` o `null`.

### Paso 2 — Verificar si es `null`

Utilizar `if` para comprobar explícitamente si el código es `null`.

Si es `null`, mostrar un mensaje indicando que no se aplicará ningún descuento.

Ejemplo:

```kotlin
if (codigoDescuento == null) {
    println("No se aplicará descuento")
}
```

### Paso 3 — Utilizar operador Elvis

Utilizar:

```kotlin
?:
```

para crear una nueva variable que contenga:

```text
código ingresado
```

o, si el valor es `null`:

```text
"SIN-CODIGO"
```

Ejemplo:

```kotlin
val codigoMostrar = codigoDescuento ?: "SIN-CODIGO"
```

### Paso 4 — Probar con un código real

Cambiar:

```kotlin
codigoDescuento = null
```

por:

```kotlin
codigoDescuento = "VERANO10"
```

Agregar un comentario explicando qué resultado cambiaría al volver a ejecutar el programa.

Por ejemplo:

```kotlin
// Al cambiar null por "VERANO10", ya no se mostrará "SIN-CODIGO"
// y la variable codigoMostrar tendrá el código ingresado.
```

---

# Ejercicio 6 — Nulos, Elvis y `when`

## Objetivo

Combinar:

* Tipos nullable.
* Verificación de nulos.
* `Boolean`.
* `when`.
* Operador Elvis `?:`
* Plantillas de texto.

## Enunciado

Se registrará el email de un cliente, considerando que este dato es opcional.

### Paso 1 — Declarar el email

Declarar:

```kotlin
var emailCliente: String?
```

El valor puede ser un email válido o `null`.

Ejemplos:

```kotlin
var emailCliente: String? = "cliente@tienda.cl"
```

o:

```kotlin
var emailCliente: String? = null
```

### Paso 2 — Determinar si tiene email

Utilizar una verificación de nulos para guardar en una variable `Boolean` llamada:

```kotlin
tieneEmail
```

Esta variable debe indicar si el cliente registró un email.

Ejemplo conceptual:

```kotlin
val tieneEmail = emailCliente != null
```

El resultado será:

```text
true
```

si existe un email, o:

```text
false
```

si el valor es `null`.

### Paso 3 — Utilizar `when`

Utilizar `when` sobre `tieneEmail`.

Debe producir uno de estos mensajes:

```text
"Contacto completo"
```

o:

```text
"Falta email de contacto"
```

Ejemplo conceptual:

```kotlin
val mensaje = when (tieneEmail) {
    true -> "Contacto completo"
    false -> "Falta email de contacto"
}
```

### Paso 4 — Utilizar operador Elvis

Crear una nueva variable utilizando:

```kotlin
?:
```

Si `emailCliente` contiene un email, se debe utilizar ese valor.

Si es `null`, se debe utilizar:

```text
"no-registrado@tienda.cl"
```

Ejemplo:

```kotlin
val emailMostrar = emailCliente ?: "no-registrado@tienda.cl"
```

### Paso 5 — Mostrar los resultados

Mostrar:

* El email que se utilizará.
* El estado del contacto.

Utilizar plantillas de texto.

---

# Resumen de conceptos practicados

| Ejercicio | Contenidos principales                             |
| --------- | -------------------------------------------------- |
| 1         | Variables, tipos, `String`, `Double`, `toDouble()` |
| 2         | Operadores, comparación, `&&`, `Boolean`           |
| 3         | `if` como expresión, `when`, condiciones           |
| 4         | `for`, `downTo`, `while`, contador, acumulador     |
| 5         | `String?`, `null`, `if`, `?:`                      |
| 6         | Nullable, `Boolean`, `when`, `?:`                  |

---

# Orden recomendado de resolución

```text
Ejercicio 1
    ↓
Variables y conversión

Ejercicio 2
    ↓
Operadores

Ejercicio 3
    ↓
Condicionales

Ejercicio 4
    ↓
Ciclos

Ejercicio 5
    ↓
Nulos y Elvis

Ejercicio 6
    ↓
Integración de conceptos
```

La dificultad aumenta progresivamente: los primeros ejercicios trabajan conceptos individuales y los últimos combinan varios conceptos vistos en la guía.
